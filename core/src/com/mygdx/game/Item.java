package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item extends Entity{

    public int cost;

    protected boolean displayText;
    protected boolean pickable;
    protected BitmapFont font;


    public Item(String name, int cost, int x, int y) {

        super(x,y,name);
        this.cost = cost;
        this.pickable=true;
        this.displayText=true;
        this.font = new BitmapFont();
    }

    public void update(Player player, SpriteBatch batch) {
        if(!this.pickable){
            return;
        }
        if(Math.abs(player.x - this.x) <= 1 && Math.abs(player.y - this.y) <= 1){
            displayText = true;

            if(Gdx.input.isKeyPressed(Input.Keys.P)){
                if(this.name.equals("key")){
                    this.x = 39;
                    this.y = -1;
                    this.pickable = false;
                    this.displayText = false;
                    this.sprite.setSize(40,40);
                    this.sprite.setPosition(1240,850);
                    player.hasKey = true;
                }else{
                    boolean ItemInInventory = false;
                    for (int i = 0; i < player.inventory.size(); i++) {
                        if (player.inventory.get(i).name.equals(this.name)) {
                            ItemInInventory = true;
                        }
                    }
                    if(!ItemInInventory) {
                        player.inventory.add(this);
                        System.out.println("Item Collected");
                        this.x = -1000;
                        this.y = -1000;
                        this.pickable = false;
                        this.displayText = false;
                    }else{
                        this.font.draw(batch, "You already have this item in your inventory.", 580,870);
                        this.displayText = false;
                    }
                }
            }
        }else {
            displayText = false;
        }
    }

    public void draw(SpriteBatch batch) {
        this.sprite.setPosition(this.x * 32, 800 - this.y*32);
        this.sprite.draw(batch);
        if(this.displayText){
            this.font.draw(batch, this.name + " : Press P to pickup", this.x * 32, 800 - this.y*32 + 64);
        }
    }
}
