package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Item {

    public String name;
    public int effectValue;
    public String effectType;
    public int cost;
    public Sprite sprite;
    public Rectangle position;

    public Rectangle holdArea;

    private boolean displayText;
    private boolean pickable;

    private BitmapFont font;


    public Item(String name, int effectValue, String effectType, int cost) {
        this.name = name;
        this.effectValue = effectValue;
        this.effectType = effectType;
        this.cost = cost;
        this.sprite = new Sprite(new Texture(Gdx.files.internal("droplet.png")));
        this.sprite.setColor(0, 0, 1, 1);
        this.sprite.setSize(32, 32);
        this.position = new Rectangle();
        this.position.width = 32;
        this.position.height = 32;
        this.position.x = 200;
        this.position.y = 200;
        this.holdArea = new Rectangle();
        this.holdArea.width = 128;
        this.holdArea.height = 128;
        this.holdArea.x = this.position.x - this.holdArea.width / 2;
        this.holdArea.y = this.position.y - this.holdArea.height / 2;
        this.pickable=true;
        this.displayText=true;

        this.font = new BitmapFont();
    }

    public void update(Player player) {
        if(!this.pickable){
            return;
        }
        if(this.holdArea.overlaps(player.position)){

            displayText = true;

            if(Gdx.input.isKeyPressed(Input.Keys.P)){
                player.inventory.add(this);
                System.out.println("Item Collected");
                this.sprite.setColor(0, 0, 0, 0);
                this.position.x = -1000;
                this.position.y = -1000;
                this.pickable = false;
            }
        }else {
            displayText = false;
        }
    }

    public void draw(SpriteBatch batch) {
        this.sprite.setPosition(this.position.x, this.position.y);
        this.sprite.draw(batch);
        if(this.displayText){
            this.font.draw(batch, this.name + " : Press P to pickup", this.position.x, this.position.y + 64);
        }
    }


}
