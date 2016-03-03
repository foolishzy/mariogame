package com.foolishzy.mariogame.Sprites;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.foolishzy.mariogame.MyMarioGame;



/**
 * Created by foolishzy on 2016/1/11.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public abstract class InterActiveBox2dSprites {
    protected TiledMap map;
    protected World world;

    public InterActiveBox2dSprites(World world, TiledMap map, int i, Object userdate, short mask) {
        PolygonShape shape = new PolygonShape();
        BodyDef bodyDef = new BodyDef();
        Body body;
        FixtureDef fixtureDef = new FixtureDef();
        Fixture fixture;
        this.world = world;
        this.map = map;

        for (MapObject object : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / MyMarioGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyMarioGame.PPM);
            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth() / 2 / MyMarioGame.PPM, rect.getHeight() / 2 / MyMarioGame.PPM);
            fixtureDef.filter.categoryBits = mask;
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef).setUserData(userdate);
        }
    }
}
























//        BodyDef bodyDef = new BodyDef();
//        FixtureDef fixtureDef= new FixtureDef();
//        PolygonShape shape = new PolygonShape();
//        Body body;
////        create ground/body/fixture
////      get（2）取得第三层，getobjects，取得对象，getbytype（rectangmapobject），按照类型去取rectangle
//        for (MapObject mapObject: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
//            com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
//
//            bodyDef.type = BodyDef.BodyType.StaticBody;
//            bodyDef.position.set((rect.getX() + rect.getWidth()/2) / MyMarioGame.PPM,(rect.getY()+rect.getHeight()/2) / MyMarioGame.PPM);
//
//            body = world.createBody(bodyDef);
//            shape.setAsBox(rect.getWidth() / 2 / MyMarioGame.PPM, rect.getHeight() / 2 / MyMarioGame.PPM);
//            fixtureDef.shape = shape;
//            body.createFixture(fixtureDef);
//        }
////        bricks
//        for (MapObject mapObject: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
//            com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
//
//            bodyDef.type = BodyDef.BodyType.StaticBody;
//            bodyDef.position.set((rect.getX() + rect.getWidth()/2) / MyMarioGame.PPM,(rect.getY()+rect.getHeight()/2) / MyMarioGame.PPM);
//
//            body = world.createBody(bodyDef);
//            shape.setAsBox(rect.getWidth() / 2 / MyMarioGame.PPM, rect.getHeight() / 2 / MyMarioGame.PPM);
//            fixtureDef.shape = shape;
//            body.createFixture(fixtureDef);
//        }
//
////        coins
//        for (MapObject mapObject: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
//            com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
//
//            bodyDef.type = BodyDef.BodyType.StaticBody;
//            bodyDef.position.set((rect.getX() + rect.getWidth()/2) / MyMarioGame.PPM,(rect.getY()+rect.getHeight()/2) / MyMarioGame.PPM);
//
//            body = world.createBody(bodyDef);
//            shape.setAsBox(rect.getWidth() / 2 / MyMarioGame.PPM, rect.getHeight() / 2 / MyMarioGame.PPM);
//            fixtureDef.shape = shape;
//            body.createFixture(fixtureDef);
//        }
////        pipe
//        for (MapObject mapObject: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
//            com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
//
//            bodyDef.type = BodyDef.BodyType.StaticBody;
//            bodyDef.position.set((rect.getX() + rect.getWidth()/2) / MyMarioGame.PPM,(rect.getY()+rect.getHeight()/2) / MyMarioGame.PPM);
//
//            body = world.createBody(bodyDef);
//            shape.setAsBox(rect.getWidth() / 2 / MyMarioGame.PPM, rect.getHeight() / 2 / MyMarioGame.PPM);
//            fixtureDef.shape = shape;
//            body.createFixture(fixtureDef);
//        }
