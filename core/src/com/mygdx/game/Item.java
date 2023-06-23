package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item extends Entity{

    public int cost;
    protected boolean displayText;
    protected boolean pickable;

    private boolean itemInInventory;


    public Item(String name, int cost, int x, int y) {

        super(x,y,name);
        this.cost = cost;
        this.pickable=true;
        this.displayText=true;
        this.itemInInventory = false;
    }

    public Item(Item i){
        super(i.x,i.y,i.name);
        this.cost = i.cost;
        this.pickable = i.pickable;
        this.displayText = i.displayText;
        this.itemInInventory = i.itemInInventory;
    }

    public void update(Player player) {
        if(!this.pickable){
            return;
        }
        if(Math.abs(player.x - this.x) <= 1 && Math.abs(player.y - this.y) <= 1){
            displayText = true;

            itemInInventory = checkIfInInventory(player);

            if(Gdx.input.isKeyPressed(Input.Keys.P)){

                    if(!this.itemInInventory) {
                        player.inventory.add(this);
                        Gdx.audio.newSound(Gdx.files.internal("sounds/04_sack_open_" + (int)(Math.random()*2+1) + ".wav")).play(0.8f);
                        System.out.println("Item Collected");
                        this.dynamic_light = false;
                        this.x = -1000;
                        this.y = -1000;
                        this.pickable = false;
                    }
                    this.displayText = false;
            }
        }else {
            displayText = false;
        }
    }

    public boolean checkIfInInventory(Player player){

        if(this.name.equals("sword")||this.name.equals("axe")) {
            for (int i = 0; i < player.inventory.size(); i++) {
                if (player.inventory.get(i).name.equals("sword") || player.inventory.get(i).name.equals("axe")){
                    return true;
                }
            }
        }else{
            for (int i = 0; i < player.inventory.size(); i++) {
                if (player.inventory.get(i).name.equals(this.name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void draw(Player player,SpriteBatch batch, BitmapFont font ) {
        if(this.displayText){
            if(this.checkIfInInventory(player)){
                font.draw(batch, this.name + " : Already have item of same type in inventory", x * 32, 800 - y*32 + 64);
            }
            else{
                font.draw(batch, this.name + " : Press P to pickup", x * 32, 800 - y*32 + 64);
            }

        }
        super.draw(batch);
    }
}
