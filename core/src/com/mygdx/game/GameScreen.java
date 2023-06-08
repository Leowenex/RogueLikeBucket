package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    final GameLauncher game;

    Map map;

    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;

    Player player;
    ArrayList<Monster> monsters;

    ArrayList<Item> items;

    long lastDropTime;
    int dropsGathered;

    boolean bucketMovingRight;
    boolean bucketMovingDown;

    int[] exitPos;

    public GameScreen(final GameLauncher game) {
        this.game = game;

        map = new Map(40,25);

        int[] playerPos = map.findPlayerStart();
        player = new Player(playerPos[0], playerPos[1]);


        int difficuty = 2;
        monsters = map.placeMonsters(playerPos, difficuty);
        items = map.placeItems(playerPos, difficuty);

        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        exitPos = map.placeExit(playerPos);

        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above

        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        map.draw(game.batch);
        player.draw(game.batch);
        for(Monster monster : monsters)
            monster.draw(game.batch);
        for (Item item : items)
            item.draw(game.batch);
        /*
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        */
        game.font.draw(game.batch, "Number of items in the inventory: " + player.inventory.size(), 600, 850);
        /*
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height, 0, 0, 64, 64, bucketMovingRight, bucketMovingDown);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
         */
        game.batch.end();

        player.update(map);
        for(Monster monster : monsters)
            monster.update(player);
        for (Item item : items)
            item.update(player);

        if(player.x == exitPos[0] && player.y == exitPos[1]){
            //TODO: faire une fonction qui remplace la map et reset les monstres et items au lieu de recréer un GameScreen
            game.setScreen(new GameScreen(game));
            rainMusic.stop();
        }

        /*
        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }

         */

        if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
            game.setScreen(new MainMenuScreen(game));
            rainMusic.stop();
        }
        if(Gdx.input.isKeyPressed(Keys.M)){
            if(rainMusic.isPlaying())
                rainMusic.pause();
            else
                rainMusic.play();
        }

        if(player.getHealth()<=0){
            game.setScreen(new GameOverScreen(game));
        }

        // make sure the bucket stays within the screen bounds
        /*
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;
        */


        // check if we need to create a new raindrop

        /*
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

         */

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.

        /*
        Iterator<Rectangle> iter = raindrops.iterator();
        while (false && iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(player.position)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }
        */
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        rainMusic.pause();
    }

    @Override
    public void resume() {
        rainMusic.play();
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

}
