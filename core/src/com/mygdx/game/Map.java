package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Map {

    private final Tile[][] tiles;
    public int width;
    public int height;
    public String[] weapons = {"sword","axe"};
    public String[] armors = {"chestplate","glove","boots"};
    public String[] potions = {"health_potion","mana_potion"};
    public String[] spells = {"fire_spell","ice_spell","nature_spell"};

    public boolean isDungeon;


    public Map(int width, int height, int[] playerPos, boolean isDungeon) {
        this.width = width;
        this.height = height;

        this.isDungeon = isDungeon;


        tiles = new Tile[width][height];

        if(isDungeon){
            generateDungeon(playerPos);
        }else{
            generateField(playerPos);
        }
    }

    public void generateDungeon( int[] playerPos){
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(Materials.AIR, new Texture(Gdx.files.internal("textures/sol.png")));
            }
        }

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(i==0 || i== width-1 || j==0 || j== height-1){
                    tiles[i][j] = new Tile(Materials.WALL, new Texture(Gdx.files.internal("textures/mur.png")));
                }
            }

        }

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                tiles[x][y] = Math.random() < 0.3 ? new Tile(Materials.WALL, new Texture(Gdx.files.internal("textures/mur.png"))) : new Tile(Materials.AIR, new Texture(Gdx.files.internal("textures/sol.png")));
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
                    tiles[i][j] = new Tile(Materials.AIR, new Texture(Gdx.files.internal("textures/sol.png")));
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
                    tiles[i][j] = new Tile(Materials.WALL, new Texture(Gdx.files.internal("textures/mur.png")));
                }
            }
        }

        //Elimination des zones de murs autour du joueur
        for(int i = playerPos[0]-1; i<playerPos[0]+2; i++){
            for(int j = playerPos[1]-1; j< playerPos[1]+2; j++){
                if(i!=0 && i!= width-1 && j!=0 && j!= height-1) {
                    tiles[i][j] = new Tile(Materials.AIR, new Texture(Gdx.files.internal("textures/sol.png")));
                }
            }
        }
    }

    public void generateField(int[] playerPos){
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tiles[i][j] = new Tile(Materials.AIR, new Texture(Gdx.files.internal("textures/grass.png")));
            }
        }

        tiles[playerPos[0]][playerPos[1]] = new Tile(Materials.AIR, new Texture(Gdx.files.internal("textures/sol.png")));
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public ArrayList<Monster> placeMonsters(int[] playerPos, int difficulty){
        ArrayList<Monster> monsters = new ArrayList<>();

        if(difficulty == 0){
            return monsters;
        }

        for(int i = 0; i <difficulty; i++){
            int[] pos = findRandomPos(playerPos);
            monsters.add(new Monster(pos[0],pos[1]));
        }

        return monsters;

    }

    public ArrayList<Item> placeItems(int[] playerPos, int difficulty){
        ArrayList<Item> items = new ArrayList<>();

        if(difficulty == 0){
            return items;
        }

        int[] pos;

        // weapon
        pos = findRandomPos(playerPos);

        int randomidx = ThreadLocalRandom.current().nextInt(0, 2);
        if(weapons[randomidx].equals("sword"))
            items.add(new Weapon(weapons[randomidx], 10, pos[0], pos[1],1));
        else
            items.add(new Weapon(weapons[randomidx], 10, pos[0], pos[1],2));

        //armor
        pos = findRandomPos(playerPos);
        randomidx = ThreadLocalRandom.current().nextInt(0, 3);
        items.add(new Item(armors[randomidx], 25, pos[0], pos[1]));

        //potion
        pos = findRandomPos(playerPos);
        randomidx = ThreadLocalRandom.current().nextInt(0, 2);
        items.add(new Item(potions[randomidx], 15, pos[0], pos[1]));

        //spell
        pos = findRandomPos(playerPos);
        randomidx = ThreadLocalRandom.current().nextInt(0, 3);
        items.add(new Item(spells[randomidx], 50, pos[0], pos[1]));

        return items;

    }

    public Key placeKey(int[] playerPos){
        int[] pos = findRandomPos(playerPos);
        return new Key(pos[0], pos[1]);
    }

    public ArrayList<Gold> placeGold(int[] playerPos, int difficulty){
        ArrayList<Gold> gold = new ArrayList<>();

        for(int i = 0; i < 5 * difficulty ; i++){
            int [] pos = findRandomPos(playerPos);
            gold.add(new Gold(pos[0], pos[1]));
        }

        return gold;

    }

    public int[] placeExit(int[] playerPos){
        int[] pos = findRandomPos(playerPos);
        tiles[pos[0]][pos[1]].texture = new Texture(Gdx.files.internal("textures/porte.png"));
        return pos;

    }

    public int[] findRandomPos(int[] playerPos){

        int randomX = ThreadLocalRandom.current().nextInt(1, width-1);
        int randomY = ThreadLocalRandom.current().nextInt(1, height-1);

        while(tiles[randomX][randomY].getMaterial() != Materials.AIR || (Math.abs(randomX - playerPos[0]) + Math.abs(randomY - playerPos[1]) <20)){
            randomX = ThreadLocalRandom.current().nextInt(1, width-1);
            randomY = ThreadLocalRandom.current().nextInt(1, height-1);
        }
        return new int[]{randomX, randomY};

    }

    public void draw(SpriteBatch batch) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                    tiles[i][j].draw(batch, i * 32, 800 - j * 32, isDungeon?Player.computeLight(i, j):1);
            }
        }
    }

    public void dispose() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tiles[i][j].dispose();
            }
        }
    }

}
