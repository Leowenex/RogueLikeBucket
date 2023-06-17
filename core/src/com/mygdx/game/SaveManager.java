package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SaveManager {
    public static void saveCharacter(Player player){
        Preferences prefs = Gdx.app.getPreferences("Character");
        prefs.putInteger("health", player.getHealth());
        prefs.putInteger("gold", player.gold);

        StringBuilder inventory = new StringBuilder();
        for(Item item : player.inventory){
            inventory.append(item.name).append(",");
        }
        prefs.putString("inventory", inventory.toString());
        prefs.flush();
    }

    public static Player loadCharacter(){
        Preferences prefs = Gdx.app.getPreferences("Character");
        Player player = new Player(0,0);
        player.health = prefs.getInteger("health");
        if(player.health <= 0)
            return new Player(2,2);
        player.gold = prefs.getInteger("gold");
        String[] inventory = prefs.getString("inventory").split(",");
        for(String itemName : inventory){
            insertItemName(player, itemName);
        }

        return player;
    }

    public static void insertItemName(Player player, String itemName){

        Item item = null;

        switch(itemName){
            case "sword":
                item = new Weapon("sword", 5, 0, 0,1);
                break;
            case "axe":
                item = new Weapon("axe", 10, 0, 0,2);
                break;
            case "chestplate":
                item = new Item("chestplate", 5, 0, 0);
                break;
            case "glove":
                item = new Item("glove", 5, 0, 0);
                break;
            case "boots":
                item = new Item("boots", 5, 0, 0);
                break;
            case "health_potion":
                item = new Item("health_potion", 5, 0, 0);
                break;
            case "mana_potion":
                item = new Item("mana_potion", 5, 0, 0);
                break;
            case "fire_spell":
                item = new Item("fire_spell", 5, 0, 0);
                break;
            case "ice_spell":
                item = new Item("ice_spell", 5, 0, 0);
                break;
            case "nature_spell":
                item = new Item("nature_spell", 5, 0, 0);
                break;
        }
        if(item == null)
            return;

        item.pickable = false;
        item.dynamic_light = false;
        player.inventory.add(item);
    }
}
