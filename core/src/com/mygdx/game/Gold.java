package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Gold{

    public Sprite sprite;
    public int x;
    public int y;
    public boolean pickable;
    protected BitmapFont font;

    public Gold(int x, int y) {
        this.x = x;
        this.y = y;
        this.sprite = new Sprite(new Texture(Gdx.files.internal( "gold.png")));
        this.sprite.setSize(26,26);
        this.pickable = true;
        this.font = new BitmapFont();
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

    public void draw(SpriteBatch batch) {
        this.sprite.setPosition(this.x * 32 + 3, 800 - this.y*32 + 3);
        this.sprite.draw(batch);
    }

}
