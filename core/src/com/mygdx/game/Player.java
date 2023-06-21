package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Player extends Entity {

    private static Player player;

    private static final int DUNGEON_VIEWRANGE = 30;

    public int health;
    public int mana;
    private boolean invulnerable;
    public boolean attacking;
    public boolean hasKey;
    public ArrayList<Item> inventory;
    public int gold;
    private final Texture heartTex;
    private final Texture manaTex;

    public String direction;

    private long lastMoveTime;

    private long lastAttackTime;

    public Player(int x, int y) {
        super(x,y,"player");

        this.gold = 0;
        this.health = 10;
        this.mana=10;

        this.inventory = new ArrayList<>();

        this.invulnerable = false;
        this.attacking = false;
        this.hasKey = false;

        this.heartTex = new Texture(Gdx.files.internal("textures/PV.png"));
        this.manaTex = new Texture(Gdx.files.internal("textures/PM.png"));

        this.lastMoveTime = TimeUtils.nanoTime();
        this.lastAttackTime = TimeUtils.nanoTime();

        this.direction = "down";

        Player.player = this;
    }

    public int getHealth() {
        return health;
    }

    public int getActiveWeaponDamage(){
        for(Item item : this.inventory){
            if(item instanceof Weapon){
                Weapon weapon = (Weapon)item;
                return weapon.damage;
            }
        }
        return 0;
    }

    public void update(Map map) {

        int move_delay = this.isInInventory("boots")? 100000000 : 150000000;

        if (TimeUtils.nanoTime() - lastMoveTime > move_delay) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && map.getTile(x - 1,y).getMaterial() != Materials.WALL) {
                lastMoveTime = TimeUtils.nanoTime();
                player.sprite = new Sprite(new Texture(Gdx.files.internal("textures/player_left.png")));
                player.sprite.setSize(16,32);
                player.direction = "left";
                this.x -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && map.getTile(x + 1,y).getMaterial() != Materials.WALL) {
                lastMoveTime = TimeUtils.nanoTime();
                player.sprite = new Sprite(new Texture(Gdx.files.internal("textures/player_right.png")));
                player.sprite.setSize(16,32);
                player.direction = "right";
                this.x += 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) && map.getTile(x,y - 1).getMaterial() != Materials.WALL) {
                lastMoveTime = TimeUtils.nanoTime();
                player.sprite = new Sprite(new Texture(Gdx.files.internal("textures/player_back.png")));
                player.sprite.setSize(16,32);
                player.direction = "up";
                this.y -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && map.getTile(x,y + 1).getMaterial() != Materials.WALL) {
                lastMoveTime = TimeUtils.nanoTime();
                player.sprite = new Sprite(new Texture(Gdx.files.internal("textures/player.png")));
                player.sprite.setSize(16,32);
                player.direction = "down";
                this.y += 1;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && (this.isInInventory("sword") || this.isInInventory("axe")) && canAttack()){

            this.attacking = true;
            Gdx.audio.newSound(Gdx.files.internal("sounds/07_human_atk_sword_" + (int)(Math.random()*2+1) + ".wav")).play(0.7f);
            this.sprite.setColor(0, 1, 0, 1);
            CompletableFuture.delayedExecutor(250, TimeUnit.MILLISECONDS).execute(() -> this.attacking = false);

        } else {

            this.sprite.setColor(1, 1, 1, 1);

        }

        if(Gdx.input.isKeyPressed(Input.Keys.V)){
            if(player.isInInventory("health_potion")){
                player.inventory.removeIf(item -> item.name.equals("health_potion"));
                player.health = 10;
                Gdx.audio.newSound(Gdx.files.internal("sounds/08_human_charge_1.wav")).play(0.7f);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.B)){
            if(player.isInInventory("mana_potion")){
                player.inventory.removeIf(item -> item.name.equals("mana_potion"));
                player.mana = 10;
                Gdx.audio.newSound(Gdx.files.internal("sounds/08_human_charge_2.wav")).play(0.7f);
            }
        }

    }

    public void draw(SpriteBatch batch){
        this.sprite.setPosition(this.x*32, 800-this.y*32);
        this.sprite.draw(batch);
        //Display the life points
        for(int i = 0; i<this.health; i++){
            batch.draw(heartTex, i*32 + 15, 850, 32,32);
        }

        //Display the mana points
        for(int i = 0; i<this.mana; i++){
            batch.draw(manaTex, i*32 + 365, 850, 32,32);
        }

    }

    public void getAttacked(int damage){
        if(!invulnerable){
            System.out.println("Player got attacked !");
            this.sprite.setColor(1,0,0,1);
            if(this.isInInventory("chestplate")){
                this.health -= damage/2;
            }
            else{
                this.health -= damage;
            }
            Gdx.audio.newSound(Gdx.files.internal("sounds/11_human_damage_" + (int)(Math.random()*2+1) + ".wav")).play(0.8f);
            System.out.println("New HP:" + this.health);
            if(this.health <= 0){
                System.out.println("Game Over");
            }
            this.invulnerable = true;

            CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> this.invulnerable = false);
        }
    }

    public Item dropItem(int i){
        if (TimeUtils.nanoTime() - lastMoveTime > 150000000) {
            lastMoveTime = TimeUtils.nanoTime();
            Item item = this.inventory.get(i);
            item.x = this.x;
            item.y = this.y;
            item.pickable = true;
            item.dynamic_light = true;
            item.sprite.setSize(32, 32);
            this.inventory.remove(i);
            return item;
        }
        return null;
    }

    public boolean isInInventory(String name){
        for(Item i : this.inventory){
            if(i.name.equals(name)){
                return true;
            }
        }
        return false;
    }

    public boolean canAttack(){
        int delay = this.isInInventory("glove")? 500000000 : 1000000000;
        if(TimeUtils.nanoTime() - lastAttackTime > delay){
            lastAttackTime = TimeUtils.nanoTime();
            return true;
        }
        return false;
    }

    public void dispose(){
        this.sprite.getTexture().dispose();
        this.heartTex.dispose();
        this.manaTex.dispose();
        this.inventory.forEach(Item::dispose);
    }

    public static float computeLight(int x, int y){
        int player_x = Player.player.x;
        int player_y = Player.player.y;
        return (float) MathUtils.clamp((Player.DUNGEON_VIEWRANGE /(Math.pow(Math.abs(x - player_x),2) + Math.pow(Math.abs(y - player_y),2))),0,1);
    }
}

