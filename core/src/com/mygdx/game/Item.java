package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item extends Entity{

    public int cost;
    protected boolean displayText;
    protected boolean pickable;


    public Item(String name, int cost, int x, int y) {

        super(x,y,name);
        this.cost = cost;
        this.pickable=true;
        this.displayText=true;
    }

    public void update(Player player, SpriteBatch batch, BitmapFont font) {
        if(!this.pickable){
            return;
        }
        if(Math.abs(player.x - this.x) <= 1 && Math.abs(player.y - this.y) <= 1){
            displayText = true;

            if(Gdx.input.isKeyPressed(Input.Keys.P)){
                if(this.name.equals("key")){
                    this.x = 39;
                    this.y = -1;
                    this.dynamic_light = false;
                    this.pickable = false;
                    this.displayText = false;
                    this.sprite.setSize(40,40);
                    this.sprite.setPosition(1240,850);
                    player.hasKey = true;
                }else{
                    boolean ItemInInventory = false;
                    if(this.name.equals("sword")||this.name.equals("axe")) {
                        for (int i = 0; i < player.inventory.size(); i++) {
                            if (player.inventory.get(i).name.equals("sword") || player.inventory.get(i).name.equals("axe")){
                                ItemInInventory = true;
                                break;
                            }
                        }
                    }else{
                        for (int i = 0; i < player.inventory.size(); i++) {
                            if (player.inventory.get(i).name.equals(this.name)) {
                                ItemInInventory = true;
                                break;
                            }
                        }
                    }
                    if(!ItemInInventory) {
                        player.inventory.add(this);
                        System.out.println("Item Collected");
                        this.dynamic_light = false;
                        this.x = -1000;
                        this.y = -1000;
                        this.pickable = false;
                    }else{
                        font.draw(batch, "You already have this item or an item of the same type in your inventory.", 550,870);
                    }
                    this.displayText = false;
                }
            }
        }else {
            displayText = false;
        }
    }

    public void draw(SpriteBatch batch, BitmapFont font ) {
        this.sprite.setPosition(x * 32, 800 - y*32);
        this.sprite.setColor(1,1,1,dynamic_light?Player.computeLight(this.x, this.y):1);
        this.sprite.draw(batch);
        if(this.displayText){
            font.draw(batch, this.name + " : Press P to pickup", x * 32, 800 - y*32 + 64);
        }
    }
}
