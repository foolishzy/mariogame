package com.foolishzy.mariogame.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.foolishzy.mariogame.MyMarioGame;

/**
 * Created by foolishzy on 2016/1/11.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class Grounds extends InterActiveBox2dSprites {
    public Grounds(World world, TiledMap map, int i) {
        super(world, map, i, "ground",MyMarioGame.DEFAULT_BIT);
    }


}

