package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Player {

    public Rectangle position;
    public Sprite sprite;

    private int health;

    public ArrayList<Item> inventory;

    public Rectangle attackArea;

    public Player() {
        this.sprite = new Sprite(new Texture(Gdx.files.internal("bucket.png")));
        this.position = new Rectangle();
        this.position.width = 64;
        this.position.height = 64;
        this.position.x = 800 / 2 - 64 / 2;
        this.position.y = 20;
        this.attackArea = new Rectangle();
        this.attackArea.width = 0;
        this.attackArea.height = 0;
        this.attackArea.x = this.position.x;
        this.attackArea.y = this.position.y;
        this.health = 100;
        this.inventory = new ArrayList<Item>();
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            this.position.x -= 400 * Gdx.graphics.getDeltaTime();
            this.attackArea.x -= 400 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.position.x += 400 * Gdx.graphics.getDeltaTime();
            this.attackArea.x += 400 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.position.y += 400 * Gdx.graphics.getDeltaTime();
            this.attackArea.y += 400 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.position.y -= 400 * Gdx.graphics.getDeltaTime();
            this.attackArea.y -= 400 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && this.hasSword()) {


            this.sprite.setColor(0, 1, 0, 1);
            this.attackArea.width = 128;
            this.attackArea.height = 128;
        } else {
            this.sprite.setColor(1, 1, 1, 1);
            this.attackArea.width = 0;
            this.attackArea.height = 0;
        }
    }

    public void draw(SpriteBatch batch){
        this.sprite.setPosition(this.position.x, this.position.y);
        this.sprite.draw(batch);
    }

    public void getAttacked(int damage){
        this.health -= damage;
        if(this.health <= 0){
            System.out.println("Game Over");
            Gdx.app.exit();
        }
    }

    public boolean hasSword(){
        for(Item item : this.inventory){
            if(item.name.equals("sword")){
                return true;
            }
        }
        return false;
    }
}
