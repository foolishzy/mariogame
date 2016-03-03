package com.foolishzy.mariogame.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.foolishzy.mariogame.MyMarioGame;

/**
 * Created by foolishzy on 2016/1/11.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class Bricks extends InterActiveBox2dSprites{
    public Bricks(World world, TiledMap map, int i) {
        super(world, map, i, "brick", MyMarioGame.BRICK_BIT);
    }
}

