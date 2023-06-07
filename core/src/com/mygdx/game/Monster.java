package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Monster {

    public Rectangle position;
    public Sprite sprite;

    private boolean alive;

    public Monster(float x, float y) {
        this.sprite = new Sprite(new Texture(Gdx.files.internal("bucket.png")));
        this.sprite.setColor(1, 0, 0, 1);
        this.position = new Rectangle();
        this.position.width = 64;
        this.position.height = 64;
        this.position.x = x;
        this.position.y = y;
        this.alive = true;
    }

    public void update(Player player) {
        if(!this.alive){
            return;
        }
        if(player.position.x - player.sprite.getWidth() > this.position.x){
            this.position.x += 100 * Gdx.graphics.getDeltaTime();
        }
        if(player.position.x + player.sprite.getWidth() < this.position.x){
            this.position.x -= 100 * Gdx.graphics.getDeltaTime();
        }
        if(player.position.y > this.position.y){
            this.position.y += 100 * Gdx.graphics.getDeltaTime();
        }
        if(player.position.y < this.position.y){
            this.position.y -= 100 * Gdx.graphics.getDeltaTime();
        }

        if(this.position.overlaps(player.attackArea) && player.attacking){
            System.out.println("Monster Hit");
            this.position.x = 0;
            this.position.y = 0;
            this.sprite.setColor(0, 0, 0, 0);
            this.alive = false;
        }

        if(this.position.overlaps(player.position)){
            player.getAttacked(1);
        }
    }

    public void draw(SpriteBatch batch){
        this.sprite.setPosition(this.position.x, this.position.y);
        this.sprite.draw(batch);
    }

}
