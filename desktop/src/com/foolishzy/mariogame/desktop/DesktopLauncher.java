package com.foolishzy.mariogame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.foolishzy.mariogame.MyMarioGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=MyMarioGame.V_WIDTH*2;
		config.height=MyMarioGame.V_HEIGTH*2;
		config.title="SuperMarioBros";
		new LwjglApplication(new MyMarioGame(), config);
	}
}
