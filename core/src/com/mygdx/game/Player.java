package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Player extends Entity {

    private int health;
    private boolean invulnerable;
    public boolean attacking;
    public ArrayList<Item> inventory;
    public int gold;
    public Rectangle attackArea;
    private final Texture heartTex;

    private long lastMoveTime;

    public Player(int x, int y) {
        super(x,y,"player");
        this.attackArea = new Rectangle();
        this.attackArea.width = 0;
        this.attackArea.height = 0;
        this.gold = 0;
        this.attackArea.x = this.x;
        this.attackArea.y = this.y;
        this.health = 10;
        this.inventory = new ArrayList<>();
        this.invulnerable = false;
        this.attacking = false;
        this.heartTex = new Texture(Gdx.files.internal("heart.png"));
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

        if (TimeUtils.nanoTime() - lastMoveTime > 150000000) {
            lastMoveTime = TimeUtils.nanoTime();
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && map.tiles[x - 1][y].getMaterial()!= Materials.WALL) {
                this.x -= 1;
                this.attackArea.x -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && map.tiles[x + 1][y].getMaterial()!= Materials.WALL) {
                this.x += 1;
                this.attackArea.x += 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) && map.tiles[x][y - 1].getMaterial()!= Materials.WALL) {
                this.y -= 1;
                this.attackArea.y -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && map.tiles[x][y + 1].getMaterial()!= Materials.WALL ) {
                this.y += 1;
                this.attackArea.y += 1;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && this.hasSword()) {
            this.attacking = true;
            this.sprite.setColor(0, 1, 0, 1);
            this.attackArea.width = 128;
            this.attackArea.height = 128;
            CompletableFuture.delayedExecutor(500, TimeUnit.MILLISECONDS).execute(() -> this.attacking = false);
        } else {
            this.sprite.setColor(1, 1, 1, 1);
            this.attackArea.width = 0;
            this.attackArea.height = 0;
        }
    }

    public void draw(SpriteBatch batch){
        this.sprite.setPosition(this.x*32, 800-this.y*32);
        this.sprite.draw(batch);
        //Display the life points
        for(int i = 0; i<this.health; i++){
            batch.draw(heartTex, i*32 + 15, 850, 32,32);
        }

    }

    public void getAttacked(int damage){
        if(!invulnerable){
            System.out.println("Player got attacked !");
            this.sprite.setColor(1,0,0,1);
            this.health -= damage;
            System.out.println("New HP:" + this.health);
            if(this.health <= 0){
                System.out.println("Game Over");
            }
            this.invulnerable = true;

            CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> this.invulnerable = false);
        }
    }

    public Item dropItem(int i){
        Item item = this.inventory.get(i);
        if (TimeUtils.nanoTime() - lastMoveTime > 150000000) {
            item = this.inventory.get(i);
            item.x = this.x;
            item.y = this.y;
            item.pickable = true;
            item.sprite.setSize(32, 32);
            this.inventory.remove(i);
            return item;
        }
        return item;
    }

    public boolean hasSword(){
        for(Item item : this.inventory){
            if(item.name.equals("sword")){
                return true;
            }
        }
        return false;
    }

    public boolean hasKey(){
        for(Item item : this.inventory){
            if(item.name.equals("key")){
                return true;
            }
        }
        return false;
    }

    public boolean hasGold(){
        return this.gold > 0;
    }
}
