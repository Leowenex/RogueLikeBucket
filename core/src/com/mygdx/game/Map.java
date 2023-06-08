package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map {

    public Tile[][] tiles;
    public int width;
    public int height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(Materials.AIR, new Texture(Gdx.files.internal("Stone.png")));
            }
        }

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(i==0 || i== width-1 || j==0 || j== height-1){
                    tiles[i][j] = new Tile(Materials.WALL, new Texture(Gdx.files.internal("Wall.png")));
                }
            }
        }

    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void draw(SpriteBatch batch) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tiles[i][j].draw(batch, i * 32, 448-j * 32);
            }
        }
    }

    public void update() {

    }

}
