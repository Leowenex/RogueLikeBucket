package com.mygdx.game;

public class Weapon extends Item{
    int damage;


    public Weapon(String name, int cost, int x, int y, int damage) {
        super(name, cost, x, y);
        this.damage = damage;
    }
}
