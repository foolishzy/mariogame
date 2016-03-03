package test;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.foolishzy.mariogame.AnimationCreate.allAnimation;
import com.foolishzy.mariogame.MyMarioGame;


/**
 * Created by foolishzy on 2016/1/23.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class testSprite extends Sprite {
    TiledMap map;
    World world;
    Animation ani;
    float statetime = 0;
    public testSprite(testScreen screen, float x, float y) {
    this.world = screen.getWorld();
        this.map = screen.getMap();
        initanimation();
        setPosition(x / MyMarioGame.PPM, y / MyMarioGame.PPM);
    }
    public void initanimation(){
        com.foolishzy.mariogame.AnimationCreate.allAnimation all = new allAnimation();
        ani = all.getLittleMariojump();
    }
    public void update(float dt){
        statetime += dt;
        setRegion(ani.getKeyFrame(statetime));
    }

}
