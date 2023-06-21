package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class NPC extends Entity{

    String job;

    public void update(Player player){

    }

    public NPC(int x, int y, String name,String job) {
        super(x, y, job);
        dynamic_light = false;
        this.job = job;
        this.sprite = new Sprite(new Texture(Gdx.files.internal("textures/"+job + ".png")));
    }
}
