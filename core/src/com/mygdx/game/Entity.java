package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {
    int x;
    int y;

    boolean dynamic_light;

    Sprite sprite;
    String name;

    public Entity(int x, int y, String name){
        this.x = x;
        this.y =y;
        dynamic_light = true;
        this.name = name;
        this.sprite = new Sprite(new Texture(Gdx.files.internal("textures/"+name + ".png")));
        this.sprite.setSize(32, 32);

    }

    public void draw(SpriteBatch batch) {
        this.sprite.setPosition(this.x * 32, 800 - this.y * 32);
        this.sprite.setAlpha(dynamic_light?Player.computeLight(this.x, this.y):1);
        this.sprite.draw(batch);
    }

    public void dispose() {
        this.sprite.getTexture().dispose();
    }
}
