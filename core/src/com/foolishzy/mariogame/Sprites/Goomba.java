package com.foolishzy.mariogame.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.foolishzy.mariogame.MyMarioGame;
import com.foolishzy.mariogame.Screen.playScreen;

/**
 * Created by foolishzy on 2016/1/22.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class Goomba extends Enemy {
    private static Animation walkAnimation;
    private static Animation dieAnimation;
    private float statetime = 0;

    private boolean setToDestory;
    private boolean isLive;
    private Vector2 velosity;
     private playScreen screen;
    public Goomba(playScreen screen, float x, float y) {
        super(screen, x, y);
        this.screen = screen;
        initAnimation();
        setBounds(getX(), getY(), 16 / MyMarioGame.PPM, 16 / MyMarioGame.PPM);

        velosity = new Vector2(0.5f, 0f);
        isLive = true;
        setToDestory = false;
    }
     public void update(float dt){

         if(isLive && !setToDestory){
             statetime += dt;
             b2body.setLinearVelocity(velosity);
             setPosition(b2body.getPosition().x - getWidth() / 2,
                     b2body.getPosition().y - getHeight() / 2);
             setRegion(walkAnimation.getKeyFrame(statetime, true));
         }else if(isLive && setToDestory){
             statetime = 0;
             isLive = false;
             world.destroyBody(b2body);
             }else if(!isLive && setToDestory){
             statetime += dt;
             setRegion(dieAnimation.getKeyFrame(statetime, false));
//             setRegion(new TextureRegion(new Texture("Goomba/NES - Super Mario Bros - Enemies.png"), 32, 16, 16, 16));

         }

         }

    @Override
    protected void defineEnemy(float x, float y) {
        BodyDef bdf = new BodyDef();

        bdf.position.set(x, y);
        bdf.type = BodyDef.BodyType.DynamicBody;
        this.b2body = world.createBody(bdf);

        FixtureDef fixdf = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyMarioGame.PPM);
        fixdf.filter.categoryBits = MyMarioGame.ENEMY_BIT;
        fixdf.filter.maskBits =
                MyMarioGame.COIN_BIT  |
                MyMarioGame.BRICK_BIT |
                MyMarioGame.DEFAULT_BIT |
                MyMarioGame.PIPE_BIT|
                MyMarioGame.MARIO_BIT;
        fixdf.shape = shape;
        this.b2body.createFixture(fixdf).setUserData(this);

//      create the head of goomba
        PolygonShape headShape = new PolygonShape();
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-4.5f, 9).scl(1 / MyMarioGame.PPM, 1 / MyMarioGame.PPM);
        vertices[1] = new Vector2(4.5f, 9).scl(1 / MyMarioGame.PPM, 1 / MyMarioGame.PPM);
        vertices[2] = new Vector2(1, 5).scl(1 / MyMarioGame.PPM, 1 / MyMarioGame.PPM);
        vertices[3] = new Vector2(-1, 5).scl(1 / MyMarioGame.PPM, 1 / MyMarioGame.PPM);

        headShape.set(vertices);
        fixdf.shape = headShape;
        fixdf.filter.categoryBits = MyMarioGame.ENEMY_HEAD_BIT;
        fixdf.filter.maskBits = MyMarioGame.MARIO_BIT;
        fixdf.restitution = 0.6f;
        this.b2body.createFixture(fixdf).setUserData(this);

    }

    @Override
    public void onHit() {
        screen.hud.addscore(playScreen.KILL_GOOMBA);
        setToDestory = true;
        MyMarioGame.ASSET_MANAGER.get("audio/coin.ogg", Sound.class).play();
    }

    private void initAnimation(){
        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        keyFrames.add(new TextureRegion(new Texture("Goomba/NES - Super Mario Bros - Enemies.png"), 0, 16, 16, 16));
        keyFrames.add(new TextureRegion(new Texture("Goomba/NES - Super Mario Bros - Enemies.png"), 16, 16, 16, 16));
        walkAnimation = new Animation(1/3f,keyFrames, Animation.PlayMode.LOOP);

        Array<TextureRegion> keyFrames1 = new Array<TextureRegion>();
        keyFrames1.add(new TextureRegion(new Texture("Goomba/NES - Super Mario Bros - Enemies.png"), 32, 16, 16, 16));
        keyFrames1.add(new TextureRegion(new Texture("Goomba/NES - Super Mario Bros - Enemies.png"), 32, 16, 16, 16));
        keyFrames1.add(new TextureRegion(new Texture("Goomba/NES - Super Mario Bros - Enemies.png"), 0, 0, 16, 16));

        dieAnimation = new Animation(1/2f,keyFrames1, Animation.PlayMode.NORMAL);

    }
    @Override
     public void flipVelosity(boolean x, boolean y){
        if(x)   velosity.x = -velosity.x;
        if(y)   velosity.y = -velosity.y;
    }
}
