package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class Projectile extends Entity{

    String direction;
    long lastMoveTime;
    boolean active;

    public Projectile(int x, int y, String name, String direction) {
        super(x, y, name);
        this.direction = direction;
        this.lastMoveTime = TimeUtils.nanoTime();
        this.active = true;
    }

    public void update(ArrayList<Monster> monsters){

        if(this.x < 2 || this.x > 37 || this.y < 2 || this.y > 22){
            this.active = false;
            this.dispose();
        }

        if(TimeUtils.nanoTime() - lastMoveTime > 10000000 && this.active){
            switch (direction){
                case "up":
                    this.y -= 1;
                    break;
                case "down":
                    this.y += 1;
                    break;
                case "left":
                    this.x -= 1;
                    break;
                case "right":
                    this.x += 1;
                    break;
            }
            this.lastMoveTime = TimeUtils.nanoTime();
            for(Monster monster : monsters){
                if(this.x == monster.x && this.y == monster.y){
                    monster.getAttacked(1);
                    this.active = false;
                    this.dispose();
                }
            }
        }
    }

    public void draw(SpriteBatch batch){
        if(this.active){
            super.draw(batch);
        }
    }
}
