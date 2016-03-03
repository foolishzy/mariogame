package com.foolishzy.mariogame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.foolishzy.mariogame.MyMarioGame;
import com.foolishzy.mariogame.Screen.playScreen;
import com.sun.media.jfxmedia.events.PlayerStateEvent;
import com.sun.scenario.effect.impl.prism.ps.PPSBlend_ADDPeer;

import org.omg.CORBA.INTERNAL;

import java.io.BufferedReader;
import java.io.Reader;

import javafx.stage.Screen;
import test.testScreen;

/**
 * Created by foolishzy on 2016/1/25.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class Coin {
    private World world;
    public Body body;
    private TiledMap map;
    private playScreen screen;
    public MapObject object;
    public Coin(playScreen screen, MapObject object){
        this.object = object;
         this.screen = screen;
        this.map = screen.getMap();
        this.world = screen.getWorld();
        BodyDef bdf = new BodyDef();
        FixtureDef fixdef = new FixtureDef();

        Rectangle rect  = ((RectangleMapObject) object).getRectangle();
        bdf.position.set((rect.getX() + rect.getWidth() / 2) / MyMarioGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyMarioGame.PPM);
        bdf.type = BodyDef.BodyType.StaticBody;

        fixdef.filter.categoryBits = MyMarioGame.COIN_BIT;
        fixdef.filter.maskBits = MyMarioGame.MARIO_BIT | MyMarioGame.ITEM_BIT;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() /2 / MyMarioGame.PPM, rect.getHeight()/ 2 /MyMarioGame.PPM);

        fixdef.shape = shape;
        body = world.createBody(bdf);
        body.createFixture(fixdef).setUserData(this);
    }


    public void onHit(){
        TiledMapTileLayer layer = ((TiledMapTileLayer) map.getLayers().get(1));
        TiledMapTileLayer.Cell cell = layer.getCell(((int) (body.getPosition().x * MyMarioGame.PPM / 15)),
                ((int) (body.getPosition().y * MyMarioGame.PPM / 15)));
        //是否被撞过
        if(cell.getTile().getId() == MyMarioGame.COINED_ID){
            MyMarioGame.ASSET_MANAGER.get("audio/brick&coined.ogg", Sound.class).play();
        }
        //判断为普通的coin
        else {
                MyMarioGame.ASSET_MANAGER.get("audio/coin.ogg", Sound.class).play();
                TiledMapTile coined = map.getTileSets().getTileSet("NES - Super Mario Bros - Tileset").getTile(MyMarioGame.COINED_ID);
                cell.setTile(coined);
                screen.hud.addscore(playScreen.COIN_SCORE);

            //是否为mushroom
             if (object.getProperties().containsKey(MyMarioGame.mushroom)) {
                screen.hud.addscore(playScreen.GET_MUSHROOM);
                Gdx.app.log("find mushroom", "play find mushroom sound");
//            MyMarioGame.ASSET_MANAGER.get("",Sound.class).play();
                screen.createMushroom(body.getPosition().x *MyMarioGame.PPM,
                        body.getPosition().y*MyMarioGame.PPM + 15 );
            }
            }
    }

}


