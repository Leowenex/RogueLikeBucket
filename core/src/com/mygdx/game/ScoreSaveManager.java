package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreSaveManager {

    public static void saveScore(int score){
        Preferences prefs = Gdx.app.getPreferences("Score");

        prefs.getInteger("highScore");

        if(score > prefs.getInteger("highScore")){
            prefs.putInteger("highScore", score);
        }
        prefs.putInteger("lastScore", score);
        prefs.flush();
    }

    public static int[] loadScore(){
        Preferences prefs = Gdx.app.getPreferences("Score");
        int[] scores = new int[2];
        scores[0] = prefs.getInteger("highScore");
        scores[1] = prefs.getInteger("lastScore");
        return scores;
    }
}
