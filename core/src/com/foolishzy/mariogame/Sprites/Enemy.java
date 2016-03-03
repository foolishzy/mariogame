package com.foolishzy.mariogame.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.foolishzy.mariogame.Screen.playScreen;

/**
 * Created by foolishzy on 2016/1/22.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public abstract  class Enemy extends Sprite {
    protected World world;
    protected TiledMap map;
    public Body b2body;
    public Enemy(playScreen screen, float x, float y){
        this.map = screen.getMap();
        this.world = screen.getWorld();
        setPosition(x, y);
        defineEnemy(x, y);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy(float x, float y);
    public abstract void onHit();
    public abstract void  flipVelosity(boolean x, boolean y);
}
