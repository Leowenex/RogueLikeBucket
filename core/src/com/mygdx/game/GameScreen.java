package com.mygdx.game;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final GameLauncher game;

    Map map;

    Sound dropSound;
    Music music;
    OrthographicCamera camera;

    Player player;
    ArrayList<Monster> monsters;

    ArrayList<Item> items;

    ArrayList<Sprite> spawns;

    ArrayList<Item> coins;

    NPC npc ;

    int level;

    int[] exitPos;

    public GameScreen(final GameLauncher game) {
        this.game = game;

        level = 0;

        npc=new NPC(5,5,"traveler","traveler");

        player = new Player(0,0);

        music = Gdx.audio.newMusic(Gdx.files.internal("musics/DarkDungeon.ogg"));
        music.setLooping(true);
        music.setVolume(0.3f);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);

        this.loadNextMap();
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
        npc.draw(game.batch);
        for(Monster monster : monsters) {
            monster.draw(game.batch);
        }
        for (Item item : items)
            item.draw(game.batch);

        for (Item coin : coins){
            coin.draw(game.batch);
        }
        /*
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        */
        game.font.draw(game.batch, "Number of items in the inventory: " + player.inventory.size(), 600, 850);
        Sprite gold = new Sprite(new Texture(Gdx.files.internal( "gold.png")));
        gold.setSize(32,32);
        gold.setPosition(1300,850);
        gold.draw(game.batch);
        game.font.draw(game.batch, " x " + player.gold, 1340,872);

        for (int i=0;i<9;i++){
            Sprite inventory = new Sprite(new Texture(Gdx.files.internal("inventory_case.png")));
            inventory.setSize(80,80);
            inventory.setPosition(1300, 750 - i*90);
            inventory.draw(game.batch);
        }

        for(int i=0;i<player.inventory.size();i++){
            player.inventory.get(i).sprite.setPosition(1308, 758 - i*90);
            player.inventory.get(i).sprite.setSize(64,64);
            player.inventory.get(i).sprite.draw(game.batch);
        }

        if(Gdx.input.isKeyPressed(Keys.NUM_1)){
            if (player.inventory.size() > 0){
                Item itemDropped  = player.dropItem(0);
                items.add(itemDropped);
            }
        }

        if(Gdx.input.isKeyPressed(Keys.NUM_2)){
            if (player.inventory.size() > 1){
                Item itemDropped  = player.dropItem(1);
                items.add(itemDropped);
            }
        }
        if(Gdx.input.isKeyPressed(Keys.NUM_3)){
            if (player.inventory.size() > 2){
                Item itemDropped  = player.dropItem(2);
                items.add(itemDropped);
            }
        }
        if(Gdx.input.isKeyPressed(Keys.NUM_4)){
            if (player.inventory.size() > 3){
                Item itemDropped  = player.dropItem(3);
                items.add(itemDropped);
            }
        }
        if(Gdx.input.isKeyPressed(Keys.NUM_5)){
            if (player.inventory.size() > 4){
                Item itemDropped  = player.dropItem(4);
                items.add(itemDropped);
            }
        }
        if(Gdx.input.isKeyPressed(Keys.NUM_6)){
            if (player.inventory.size() > 5){
                Item itemDropped  = player.dropItem(5);
                items.add(itemDropped);
            }
        }
        if(Gdx.input.isKeyPressed(Keys.NUM_7)){
            if (player.inventory.size() > 6){
                Item itemDropped  = player.dropItem(6);
                items.add(itemDropped);
            }
        }
        if(Gdx.input.isKeyPressed(Keys.NUM_8)){
            if (player.inventory.size() > 7){
                Item itemDropped  = player.dropItem(7);
                items.add(itemDropped);
            }
        }
        if(Gdx.input.isKeyPressed(Keys.NUM_9)){
            if (player.inventory.size() > 8){
                Item itemDropped  = player.dropItem(8);
                items.add(itemDropped);
            }
        }

        player.update(map);
        for(Monster monster : monsters)
            monster.update(player);
        for (Item item : items) {
            item.update(player, game.batch);
        }
        for(Item coin : coins){
            coin.update(player,game.batch);
        }


        if(player.x == exitPos[0] && player.y == exitPos[1]){
            if(player.hasKey()) {
                player.inventory.removeIf(item -> item.name.equals("key"));
                this.loadNextMap();
            }else{
                if (exitPos[0]*32 - 80 >= 0 )
                    game.font.draw(game.batch, "Pick up the key to open the door ! " , exitPos[0]*32 - 80, 800 - exitPos[1]*32 + 50);
                else
                    game.font.draw(game.batch, "Pick up the key to open the door ! " , 0, 800 - exitPos[1]*32 + 50);
            }
        }


        game.batch.end();

        if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
            game.setScreen(new MainMenuScreen(game));
            music.stop();
        }
        if(Gdx.input.isKeyPressed(Keys.M)){
            if(music.isPlaying())
                music.pause();
            else
                music.play();
        }

        if(player.getHealth()<=0){
            music.stop();
            game.setScreen(new GameOverScreen(game));
        }
    }

    public void loadNextMap(){

        level++;

        int[] playerPos = new int[2];
        if(level == 1) {
            playerPos[0] = ThreadLocalRandom.current().nextInt(1, 39);
            playerPos[1] = ThreadLocalRandom.current().nextInt(1, 24);
        }
        else{
            playerPos = exitPos;
        }
        map = new Map(40,25, level, playerPos);
        player.x = playerPos[0];
        player.y = playerPos[1];
        monsters = map.placeMonsters(playerPos, level);
        this.spawns = new ArrayList<>();
        for(Monster monster : monsters){
            Sprite spawn = new Sprite(new Texture(Gdx.files.internal("spawn-monster.png")));
            spawn.setSize(32,32);
            spawn.setPosition(monster.x *32,800 - monster.y*32);
            spawns.add(spawn);
        }
        items = map.placeItems(playerPos, level);
        coins = map.placeGold(playerPos, level);

        exitPos = map.placeExit(playerPos);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        music.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    @Override
    public void dispose() {
        dropSound.dispose();
        music.dispose();
    }

}
