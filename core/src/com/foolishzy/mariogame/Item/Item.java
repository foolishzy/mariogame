package com.foolishzy.mariogame.Item;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.foolishzy.mariogame.MyMarioGame;
import com.foolishzy.mariogame.Screen.playScreen;

/**
 * Created by foolishzy on 2016/1/23.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public abstract  class Item extends Sprite {
    protected Body b2body;
    protected World world;
    protected playScreen screen;
    protected boolean setTodestory;
    protected boolean isLive;
    protected Vector2 velocity;

    public Item (playScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x / MyMarioGame.PPM, y / MyMarioGame.PPM);
        setBounds(getX(), getY(), 15 /MyMarioGame.PPM ,15 / MyMarioGame.PPM);
        setTodestory = false;
        isLive = true;
        defineItem(x, y);
    }
    public void draw(Batch batch){
        if(isLive && !setTodestory){
            super.draw(batch);
        }
    }
    public abstract  void defineItem(float x, float y);
    public void update(float dt){
        if(isLive && setTodestory){
            isLive = false;
            world.destroyBody(b2body);
        }
    }
    public abstract void use();
    public void destory(){

    }
}
