package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Roguelike Bucket");
		config.setWindowedMode(1600, 900);
		config.useVsync(true);
		config.setForegroundFPS(60);
		config.setResizable(false);
		config.setWindowSizeLimits(1600, 900, 1600, 900);
		config.setWindowIcon("textures/monster.png");
		new Lwjgl3Application(new GameLauncher(), config);
	}
}
