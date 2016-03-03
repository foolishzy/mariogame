package com.foolishzy.mariogame.others;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.foolishzy.mariogame.Item.Mushroom;
import com.foolishzy.mariogame.MyMarioGame;
import com.foolishzy.mariogame.Screen.playScreen;
import com.foolishzy.mariogame.Sprites.Coin;
import com.foolishzy.mariogame.Sprites.Enemy;
import com.foolishzy.mariogame.Sprites.Flag;
import com.foolishzy.mariogame.Sprites.Goomba;
import com.foolishzy.mariogame.Sprites.Mario;

/**
 * Created by foolishzy on 2016/1/13.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class WorldContactListener implements ContactListener {
    playScreen screen;
    public WorldContactListener(playScreen screen){
        this.screen = screen;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

         int A_B = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (A_B){
            //enemy 和 mario 相撞 kill mario
            case MyMarioGame.ENEMY_BIT | MyMarioGame.MARIO_BIT :
                Fixture mario = fixA.getFilterData().categoryBits == MyMarioGame.MARIO_BIT ? fixA : fixB;
                Fixture enemy = fixA == mario ? fixB : fixA;
                mario.getFilterData().categoryBits = MyMarioGame.MARIO_DIED_BIT;
                //maskBits 设置为自身，不能再和任何fixture相碰
                mario.getFilterData().maskBits = MyMarioGame.MARIO_DIED_BIT;
                        ((Mario) mario.getUserData()).hurtMario(enemy);
                break;
            //mario 和 mushroom
            case MyMarioGame.ITEM_BIT | MyMarioGame.MARIO_BIT:
                 mario = fixA.getFilterData().categoryBits == MyMarioGame.MARIO_BIT ? fixA : fixB;
                 Fixture mushroom = mario == fixA ? fixB : fixA;
                ((Mario) mario.getUserData()).growUp();
                ((Mushroom) mushroom.getUserData()).destory();
                break;
            //mushroom pipe
            case MyMarioGame.ITEM_BIT | MyMarioGame.PIPE_BIT :
                mushroom = fixA.getFilterData().categoryBits == MyMarioGame.ITEM_BIT ? fixA :fixB;
                ((Mushroom) mushroom.getUserData()).resetVelocity(true, false);
                break;
            //mario flagpole
            case MyMarioGame.MARIO_BIT | MyMarioGame.FLAG_POLE_BIT:
                mario = fixA.getFilterData().categoryBits == MyMarioGame.MARIO_BIT ? fixA : fixB;
                Fixture flagpolefix = mario == fixA ? fixB : fixA;
                ((Mario) mario.getUserData()).CatchFlag();
                ((Flag) flagpolefix.getUserData()).poleonHit(((Mario) mario.getUserData()));
                break;
            //flag ground
            case MyMarioGame.FLAG_BIT | MyMarioGame.DEFAULT_BIT :
                Fixture flagfix = fixA.getFilterData().categoryBits == MyMarioGame.FLAG_BIT ? fixA : fixB;
                ((Flag) flagfix.getUserData()).destoryPole();
                break;
            //mario destination
            case MyMarioGame.MARIO_BIT | MyMarioGame.DESTINATION_BIT :
                mario = fixA.getFilterData().categoryBits == MyMarioGame.MARIO_BIT ? fixA : fixB;
                catchDestination(screen);
        }
//      goombahead 和 mario相撞
        if( ((fixA.getFilterData().categoryBits == MyMarioGame.MARIO_BIT) &&
                (fixB.getFilterData().categoryBits == MyMarioGame.ENEMY_HEAD_BIT))
            ||    (fixA.getFilterData().categoryBits == MyMarioGame.ENEMY_HEAD_BIT &&
                fixB.getFilterData().categoryBits == MyMarioGame.MARIO_BIT)){
            Fixture mario = fixA.getFilterData().categoryBits == MyMarioGame.MARIO_BIT ? fixA : fixB;
            Fixture goombahead = mario == fixA ? fixB : fixA;
            ((Goomba)goombahead.getUserData()).onHit();
        }
//      enemy 和 pipe 相撞
        if(fixA.getUserData() == "pipe" || fixB.getUserData() == "pipe"){
            Fixture pipe = fixA.getUserData() == "pipe" ? fixA : fixB;
            Fixture object = pipe == fixA ? fixB : fixA;
            //需要判断 object 是否为 enemy
            if(object.getFilterData().categoryBits == MyMarioGame.ENEMY_BIT){
                ((Enemy)object.getUserData()).flipVelosity(true, false);
            }
        }

        if (fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;
//          mario head 和 brick 相撞
            if(object.getUserData() == "brick"){
                //更改categorbits 和 maskbit
                //将该cell置为null
                breakBrick(object);
            }
//           mario 和 coin 相撞
            if(object.getFilterData().categoryBits == MyMarioGame.COIN_BIT){
                ((Coin) object.getUserData()).onHit();
            }
//            isAssignableFrom() 判断前者是后者的父类 则返回值为true 否则为false， 参数为class不是对象，
//            isInstanceof() 同理，前者为父类后者为子类则返回值为true，否则为false，参数为对象不是class
//            if((object.getUserData() != null && InterActiveBox2dSprites.class.isAssignableFrom(object.getUserData().getClass()))){
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    private void breakBrick(Fixture object){
        //声音
        MyMarioGame.ASSET_MANAGER.get("audio/brick&coined.ogg", Sound.class).play(0.3f);
        //分数
        playScreen.hud.addscore(playScreen.BRICK_SCORE);
        //更改 categoryBits 和 maskBits 是马里奥不能再和该brick相撞
        Filter filter = new Filter();
        filter.categoryBits = MyMarioGame.DESTOYRED_BIT;
        filter.maskBits = MyMarioGame.DESTOYRED_BIT;
        object.setFilterData(filter);
        //将被击中的 birck cell 置为bull
        TiledMapTileLayer layer =(TiledMapTileLayer)(playScreen.map.getLayers().get(1));
        //需要利用player screen中的map，因为在进行绘制的时候是用的这个map，如果此处新建一个map，那么
        //在break bricks函数执行完之后就会销毁，而且player screen中的map并没有产生任何改变，所以在绘制的时候，
        // 就看不见有任何的变化
        layer.getCell((int)(object.getBody().getPosition().x *MyMarioGame.PPM / 15 ),
                (int)(object.getBody().getPosition().y*MyMarioGame.PPM / 15)).setTile(null);
    }
    private void catchDestination(playScreen screen){
        screen.getPlayer().getDestination();
    }
}
