package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Monster {

    public int x;
    public int y;

    public Sprite sprite;

    private boolean alive;

    private long lastMoveTime;

    public Monster(int x, int y) {
        this.sprite = new Sprite(new Texture(Gdx.files.internal("bucket.png")));
        this.sprite.setSize(32, 32);
        this.sprite.setColor(1, 0, 0, 1);
        this.x = x;
        this.y = y;
        this.alive = true;
        lastMoveTime = TimeUtils.nanoTime();
    }

    public void update(Player player) {
        if(!this.alive){
            return;
        }
        if (TimeUtils.nanoTime() - lastMoveTime > 750000000) {
            lastMoveTime = TimeUtils.nanoTime();
            if (player.x - 1 > this.x) {
                this.x += 1;
            }
            if (player.x + 1 < this.x) {
                this.x -= 1;
            }
            if (player.y - 1 > this.y) {
                this.y += 1;
            }
            if (player.y + 1 < this.y) {
                this.y -= 1;
            }
        }

        if((Math.abs(player.x - this.x) <= 1 && player.y == this.y)||
                (player.x == this.x &&Math.abs(player.y - this.y) <= 1)) {
            player.getAttacked(1);
        }

        if(Math.abs(player.x - this.x) <= 1 && Math.abs(player.y - this.y) <= 1 && player.attacking){
            System.out.println("Monster Hit");
            this.x = 0;
            this.y = 0;
            this.sprite.setColor(0, 0, 0, 0);
            this.alive = false;
        }
    }

    public void draw(SpriteBatch batch){
        this.sprite.setPosition(this.x * 32, 800-this.y*32);
        this.sprite.draw(batch);
    }

    public boolean isAlive(){
        return this.alive;
    }

}
