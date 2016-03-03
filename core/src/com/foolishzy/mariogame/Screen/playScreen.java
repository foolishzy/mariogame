package com.foolishzy.mariogame.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.foolishzy.mariogame.Item.Item;
import com.foolishzy.mariogame.Item.Itemdef;
import com.foolishzy.mariogame.Item.Mushroom;
import com.foolishzy.mariogame.MyMarioGame;
import com.foolishzy.mariogame.Scenes.Hud;
import com.foolishzy.mariogame.Scenes.pressFrame;
import com.foolishzy.mariogame.Sprites.Bricks;
import com.foolishzy.mariogame.Sprites.Coin;
import com.foolishzy.mariogame.Sprites.Flag;
import com.foolishzy.mariogame.Sprites.Goomba;
import com.foolishzy.mariogame.Sprites.Grounds;
import com.foolishzy.mariogame.Sprites.Mario;
import com.foolishzy.mariogame.Sprites.Pipes;
import com.foolishzy.mariogame.others.WorldContactListener;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by foolishzy on 2016/1/7.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class playScreen implements Screen, GestureDetector.GestureListener {
    private Mario player;
    private Flag flag;
    private MyMarioGame game;
    private Viewport gameport;
    private OrthographicCamera gamecam;
    public static Hud hud;

    private TmxMapLoader mapLoader;
    public static TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
//    private Box2DDebugRenderer b2dr;
    private Array<Goomba> goombalist;
    private com.foolishzy.mariogame.Scenes.pressFrame press;

    public static final int BRICK_SCORE = 10;
    public static final int COIN_SCORE = 50;
    public static final int GET_MUSHROOM = 200;
    public static final int KILL_GOOMBA = 200;

    private Array<Item> items;
    private LinkedBlockingQueue<Itemdef> itemToSpawn;
    private boolean move_left;
    private boolean move_right;
    public playScreen(MyMarioGame game) {
        this.game = game;

        gamecam=new OrthographicCamera();
        //动态的调整，以适应屏幕的大小，起转换作用，游戏的设计过程中不需要做太多对于gameport的操作
        //游戏的大小为V_WIDTH*V_HEIGHT;
        gameport=new FitViewport(MyMarioGame.V_WIDTH / MyMarioGame.PPM,MyMarioGame.V_HEIGTH / MyMarioGame.PPM,gamecam);
        hud=new Hud(game.batch);

        mapLoader=new TmxMapLoader();
        map=mapLoader.load("mariomap1-1.tmx");

        renderer=new OrthogonalTiledMapRenderer(map,1 / MyMarioGame.PPM);              //为何？
//-----------gameport.getScreenWidth和gameport.getScreenHeight在这里不起作用，返回值均是0；
//        gamecam.position.set(gameport.getScreenWidth()/2,gameport.getScreenHeight()/2,0);

//        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);
        gamecam.position.set(2f, gameport.getWorldHeight() / 2, 0);
//      new world(new vector(x,y),dosleep)
//        x 是 x方向的重力右正左负 y 是 y方向重力上正下负

        world = new World(new Vector2(0,-10), true );
//        b2dr = new Box2DDebugRenderer();

        player = new Mario(this);
        press=new pressFrame(game.batch);

        initB2dSprite();
        initDestination();
        //contact listener
        world.setContactListener(new WorldContactListener(this));
        goombalist = new Array<Goomba>();
        initGoomba(this);

        items = new Array<Item>();
        itemToSpawn = new LinkedBlockingQueue<Itemdef>();
        //register gesture listener
        Gdx.input.setInputProcessor(new GestureDetector(this));
    }
    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(player.isLived && !player.getCatchflag() && !player.getDestina){
            //if(Gdx.input.isKeyPressed(Input.Keys.UP) ){
              //  player.b2dbody.applyLinearImpulse(new Vector2(0,0.4f),player.b2dbody.getWorldCenter(),true);
            /*
            Apply an impulse at a point. This immediately modifies the velocity. It also modifies
            the angular velocity if the point of application is not at the center of mass. This
            wakes up the body.
             */
            //}
            if (move_left &&player.b2dbody.getLinearVelocity().x<1.5){
                player.b2dbody.applyLinearImpulse(new Vector2(-0.1f, 0),player.b2dbody.getWorldCenter(),true);
            }
            if (move_right && player.b2dbody.getLinearVelocity().x<1.5){
                player.b2dbody.applyLinearImpulse(new Vector2(0.1f, 0),player.b2dbody.getWorldCenter(),true);
            }
        }
    }


    public void update(float dt){
        handleSpawningItems();
        for (Item item : items) {
            item.update(dt);
        }
//        press.addVars(player.b2dbody.getPosition().x, "%f");
//        press.addVars(player.b2dbody.getPosition().y, "%f");
//        press.setLabel(2);
        handleInput(dt);
//      1/60f do calculation 60 times per seconds
        world.step(1/50f, 6, 2);
        if(player.b2dbody.getPosition().x > 2f){
            gamecam.position.x=player.b2dbody.getPosition().x;
        }
        gamecam.update();

        renderer.setView(gamecam);

        hud.update(dt);
        for (Goomba goomba : goombalist) {
            goomba.update(dt);
            if(goomba.getX() < player.getX() + MyMarioGame.DISTANCE_MARIO_ENEMY / MyMarioGame.PPM){
                goomba.b2body.setActive(true);
            }
        }
        player.update(dt);
        if (player.b2dbody.getPosition().y < - 16 / MyMarioGame.PPM) {
            player.isLived = false;
            game.setScreen(new GameoverScreen(game));
//            dispose();
        }
        if(player.getDestina && player.isLived && player.getCatchflag()){

            game.setScreen(new scoreScreen(game));
        }
        flag.update(dt);

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw map
        renderer.render();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Goomba goomba : goombalist) {
            goomba.draw(game.batch);
        }
        for (Item item : items) {
            item.draw(game.batch);
        }
        flag.draw(game.batch);

        game.batch.end();
        //draw box2ddebuglines
//        b2dr.render(world, gamecam.combined);

        //set the batch now to draw what the hud stage sees;
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        game.batch.setProjectionMatrix(press.stage.getCamera().combined);
        press.stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        gameport.update(width,height);
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
        world.dispose();
        hud.dispose();
        player.dispose();

    }

    private void initB2dSprite(){
        new Bricks(world, map, 6);
        new Pipes(world, map, 3);
        new Grounds(world, map, 5);
//        for(MapObject object : map.getLayers().get(4).getObjects().getByType(MapObject.class)){
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(MapObject.class)){
                new Coin(this, object);
        }
        flag = new Flag(this);
    }

    public World getWorld(){
        return world;
    }

    public TiledMap getMap(){
        return map;
    }

    private void initGoomba(playScreen screen){
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombalist.add( new Goomba( screen,(rect.getX() + rect.getWidth() / 2) / MyMarioGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyMarioGame.PPM));
        }
    }

    public void spawn(Itemdef idf){
        itemToSpawn.add( idf);
    }

    public void handleSpawningItems(){
        if(!itemToSpawn.isEmpty()){
            Itemdef idf = itemToSpawn.poll();
            if(idf.type == Mushroom.class){
                items.add(new Mushroom(this, idf.position.x, idf.position.y));
            }
        }
    }

    public void createMushroom(float x, float y ){
//        items.add( new Mushroom( this, x, y));
        spawn(new Itemdef(new Vector2(x, y), Mushroom.class));
    }
    private void initDestination(){
        Rectangle rect = ((RectangleMapObject) map.getLayers().get(9).getObjects().get("destination")).getRectangle();
        FixtureDef fixdf = new FixtureDef();
        BodyDef bdf = new BodyDef();
        bdf.type = BodyDef.BodyType.StaticBody;
        bdf.position.set((rect.getX() + rect.getWidth() / 2) / MyMarioGame.PPM,
                (rect.getY() + rect.getHeight() / 2) / MyMarioGame.PPM);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() / 2 /MyMarioGame.PPM,
                rect.getHeight() / 2 /MyMarioGame.PPM);
        fixdf.shape = shape;
        fixdf.isSensor = true;
        fixdf.filter.categoryBits = MyMarioGame.DESTINATION_BIT;
        fixdf.filter.maskBits = MyMarioGame.MARIO_BIT;
        world.createBody(bdf).createFixture(fixdf);
    }

    public Mario getPlayer(){
        return player;
    }


    // gesture listener
    @Override
    public boolean tap(float x, float y, int count, int button) {
        move_right = false;
        move_left = false;
            return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        if( y < gameport.getScreenHeight() / 2) {
            jump();
        }else if(x <= gameport.getScreenWidth() / 2){
                move_left = true;
        }
            else
                move_right = true;
        return false;
        }
    private void jump(){
        if(player.isLived && !player.getCatchflag() && !player.getDestina){
            if(player.getIsBig()){
                player.b2dbody.applyLinearImpulse(MyMarioGame.BIG_JUMP, player.b2dbody.getWorldCenter(), true);
            }else player.b2dbody.applyLinearImpulse(MyMarioGame.LITTLE_JUMP, player.b2dbody.getWorldCenter(), true);
        }
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
