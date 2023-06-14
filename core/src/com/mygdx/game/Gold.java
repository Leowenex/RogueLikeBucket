package com.mygdx.game;

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
        if(player.x - this.x == 0 && player.y - this.y == 0) {
                player.gold += 1;
                System.out.println("Gold Collected");
                this.x = -1000;
                this.y = -1000;
                this.pickable = false;
        }
    }

}
