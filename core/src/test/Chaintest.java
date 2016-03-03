package test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.foolishzy.mariogame.MyMarioGame;


/**
 * Created by foolishzy on 2016/1/27.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class Chaintest implements com.badlogic.gdx.Screen {
    private  short CHAIN_BIT = 1;
    private short XANIS_BIT =2;
    private MyMarioGame game;
    private FitViewport gameport;
    private OrthographicCamera gamecam;
    private World world;
    private Box2DDebugRenderer b2drender;

    public Chaintest(MyMarioGame game){
        this.game = game;
        gamecam = new OrthographicCamera(MyMarioGame.V_WIDTH / MyMarioGame.PPM * 30,
                MyMarioGame.V_HEIGTH / MyMarioGame.PPM * 30);
        gameport = new FitViewport(MyMarioGame.V_WIDTH / MyMarioGame.PPM*30,
                MyMarioGame.V_HEIGTH / MyMarioGame.PPM * 30, gamecam );
        initBox2d();
        b2drender = new Box2DDebugRenderer();
        world.setContactListener(new contactlisten());

    }
    @Override
    public void show() {

    }

    public void update(float dt){
        world.step( 1/ 60f, 6, 2);
    }
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.end();
        b2drender.render(world, gamecam.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    private void initBox2d(){
        world = new World(new Vector2(0f, -10f), false);
        BodyDef bdf = new BodyDef();
        bdf.type = BodyDef.BodyType.DynamicBody;
        bdf.position.set(gamecam.position.x, gamecam.position.y);
        FixtureDef fdf = new FixtureDef();
        ChainShape shape = new ChainShape();
        shape.createChain(ercihanshu());
        fdf.shape = shape;
        fdf.filter.categoryBits = CHAIN_BIT;
        fdf.filter.maskBits = XANIS_BIT;
        world.createBody(bdf).createFixture(fdf);

        //x anis;
        EdgeShape eshape = new EdgeShape();
        eshape.set(-5, 0, 5, 0);
        fdf.shape = eshape;
        fdf.filter.categoryBits = XANIS_BIT;
        fdf.filter.maskBits = CHAIN_BIT;
        BodyDef bdf1 = new BodyDef();
        bdf1.position.set (gamecam.position.x  , gamecam.position.y - 10);
        bdf1.type = BodyDef.BodyType.StaticBody;
        world.createBody(bdf1).createFixture(fdf);
    }
    private Vector2[] ercihanshu(){
        float x[] = new float[10];
        float y[] = new float[10];
        Vector2[] vector2s = new Vector2[10];
        for(int i = -5; i < 5; i++){
            x[i+5] = i;
            y[i+5] = 3*i*i + 2*i +2 ;        }
        for (int i = 0; i < x.length; i++) {
            vector2s[i] = new Vector2(x[i], y[i]);
        }
        return vector2s;
    }
    class contactlisten implements ContactListener{
        @Override
        public void beginContact(Contact contact) {
            Fixture xanis = contact.getFixtureA().getFilterData().categoryBits == XANIS_BIT ?
                    contact.getFixtureA() : contact.getFixtureB();
            Fixture chain = xanis == contact.getFixtureA() ? contact.getFixtureB() :
                    contact.getFixtureA();
            System.out.print("contacted");

        }

        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }
}
