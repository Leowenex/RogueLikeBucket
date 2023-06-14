package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Monster extends Entity{

    private int health =2;
    private boolean alive;

    private long lastMoveTime;

    private boolean invulnerable;

    public Monster(int x, int y) {
        super(x,y,"player");
        this.alive = true;
        lastMoveTime = TimeUtils.nanoTime();
        this.invulnerable = false;
    }

    public void update(Player player) {
        if(!this.isAlive()){
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
            player.getAttacked(2);
        }

        if(Math.abs(player.x - this.x) <= 1 && Math.abs(player.y - this.y) <= 1 && player.attacking){
            this.getAttacked(player.getActiveWeaponDamage());
        }

    }

    public void getAttacked(int damage){
        if(!invulnerable){
            this.sprite.setColor(1,0,0,1);
            this.health -= damage;
            if(this.health <= 0){
                this.x = 0;
                this.y = 0;
                this.sprite.setColor(0, 0, 0, 0);
                this.alive = false;
            }
            this.invulnerable = true;

            CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> this.invulnerable = false);
        }
    }

    public boolean isAlive(){
        return this.alive;
    }

}
