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

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                tiles[x][y] = Math.random() < 0.3 ? new Tile(Materials.WALL, new Texture(Gdx.files.internal("Wall.png"))) : new Tile(Materials.AIR, new Texture(Gdx.files.internal("Stone.png")));
            }
        }

        //Elimination des murs isolés

        for(int i = 1; i<width-1; i++){
            for(int j = 1; j< height-1; j++){
                int walls_around = 0;
                if(tiles[i-1][j].getMaterial() == Materials.AIR){
                    walls_around++;
                }
                if(tiles[i+1][j].getMaterial() == Materials.AIR){
                    walls_around++;
                }
                if(tiles[i][j-1].getMaterial() == Materials.AIR){
                    walls_around++;
                }
                if(tiles[i][j+1].getMaterial() == Materials.AIR){
                    walls_around++;
                }
                if(walls_around>=4){
                    tiles[i][j] = new Tile(Materials.AIR, new Texture(Gdx.files.internal("Stone.png")));
                }
            }
        }

        //Elimination des zones d'air entourées par des murs

        for(int i = 1; i<width-1; i++){
            for(int j = 1; j< height-1; j++){
                int walls_around = 0;
                if(tiles[i-1][j].getMaterial() == Materials.WALL){
                    walls_around++;
                }
                if(tiles[i+1][j].getMaterial() == Materials.WALL){
                    walls_around++;
                }
                if(tiles[i][j-1].getMaterial() == Materials.WALL){
                    walls_around++;
                }
                if(tiles[i][j+1].getMaterial() == Materials.WALL){
                    walls_around++;
                }
                if(walls_around>=3){
                    tiles[i][j] = new Tile(Materials.WALL, new Texture(Gdx.files.internal("Wall.png")));
                }
            }
        }


        // METHODE DU COURS
        /*
        Tile[][] tiles_copy = tiles.clone();

        for (int times = 0; times < 0; times++) {

            for (int x = 2; x < width - 2; x++) {
                for (int y = 2; y < height - 2; y++) {

                    int floors = 0;
                    int rocks = 0;

                    for (int ox = -1; ox < 2; ox++) {
                        for (int oy = -1; oy < 2; oy++) {
                            if (x + ox < 1 || x + ox >= width-1 || y + oy < 1 || y + oy >= height - 1)
                                continue;

                            if (tiles[x + ox][y + oy].getMaterial() == Materials.AIR)
                                floors++;
                            else
                                rocks++;
                        }
                    }
                    tiles_copy[x][y] = floors >= rocks ? new Tile(Materials.AIR, new Texture(Gdx.files.internal("Stone.png"))) : new Tile(Materials.WALL, new Texture(Gdx.files.internal("Wall.png")));
                }
            }

            tiles = tiles_copy.clone();

        }

         */

    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public int[] getPlayerPosition(int entrypoint){
        System.out.println("entrypoint : " + entrypoint);
        return new int[]{1, 1};
    }

    public void draw(SpriteBatch batch) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tiles[i][j].draw(batch, i * 32, 800-j * 32);
            }
        }
    }

    public void update() {

    }

}
