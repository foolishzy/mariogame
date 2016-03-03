package com.foolishzy.mariogame.Item;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.foolishzy.mariogame.MyMarioGame;
import com.foolishzy.mariogame.Screen.playScreen;

import javafx.scene.shape.Circle;

/**
 * Created by foolishzy on 2016/1/23.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */

public class Mushroom extends Item {
    public Mushroom(playScreen screen, float x, float y) {
        //x, y 都还没有和ppm 运算过
        super(screen, x, y);
        setRegion(new TextureRegion(new Texture("Goomba/NES - Super Mario Bros - Enemies.png"), 36 * 16, 5 * 16, 16, 16));
        velocity = new Vector2(0.8f, -1f);
    }

    @Override
    public void defineItem(float x, float y) {
        BodyDef bdf = new BodyDef();
        bdf.position.set(x / MyMarioGame.PPM, y / MyMarioGame.PPM);
        bdf.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdf);

        FixtureDef fixdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6f / MyMarioGame.PPM);
        fixdef.shape = shape;
        fixdef.filter.categoryBits = MyMarioGame.ITEM_BIT;
        fixdef.filter.maskBits = MyMarioGame.MARIO_BIT |
                MyMarioGame.PIPE_BIT |
                MyMarioGame.BRICK_BIT |
                MyMarioGame.COIN_BIT |
                MyMarioGame.DEFAULT_BIT ;
        b2body.createFixture(fixdef).setUserData(this);
    }
    @Override
    public void update(float dt) {
        if(isLive && !setTodestory){
            setPosition(b2body.getPosition().x - getWidth() / 2,
                b2body.getPosition().y - getWidth() / 2);
            b2body.setLinearVelocity(velocity);
        }else if(setTodestory && isLive){
            isLive = false;
            world.destroyBody(b2body);
        }
    }
    @Override
    public void use() {
    }
    public void resetVelocity(boolean x, boolean y){
        if(x) velocity.x = -velocity.x;
        if(y) velocity.y = -velocity.y;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    @Override
    public void destory() {
        MyMarioGame.ASSET_MANAGER.get("audio/coin.ogg", Sound.class).play();
        screen.hud.addscore(playScreen.GET_MUSHROOM);
        setTodestory = true;
    }
}
