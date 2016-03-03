package com.foolishzy.mariogame.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.foolishzy.mariogame.MyMarioGame;

import java.nio.channels.Pipe;

/**
 * Created by foolishzy on 2016/1/11.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class Pipes extends InterActiveBox2dSprites {
    public Pipes(World world, TiledMap map, int i) {
        super(world, map, i, "pipe", MyMarioGame.PIPE_BIT);

    }

}
