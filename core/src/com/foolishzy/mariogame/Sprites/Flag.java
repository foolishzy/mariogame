package com.foolishzy.mariogame.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.foolishzy.mariogame.MyMarioGame;
import com.foolishzy.mariogame.Screen.playScreen;

/**
 * Created by foolishzy on 2016/1/27.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class Flag extends Sprite {
    private static Vector2 contactPoint;
    private static Rectangle poleShape;
    private boolean isbeingcath;
    private World world;
    private Body bodyFlagPole;
    private Body bodyFlag;
    private TiledMap map;
    private TextureRegion region;
    private boolean flagpoleSettoDestory;
    private boolean flagpoleexist;
    private playScreen screen;
    public Flag(playScreen screen){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.screen = screen;
        isbeingcath = false;
        flagpoleSettoDestory = false;
        flagpoleexist = true;
        region = new TextureRegion(new Texture("Goomba/NES - Super Mario Bros - Enemies.png"),
                0, 16, 16, 16);
        defineFlag();
    }

    public  void defineFlag(){
        //init box2d flag pole
        MapObject object = map.getLayers().get(8).getObjects().get("flag");
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        poleShape = rect;
        BodyDef bddef = new BodyDef();
        bddef.type = BodyDef.BodyType.StaticBody;
        bddef.position.set((rect.getX() + rect.getWidth() / 2) / MyMarioGame.PPM,
                (rect.getY() + rect.getHeight() / 2) / MyMarioGame.PPM);
        FixtureDef fixdef = new FixtureDef();
        fixdef.filter.categoryBits = MyMarioGame.FLAG_POLE_BIT;
        fixdef.filter.maskBits = MyMarioGame.MARIO_BIT;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() / 2 / MyMarioGame.PPM,
                rect.getHeight() / 2 / MyMarioGame.PPM);
        fixdef.shape = shape;
        bodyFlagPole = world.createBody(bddef);
        bodyFlagPole.createFixture(fixdef).setUserData(this);
        //init box2d flag
        bddef.position.set(bodyFlagPole.getPosition().x -
                rect.getWidth() / 2 / MyMarioGame.PPM - 16 / 2 /MyMarioGame.PPM,
                bodyFlagPole.getPosition().y + rect.getHeight() / 2 / MyMarioGame.PPM
                - 16 / 2 /MyMarioGame.PPM);
        bddef.type = BodyDef.BodyType.DynamicBody;
        bddef.active = false;
        shape.setAsBox(16 / 2 /MyMarioGame.PPM, 16 / 2 /MyMarioGame.PPM);
        fixdef.shape = shape;
        fixdef.filter.categoryBits = MyMarioGame.FLAG_BIT;
        fixdef.filter.maskBits = ~MyMarioGame.MARIO_BIT;
        bodyFlag = world.createBody(bddef);
        bodyFlag.createFixture(fixdef).setUserData(this);
        //init flag region
        setPosition(bodyFlag.getPosition().x - 16 / 2 / MyMarioGame.PPM, bodyFlag.getPosition().y);
        setBounds(getX(), getY(), 16 / MyMarioGame.PPM, 16 / MyMarioGame.PPM);
        setRegion(region);
    }
    public void update(float delt){
        if(isbeingcath && !bodyFlag.isActive()){
            bodyFlag.setActive(true);
        }else if (isbeingcath && bodyFlag.isActive()){
            setPosition(bodyFlag.getPosition().x - 16 / 2 / MyMarioGame.PPM ,
                    bodyFlag.getPosition().y - 16 / 2 / MyMarioGame.PPM);
        }
        if(flagpoleSettoDestory && flagpoleexist){
            flagpoleexist = false;
            world.destroyBody(bodyFlagPole);
        }
    }
    public void poleonHit(Mario mario){
        isbeingcath = true;
        contactPoint = mario.b2dbody.getPosition();
        float length = poleShape.getHeight() / MyMarioGame.PPM;
        float score = 1000*(contactPoint.y - bodyFlagPole.getPosition().y
                + length / 2 ) / length;
        screen.hud.addDragFlagscore(((int) score));
    }
    public void destoryPole(){
        flagpoleSettoDestory = true;
    }

}
