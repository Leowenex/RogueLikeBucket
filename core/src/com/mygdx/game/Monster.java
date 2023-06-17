package com.mygdx.game;

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

        if(Math.abs(player.x - this.x) <= 1 && Math.abs(player.y - this.y) <= 1){
            player.getAttacked(2);
        }

        if(Math.abs(player.x - this.x) <= 3 && Math.abs(player.y - this.y) <= 3 && player.attacking){
            this.getAttacked(player.getActiveWeaponDamage());
        }

    }

    public void getAttacked(int damage){
        if(!invulnerable){
            this.health -= damage;
            if(this.health <= 0){
                this.x = -1000;
                this.y = -1000;
                this.sprite.setColor(0, 0, 0, 0);
                this.alive = false;
            }
            this.invulnerable = true;

            CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> this.invulnerable = false);
        }
    }

    public void draw(SpriteBatch batch){
        if(!this.isAlive()){
            return;
        }
        if(this.invulnerable)
            this.sprite.setColor(1, 0.5f, 0.5f, 1);
        else
            this.sprite.setColor(1, 1, 1, 1);

        super.draw(batch);
    }

    public boolean isAlive(){
        return this.alive;
    }

}
