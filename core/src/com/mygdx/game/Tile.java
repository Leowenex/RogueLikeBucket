package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {
    Materials material;
    Texture texture;

    public Tile(Materials material, Texture texture) {
        this.material = material;
        this.texture = texture;
    }

    public Materials getMaterial() {
        return material;
    }

    public void draw(SpriteBatch batch, int x, int y, float alpha) {
        batch.setColor(1, 1, 1, alpha);
        batch.draw(texture, x, y);
        batch.setColor(1, 1, 1, 1);
    }

    public void dispose() {
        texture.dispose();
    }
}
