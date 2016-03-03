package com.foolishzy.mariogame.AnimationCreate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by foolishzy on 2016/1/13.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class allAnimation {
    private Animation littleMarioRun;
    private Animation littleMariojump;
    private TextureRegion littleMarioStand;
    private TextureRegion littleMarioFall;

    private Animation bigMarioRun;
    private Animation bigMarioJump;
    private TextureRegion bigMarioStand;
    private TextureRegion bigMarioFall;

    public Vector2 position;

    public allAnimation(){
        Texture littleMario=new Texture(Gdx.files.internal("Mario/littlemario.png"));
        littleMarioStand =new TextureRegion(littleMario, 16, 0, 16, 16);
        littleMarioRun = new animationCreator("Mario/littlemario.png", 1, 14, 1, 1, 2, 3).getAnimation() ;
        littleMariojump = new  animationCreator("Mario/littlemario.png", 1, 14, 1, 1, 4, 5).getAnimation();
        littleMarioFall = new TextureRegion(littleMario, 7*16, 0, 16, 16);

        Texture bigMario = new Texture(Gdx.files.internal("Mario/bigmario.png"));
        bigMarioStand = new TextureRegion(bigMario, 16, 0, 16, 32 );
        bigMarioRun = new animationCreator("Mario/bigmario.png",1, 21, 1, 1, 2, 3).getAnimation();
        bigMarioJump = new animationCreator("Mario/bigmario.png", 1, 21, 1, 1, 4, 5).getAnimation();
        bigMarioFall = new TextureRegion( bigMario, 7*16, 0, 16, 32);
    }

//  getters


    public Animation getLittleMarioRun() {
        return littleMarioRun;
    }

    public Animation getLittleMariojump() {
        return littleMariojump;
    }

    public TextureRegion getLittleMarioStand() {
        return littleMarioStand;
    }

    public Animation getBigMarioRun() {
        return bigMarioRun;
    }

    public Animation getBigMarioJump() {
        return bigMarioJump;
    }

    public TextureRegion getBigMarioStand() {
        return bigMarioStand;
    }

    public TextureRegion getLittleMarioFall() {
        return littleMarioFall;
    }

    public TextureRegion getBigMarioFall() {
        return bigMarioFall;
    }
}
