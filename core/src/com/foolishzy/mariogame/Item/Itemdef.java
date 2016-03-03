package com.foolishzy.mariogame.Item;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by foolishzy on 2016/1/23.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class Itemdef {
    public  Vector2 position;
    public  Class<?> type;
    public Itemdef(Vector2 position, Class<?> type){
        this.type = type;
        this.position = position;
    }
}
