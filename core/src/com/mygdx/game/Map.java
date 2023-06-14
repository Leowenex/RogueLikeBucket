package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Map {

    public Tile[][] tiles;
    public int width;
    public int height;

    public Map(int width, int height, int difficulty, int[] playerPos) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(Materials.AIR, new Texture(Gdx.files.internal("sol.png")));
            }
        }

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(i==0 || i== width-1 || j==0 || j== height-1){
                    tiles[i][j] = new Tile(Materials.WALL, new Texture(Gdx.files.internal("mur.png")));
                }
            }

        }

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                tiles[x][y] = Math.random() < 0.3 ? new Tile(Materials.WALL, new Texture(Gdx.files.internal("mur.png"))) : new Tile(Materials.AIR, new Texture(Gdx.files.internal("sol.png")));
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
                    tiles[i][j] = new Tile(Materials.AIR, new Texture(Gdx.files.internal("sol.png")));
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
                    tiles[i][j] = new Tile(Materials.WALL, new Texture(Gdx.files.internal("mur.png")));
                }
            }
        }

        tiles[playerPos[0]][playerPos[1]] = new Tile(Materials.AIR, new Texture(Gdx.files.internal("Stone.png")));


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

    public int[] findPlayerStart(){
        //TODO: Prendre en compte où le joueur est sorti du niveau précédent

        int randomX = ThreadLocalRandom.current().nextInt(1, width-1);
        int randomY = ThreadLocalRandom.current().nextInt(1, height-1);

        while(tiles[randomX][randomY].getMaterial() != Materials.AIR){
            randomX = ThreadLocalRandom.current().nextInt(1, width-1);
            randomY = ThreadLocalRandom.current().nextInt(1, height-1);
        }

        return new int[]{randomX, randomY};

    }

    public ArrayList<Monster> placeMonsters(int[] playerPos, int difficulty){
        //TODO: Prendre en compte les futur différents types de monstres
        ArrayList<Monster> monsters = new ArrayList<Monster>();

        for(int i = 0; i <difficulty; i++){
            int randomX = ThreadLocalRandom.current().nextInt(1, width-1);
            int randomY = ThreadLocalRandom.current().nextInt(1, height-1);

            while(tiles[randomX][randomY].getMaterial() != Materials.AIR || (randomX == playerPos[0] && randomY == playerPos[1])){
                randomX = ThreadLocalRandom.current().nextInt(1, width-1);
                randomY = ThreadLocalRandom.current().nextInt(1, height-1);
            }
            monsters.add(new Monster(randomX, randomY));
        }

        return monsters;

    }

    public ArrayList<Item> placeItems(int[] playerPos, int difficulty){
        //TODO: Faire en sorte que la porte apparaisse loin du joueur
        ArrayList<Item> items = new ArrayList<Item>();

        for(int i = 0; i <difficulty; i++){
            int randomX = ThreadLocalRandom.current().nextInt(1, width-1);
            int randomY = ThreadLocalRandom.current().nextInt(1, height-1);

            while(tiles[randomX][randomY].getMaterial() != Materials.AIR || (randomX == playerPos[0] && randomY == playerPos[1])){
                randomX = ThreadLocalRandom.current().nextInt(1, width-1);
                randomY = ThreadLocalRandom.current().nextInt(1, height-1);
            }

            items.add(new Weapon("sword", 10, randomX, randomY,1));
        }

        int randomX = ThreadLocalRandom.current().nextInt(1, width-1);
        int randomY = ThreadLocalRandom.current().nextInt(1, height-1);

        while(tiles[randomX][randomY].getMaterial() != Materials.AIR || (randomX == playerPos[0] && randomY == playerPos[1])){
            randomX = ThreadLocalRandom.current().nextInt(1, width-1);
            randomY = ThreadLocalRandom.current().nextInt(1, height-1);
        }
        items.add(new Item("key", 0, randomX, randomY));

        return items;

    }

    public ArrayList<Item> placeGold(int[] playerPos, int difficulty){
        //Potentiellement différents types de pièces ?
        ArrayList<Item> gold = new ArrayList<Item>();

        for(int i = 0; i < 5 * difficulty ; i++){
            int randomX = ThreadLocalRandom.current().nextInt(1, width-1);
            int randomY = ThreadLocalRandom.current().nextInt(1, height-1);

            while(tiles[randomX][randomY].getMaterial() != Materials.AIR || (randomX == playerPos[0] && randomY == playerPos[1])){
                randomX = ThreadLocalRandom.current().nextInt(1, width-1);
                randomY = ThreadLocalRandom.current().nextInt(1, height-1);
            }
            gold.add(new Item("gold",1,randomX, randomY));
        }

        return gold;

    }

    public int[] placeExit(int[] playerPos){
        //TODO: Faire en sorte que la porte apparaisse loin du joueur
        int randomX = ThreadLocalRandom.current().nextInt(1, width-1);
        int randomY = ThreadLocalRandom.current().nextInt(1, height-1);

        while(tiles[randomX][randomY].getMaterial() != Materials.AIR || (randomX == playerPos[0] && randomY == playerPos[1])){
            randomX = ThreadLocalRandom.current().nextInt(1, width-1);
            randomY = ThreadLocalRandom.current().nextInt(1, height-1);
        }

        tiles[randomX][randomY].texture = new Texture(Gdx.files.internal("porte.png"));

        return new int[]{randomX, randomY};

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
