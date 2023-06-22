package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Gold extends Entity{

    public boolean pickable;

    public Gold(int x, int y) {
        super(x, y, "gold");
        this.sprite.setSize(26,26);
        this.pickable = true;
    }

    public void update(Player player) {
        if(!this.pickable){
            return;
        }
        if(player.x == this.x && player.y == this.y) {
                player.gold += 1;
                System.out.println("Gold Collected");
                Gdx.audio.newSound(Gdx.files.internal("sounds/coin/coin." + (int)(Math.random()*11+1) + ".ogg")).play(0.5f);
                this.x = -1000;
                this.y = -1000;
                this.pickable = false;
        }
    }

}
