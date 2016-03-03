package com.foolishzy.mariogame;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.foolishzy.mariogame.Screen.MenuScreen;
import com.foolishzy.mariogame.Screen.playScreen;

public class MyMarioGame extends Game {

//	speed test
	public static Vector2 BIG_JUMP = new Vector2(0, 3.8f);
	public static Vector2 LITTLE_JUMP = new Vector2(0, 3.2f);

	public static final int V_WIDTH=400;
	public static final int V_HEIGTH=204;
	public static final float  PPM = 100;
	public SpriteBatch batch;

	public static final short DEFAULT_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short PIPE_BIT= 4;
	public static final short COIN_BIT = 16;
	public static final short BRICK_BIT = 8;
	public static final short DESTOYRED_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short MARIO_DIED_BIT = 256;
	public static final short ITEM_BIT = 512;
	public static final short FLAG_POLE_BIT = 1024;
	public static final short FLAG_BIT = 2048;
	public static final short DESTINATION_BIT = 4096;

	public static final int DISTANCE_MARIO_ENEMY =250;

	public static final int COINED_ID = 28;

	public static final String mushroom = "mushroom";
	public static  AssetManager ASSET_MANAGER ;
	@Override
	public void create () {
		ASSET_MANAGER=new AssetManager();
		ASSET_MANAGER.load("audio/background.mp3", Music.class);
		ASSET_MANAGER.load("audio/brick&coined.ogg", Sound.class);
		ASSET_MANAGER.load("audio/die.ogg", Sound.class);
		ASSET_MANAGER.load("audio/coin.ogg", Sound.class);
		ASSET_MANAGER.finishLoading();
		Music bgm = ASSET_MANAGER.get("audio/background.mp3", Music.class);
		bgm.setVolume(0.4f);
		bgm.setLooping(true);
		bgm.play();

		batch = new SpriteBatch();
		setScreen(new MenuScreen(this));
	}


	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		ASSET_MANAGER.dispose();
		batch.dispose();
	}
}
