package com.foolishzy.mariogame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.foolishzy.mariogame.AnimationCreate.allAnimation;
import com.foolishzy.mariogame.MyMarioGame;
import com.foolishzy.mariogame.Screen.playScreen;

import org.omg.PortableInterceptor.InvalidSlot;

/**
 * Created by foolishzy on 2016/1/11.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class Mario extends Sprite {

    public World world;
    public Body b2dbody;
    private playScreen screen;
    private Animation little_mario_run;
    private Animation little_mario_jump;
    private TextureRegion little_mario_stand;
    private TextureRegion little_mario_fall;
    private Animation big_mario_run;
    private Animation big_mario_jump;
    private TextureRegion big_mario_stand;
    private TextureRegion big_mario_fall;

    private float stateTimer;

    private enum state {FALLING, JUMPING, STANDING, RUNNING }
    private state currentState;
    private state previousState;

    private boolean catchFlag;
    private boolean runningRight;
    private boolean isBig;
    private boolean setMarioTodie;
    public boolean isLived;
    public boolean getDestina;
    public Mario(playScreen screen){
        this.screen = screen;
        this.world = screen.getWorld();
        setMarioTodie = false;
        isBig = false;
        isLived = true;
        catchFlag = false;
        getDestina = false;
        defineMario();

        currentState = state.STANDING;
        previousState = state.STANDING;
        stateTimer = 0;
        runningRight = true;

//      初始化animation
        initAnimation();
        setBounds(getX(), getY(), 16 / MyMarioGame.PPM, 16 / MyMarioGame.PPM);

    }

    public void defineMario(){
        BodyDef bdf = new BodyDef();

        bdf.position.set(32 / MyMarioGame.PPM, 32 / MyMarioGame.PPM);
        bdf.type = BodyDef.BodyType.DynamicBody;

        b2dbody = world.createBody(bdf);

        FixtureDef fixdf = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyMarioGame.PPM);
//      category
//      maskbit allowed to colide with
        fixdf.filter.categoryBits = MyMarioGame.MARIO_BIT;
        fixdf.filter.maskBits =
                MyMarioGame.ENEMY_BIT|
                MyMarioGame.COIN_BIT  |
                MyMarioGame.BRICK_BIT |
                MyMarioGame.DEFAULT_BIT |
                MyMarioGame.PIPE_BIT |
                MyMarioGame.ITEM_BIT |
                MyMarioGame.ENEMY_HEAD_BIT |
                MyMarioGame.FLAG_POLE_BIT |
                MyMarioGame.DESTINATION_BIT;

        fixdf.shape = shape;

        b2dbody.createFixture(fixdf).setUserData(this);

        //mario 头部上沿
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / MyMarioGame.PPM, 7 / MyMarioGame.PPM), new Vector2(2 / MyMarioGame.PPM, 7 / MyMarioGame.PPM));
        fixdf.shape = head;
        fixdf.isSensor = true;
        b2dbody.createFixture(fixdf).setUserData("head");
    }
    public void update(float dt){
        if(isLived && !setMarioTodie){
            if(isBig){
                setBounds(getX(), getY(), 16 / MyMarioGame.PPM, 22 / MyMarioGame.PPM);
                setPosition(b2dbody.getPosition().x - getWidth() / 2,
                        b2dbody.getPosition().y - getHeight() / 2 + 4 / MyMarioGame.PPM);
            }else{
                setBounds(getX(), getY(), 16 / MyMarioGame.PPM, 16 / MyMarioGame.PPM);
                setPosition(b2dbody.getPosition().x - getWidth() / 2,
                        b2dbody.getPosition().y -  getHeight() /2);
            }
            setRegion(getKeyFrame(dt));
        }else if (isLived && setMarioTodie){
            isLived = false;
            Gdx.app.log("mario dieing", "play mario died sound");
//            MyMarioGame.ASSET_MANAGER.get("audio/****.ogg", Sound.class).play();
            b2dbody.applyLinearImpulse(new Vector2(0f, 2.5f), b2dbody.getWorldCenter(), true);
        }else if(!isLived && setMarioTodie){
            setRegion(new TextureRegion(new Texture("Mario/littlemario.png"), 96, 0, 16, 16));
            setPosition(b2dbody.getPosition().x - getWidth() / 2,
                    b2dbody.getPosition().y - getHeight() / 2);
            Filter filter = new Filter();
            filter.categoryBits = MyMarioGame.MARIO_DIED_BIT;
            filter.maskBits = MyMarioGame.MARIO_BIT;
            b2dbody.getFixtureList().first().setFilterData(filter);
        }
//      跳红旗落地之后
            if(isLived && catchFlag && b2dbody.getPosition().y < 16 * 3.5 /MyMarioGame.PPM ){
            b2dbody.setLinearVelocity(new Vector2(0.3f, 0f));
        }
        //arrive destination
        if(getDestina && isLived && catchFlag){
            setRegion(new TextureRegion(new Texture("Goomba/NES - Super Mario Bros - Enemies.png"),
                    0, 0, 16, 16));
        }
    }

    public TextureRegion getKeyFrame(float dt){
        currentState = getState();
        TextureRegion region = new TextureRegion();
        switch (currentState){
            case JUMPING:
                region = isBig == true ? big_mario_jump.getKeyFrame(stateTimer)
                        : little_mario_jump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = isBig == true ? big_mario_run.getKeyFrame(stateTimer, true)
                        :little_mario_run.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
                region = isBig == true ? big_mario_fall : little_mario_fall;
                break;
            default:
                region = isBig == true ? big_mario_stand : little_mario_stand;
                break;
        }
        if ((b2dbody.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX() ){
            region.flip(true, false);
            runningRight = false;
        }
        if((b2dbody.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer += dt : 0;
        previousState = currentState;
        return region;
    }

    public state getState(){
        //(player.b2dbody.getLinearVelocity().y < 0 && previousState == state.JUMPING)
        //减速运动？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
        if(b2dbody.getLinearVelocity().y > 0 || (b2dbody.getLinearVelocity().y < 0 && previousState == state.JUMPING)){
            return state.JUMPING;
        }else if (b2dbody.getLinearVelocity().y < 0){
            return state.FALLING;
        }else if(b2dbody.getLinearVelocity().x != 0){
            return state.RUNNING;
        }else
            return state.STANDING;
    }

    private void initAnimation(){
        com.foolishzy.mariogame.AnimationCreate.allAnimation all = new allAnimation();

        little_mario_jump = all.getLittleMariojump();
        little_mario_run = all.getLittleMarioRun();
        little_mario_stand = all.getLittleMarioStand();
        little_mario_fall = all.getLittleMarioFall();

        big_mario_jump = all.getBigMarioJump();
        big_mario_run = all.getBigMarioRun();
        big_mario_stand = all.getBigMarioStand();
        big_mario_fall = all.getBigMarioFall();


    }

    public void hurtMario(Fixture enemy){

        if(isBig){
            ((Enemy) enemy.getUserData()).onHit();
            isBig = false;
        }else {
            setMarioTodie = true;
        }
    }

    public void growUp(){
//        MyMarioGame.ASSET_MANAGER.get("audio/getmushroom.ogg", Sound.class).play();
        Gdx.app.log("get mushroom", "play get mushroom sound");
        if(isBig){
            screen.hud.addscore(playScreen.GET_MUSHROOM);
        }else {
            isBig = true;
        }
    }

    public void dispose(){

    }

    public void CatchFlag(){
        catchFlag = true;
    }

    public boolean getCatchflag(){
        return catchFlag;
    }

    public void getDestination(){
        getDestina = true;
    }
    public boolean getIsBig(){
        return isBig;
    }
}



