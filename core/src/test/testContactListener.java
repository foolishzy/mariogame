package test;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.foolishzy.mariogame.MyMarioGame;

/**
 * Created by foolishzy on 2016/1/14.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class testContactListener implements ContactListener {
    private static Fixture fixtureobject;
    private  static Fixture fixtureBox;
    @Override
    public void beginContact(Contact contact) {



//        if (fixtureobject.getUserData() == "plantform") {
//            Filter filter = new Filter();
//            filter.categoryBits = mytestScreen.PLAYER_BIT;
//            filter.maskBits = mytestScreen.PLAYER_BIT;
//            fixtureobject.setFilterData(filter);
//
//        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        fixtureBox = fixtureA.getUserData() == "box" ? fixtureA : fixtureB;
        fixtureobject = fixtureBox.getUserData() == fixtureA.getUserData() ? fixtureB : fixtureA;
        if(fixtureobject.getUserData() == "brick"){
            Filter filter = new Filter();
            filter.categoryBits = testScreen.DESTORY_BIT;
            fixtureobject.setFilterData(filter);

            TmxMapLoader loader = new TmxMapLoader();
            TiledMap map = loader.load("mariomap1-1.tmx");
            TiledMapTileLayer layer = ((TiledMapTileLayer) map.getLayers().get(1));

//            layer.getCell((int)((fixtureobject.getBody().getPosition().x )* MyMarioGame.PPM / 16),
//                    (int)((fixtureobject.getBody().getPosition().y )*MyMarioGame.PPM /16)).setTile(null);

        System.out.println(layer.getCell((int) (((fixtureobject.getBody().getPosition().x) * MyMarioGame.PPM + 8) / 16),
                (int) (((fixtureobject.getBody().getPosition().y) * MyMarioGame.PPM +8)/ 16)) == null);
        if(!(layer.getCell((int) (((fixtureobject.getBody().getPosition().x) * MyMarioGame.PPM + 8) / 16),
                (int) (((fixtureobject.getBody().getPosition().y) * MyMarioGame.PPM +8)/ 16)) == null)){
            layer.getCell((int) (((fixtureobject.getBody().getPosition().x) * MyMarioGame.PPM + 8) / 16),
                    (int) (((fixtureobject.getBody().getPosition().y) * MyMarioGame.PPM +8)/ 16)) .setTile(null);
        }
            layer.getCell(1, 1).setTile(null);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
