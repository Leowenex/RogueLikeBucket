package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Key extends Entity{

    public boolean pickable;

    public Key(int x, int y) {
        super(x, y, "key");
        this.pickable = true;
    }

    public void update(Player player) {
        if(!this.pickable){
            return;
        }
        if(player.x == this.x && player.y == this.y) {
            this.x = 39;
            this.y = -1;
            this.dynamic_light = false;
            this.pickable = false;
            this.sprite.setSize(40, 40);
            this.sprite.setPosition(1240, 850);
            player.hasKey = true;
        }
    }
}
