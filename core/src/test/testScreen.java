package test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.foolishzy.mariogame.MyMarioGame;
import com.foolishzy.mariogame.Sprites.Coin;
import com.sun.scenario.effect.impl.prism.ps.PPSBlend_ADDPeer;


/**
 * Created by foolishzy on 2016/1/14.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class testScreen implements Screen {
    private FitViewport gameport;
    private OrthographicCamera gamecam;
    private Game game;

    private testSprite sp;

    private World world;
//    private Fixture fixture;
//    private FixtureDef fixtureDef = new FixtureDef();
//    private BodyDef bodyDef = new BodyDef();
//    private Body body;
    private Box2DDebugRenderer b2dr;

    private SpriteBatch sb;

    public static final short PLANTFORM_BIT = 1;
    public  static final short BOX_BIT = 2;
    public static final short PLAYER_BIT = 4;
    public static final short MAP_BIT = 8;
    public static final short DESTORY_BIT = 16;

    public static Body box_body;


    public static OrthogonalTiledMapRenderer mapRenderer ;
    public static TiledMap map;

    public  testScreen(MyMarioGame game){
         Fixture fixture;
         FixtureDef fixtureDef = new FixtureDef();
         BodyDef bodyDef = new BodyDef();
         Body body;



        this.sb = game.batch;
        gamecam = new OrthographicCamera( MyMarioGame.V_WIDTH / MyMarioGame.PPM  , MyMarioGame.V_HEIGTH / MyMarioGame.PPM);

        gameport = new FitViewport(MyMarioGame.V_WIDTH / MyMarioGame.PPM, MyMarioGame.V_HEIGTH/ MyMarioGame.PPM, gamecam );

        b2dr = new Box2DDebugRenderer();

        world = new World( new Vector2(0, -10), true);

        //box
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(gamecam.position.x / MyMarioGame.PPM, (gamecam.position.y + 100) / MyMarioGame.PPM);
        box_body=world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2 / MyMarioGame.PPM, 2 / MyMarioGame.PPM);
        fixtureDef.filter.maskBits = PLANTFORM_BIT | MAP_BIT |PLAYER_BIT;
        fixtureDef.filter.categoryBits = BOX_BIT;
        fixtureDef.shape = shape;

//        fixtureDef.restitution = 1;
        box_body.createFixture(fixtureDef).setUserData("box");



//        //plantform
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        bodyDef.position.set(gamecam.position.x / MyMarioGame.PPM,gamecam.position.y / MyMarioGame.PPM);
//        body = world.createBody(bodyDef);
//
//        shape.setAsBox(40 / MyMarioGame.PPM, 5 / MyMarioGame.PPM);
//        fixtureDef.shape = shape;
//        fixtureDef.filter.categoryBits = PLANTFORM_BIT;
//        fixtureDef.filter.maskBits = BOX_BIT;
//        body.createFixture(fixtureDef).setUserData("plantform");

        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("mariomap1-1.tmx");
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            shape.setAsBox(rect.getWidth() / 2/ MyMarioGame.PPM, rect.getHeight() / 2/ MyMarioGame.PPM);
            fixtureDef.shape =shape;
            fixtureDef.filter.categoryBits = MAP_BIT;
            fixtureDef.filter.maskBits = BOX_BIT;
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / MyMarioGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyMarioGame.PPM);
            world.createBody(bodyDef).createFixture(fixtureDef).setUserData("brick");
        }
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            shape.setAsBox(rect.getWidth() / 2/ MyMarioGame.PPM, rect.getHeight() / 2/ MyMarioGame.PPM);
            fixtureDef.shape =shape;
            fixtureDef.filter.categoryBits = MAP_BIT;
            fixtureDef.filter.maskBits = BOX_BIT;
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / MyMarioGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyMarioGame.PPM);
            world.createBody(bodyDef).createFixture(fixtureDef).setUserData("coin");
        }
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            shape.setAsBox(rect.getWidth() / 2/ MyMarioGame.PPM, rect.getHeight() / 2/ MyMarioGame.PPM);
            fixtureDef.shape =shape;
            fixtureDef.filter.categoryBits = MAP_BIT;
            fixtureDef.filter.maskBits = BOX_BIT;
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / MyMarioGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyMarioGame.PPM);
            world.createBody(bodyDef).createFixture(fixtureDef).setUserData("ground");
        }



        Rectangle rect = new Rectangle(15 / MyMarioGame.PPM, 15 / MyMarioGame.PPM, 15/ MyMarioGame.PPM, 15/ MyMarioGame.PPM);
        PolygonShape shape1 = new PolygonShape();
        shape1.setAsBox( 15 / 2 / MyMarioGame.PPM,
                15 / 2 / MyMarioGame.PPM);
        fixtureDef.shape = shape1;
        bodyDef.position.set(rect.getX() + rect.getWidth() / 2,
                rect.getY() + rect.getHeight() / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        world.createBody(bodyDef).createFixture(fixtureDef);


        world.setContactListener(new testContactListener());

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / MyMarioGame.PPM);

        sp = new testSprite(this, 32, 32);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.render();
        sb.setProjectionMatrix(gamecam.combined);
        b2dr.render(world, gamecam.combined);

        sb.begin();
        sp.draw(sb);
        sb.end();

        update();
        sp.update(delta);

    }

    public  void update(){
        handleInput();
        world.step(1 / 60f, 6, 2);
        gamecam.position.x = box_body.getPosition().x;
        gamecam.position.y = box_body.getPosition().y;
        gamecam.update();
        mapRenderer.setView(gamecam);
    }

    public void handleInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            box_body.setLinearVelocity(0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            box_body.setLinearVelocity(1, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            box_body.setLinearVelocity(-1, 0);
        }
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
    public TiledMap getMap(){
        return map;
    }
    public  World getWorld(){
        return world;
    }
}
