package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {
    Materials material;
    Texture texture;

    BitmapFont font;

    public Tile(Materials material, Texture texture) {
        this.material = material;
        this.texture = texture;
        this.font = new BitmapFont();
    }

    public Materials getMaterial() {
        return material;
    }

    public void draw(SpriteBatch batch, int x, int y) {
        batch.draw(texture, x, y);
        //this.font.draw(batch, "x = "+x+"; y = "+ y, x, y);
    }

    public void draw(SpriteBatch batch, int x, int y, float alpha) {
        batch.setColor(1, 1, 1, alpha);
        batch.draw(texture, x, y);
        batch.setColor(1, 1, 1, 1);
        //this.font.draw(batch, "x = "+x+"; y = "+ y, x, y);
    }
}
