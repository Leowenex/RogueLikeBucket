package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NPC extends Entity{

    String job;
    boolean pauvre;
    protected boolean displayText;

    public static ArrayList<Item> ItemsList= new ArrayList<>(List.of(
            new Weapon("sword", 10, -10,-10,1),
            new Weapon("axe", 10, -10,-10,2),
            new Item("chestplate", 25, -10,-10),
            new Item("glove", 25, -10,-10),
            new Item("boots", 25, -10,-10),
            new Item("health_potion", 15, -10,-10),
            new Item("mana_potion", 15, -10,-10),
            new Item("fire_spell", 50, -10,-10),
            new Item("ice_spell", 50, -10,-10),
            new Item("nature_spell", 50, -10,-10)
    ));

    private ArrayList<Item> shopItems = new ArrayList<>();

    public NPC(int x, int y, String name,String job) {
        super(x, y, job);
        dynamic_light = false;
        this.job = job;
        pauvre = false;
        this.sprite = new Sprite(new Texture(Gdx.files.internal("textures/"+job + ".png")));
    }

    public void update(Player player) {


        if(Math.abs(player.x - this.x) <= 1 && Math.abs(player.y - this.y) <= 1){

            if(!displayText){
                displayText = true;
                Item item;

                for(int i=0; i<(9-player.inventory.size());i++){
                    do {
                        item = new Item(ItemsList.get(ThreadLocalRandom.current().nextInt(0, 10)));

                    }while (

                            item.checkIfInInventory(player)
                    );
                    shopItems.add(item);
                }
            }


            if(Gdx.input.isKeyPressed(Input.Keys.Q) && shopItems.size()>0){
                if(player.gold >= shopItems.get(0).cost){
                    player.inventory.add(new Item(shopItems.get(0)));
                    player.gold -= shopItems.get(0).cost;
                    pauvre=false;
                    displayText=false;
                    shopItems.clear();
                }
                else {
                    pauvre = true;
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.W) && shopItems.size()>1){
                if(player.gold >= shopItems.get(1).cost){
                    player.inventory.add(new Item(shopItems.get(1)));
                    player.gold -= shopItems.get(1).cost;
                    pauvre=false;
                    displayText=false;
                    shopItems.clear();
                }
                else {
                    pauvre = true;
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.E) && shopItems.size()>2){
                if(player.gold >= shopItems.get(2).cost){
                    player.inventory.add(new Item(shopItems.get(2)));
                    player.gold -= shopItems.get(2).cost;
                    pauvre=false;
                    displayText=false;
                    shopItems.clear();
                }
                else {
                    pauvre = true;
                }
            }

        }else {
            displayText = false;
            shopItems.clear();
            pauvre = false;
        }
    }

    public void draw(SpriteBatch batch, BitmapFont font ) {
        if(this.displayText){
            if(job.equals("marchand")){
                String line0 = shopItems.size()>0? shopItems.get(0).name+", "+shopItems.get(0).cost+"golds\n" :"empty\n";
                String line1 = shopItems.size()>1? shopItems.get(1).name+", "+shopItems.get(1).cost+"golds\n" :"empty\n";
                String line2 = shopItems.size()>2? shopItems.get(2).name+", "+shopItems.get(2).cost+"golds\n" :"empty\n";


                font.draw(batch,"Hello my friend, these are the items available for purchase : \n" + line0  +line1 + line2,x * 32 - 100, 800 - y*32 + 120);
                    if(pauvre){
                        font.draw(batch,"Go get some golds first",x * 32, 800 - y*32 + 55);
                    }

            }else {
                font.draw(batch,"Hello, please go defeat all the monsters in the north",x * 32, 800 - y*32 + 64);
            }
        }
        super.draw(batch);
    }



}
