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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    final GameLauncher game;

    Map map;

    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;

    Player player;
    ArrayList<Monster> monsters;

    ArrayList<Item> items;

    ArrayList<Sprite> spawns;

    long lastDropTime;

    int level;

    int[] exitPos;

    public GameScreen(final GameLauncher game) {
        this.game = game;

        level = 1;
        map = new Map(40,25, level);

        int[] playerPos = map.findPlayerStart();
        player = new Player(playerPos[0], playerPos[1]);


        monsters = map.placeMonsters(playerPos, level);
        this.spawns = new ArrayList<Sprite>();
        for(Monster monster : monsters){
            Sprite spawn = new Sprite(new Texture(Gdx.files.internal("spawn-monster.png")));
            spawn.setSize(32,32);
            spawn.setPosition(monster.x *32,800 - monster.y*32);
            spawns.add(spawn);
        }
        items = map.placeItems(playerPos, level);
        exitPos = map.placeExit(playerPos);

        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
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
        for(Sprite spawn: spawns){
            spawn.draw(game.batch);
        }
        player.draw(game.batch);
        for(Monster monster : monsters) {
            monster.draw(game.batch);
        }
        for (Item item : items)
            item.draw(game.batch);
        /*
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        */
        game.font.draw(game.batch, "Number of items in the inventory: " + player.inventory.size(), 600, 850);

        for (int i=0;i<9;i++){
            Sprite inventory = new Sprite(new Texture(Gdx.files.internal("inventory_case.png")));
            inventory.setSize(80,80);
            inventory.setPosition(1300, 750 - i*90);
            inventory.draw(game.batch);
        }

        for(int i=0;i<player.inventory.size();i++){
            player.inventory.get(i).sprite.setPosition(1308, 750 - i*90);
            player.inventory.get(i).sprite.setSize(64,64);
            player.inventory.get(i).sprite.draw(game.batch);
        }

        /*
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height, 0, 0, 64, 64, bucketMovingRight, bucketMovingDown);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
         */


        player.update(map);
        for(Monster monster : monsters)
            monster.update(player);
        for (Item item : items) {
            item.update(player);
        }


        game.batch.end();
        if(player.x == exitPos[0] && player.y == exitPos[1]){
            this.loadNextMap();
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

    public void loadNextMap(){

        level++;

        map = new Map(40,25, level);
        int[] playerPos = map.findPlayerStart();
        player.x = playerPos[0];
        player.y = playerPos[1];
        monsters = map.placeMonsters(playerPos, level);
        this.spawns = new ArrayList<Sprite>();
        for(Monster monster : monsters){
            Sprite spawn = new Sprite(new Texture(Gdx.files.internal("spawn-monster.png")));
            spawn.setSize(32,32);
            spawn.setPosition(monster.x *32,800 - monster.y*32);
            spawns.add(spawn);
        }
        items = map.placeItems(playerPos, level);

        exitPos = map.placeExit(playerPos);
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
        dropSound.dispose();
        rainMusic.dispose();
    }

}
