package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item {

    public String name;
    public int effectValue;
    public String effectType;
    public int cost;
    public Sprite sprite;
    public int x;
    public int y;

    protected boolean displayText;
    protected boolean pickable;
    protected boolean inInventory;

    protected BitmapFont font;


    public Item(String name, int effectValue, String effectType, int cost, int x, int y) {
        this.name = name;
        this.effectValue = effectValue;
        this.effectType = effectType;
        this.cost = cost;
        this.sprite = new Sprite(new Texture(Gdx.files.internal(name + ".png")));
        this.sprite.setSize(32, 32);

        this.x = x;
        this.y = y;

        this.pickable=true;
        this.displayText=true;
        this.inInventory=false;

        this.font = new BitmapFont();
    }

    public void update(Player player, SpriteBatch batch) {
        if(!this.pickable){
            return;
        }
        if(Math.abs(player.x - this.x) <= 1 && Math.abs(player.y - this.y) <= 1){

            displayText = true;

            if(Gdx.input.isKeyPressed(Input.Keys.P)){
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
                    this.inInventory = true;
                    this.displayText = false;
                }else{
                    this.font.draw(batch, "You already have this item in your inventory.", 580,870);
                    this.displayText = false;
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
