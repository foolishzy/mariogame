package com.foolishzy.mariogame.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.foolishzy.mariogame.MyMarioGame;

/**
 * Created by foolishzy on 2016/1/27.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class scoreScreen implements com.badlogic.gdx.Screen{
    private SpriteBatch batch;
    private MyMarioGame game;
    private Label label1;
    private Table table;
    private Stage stage;
    private FitViewport gameport;
    private OrthographicCamera gamecam;
    public scoreScreen(MyMarioGame game){
        gameport = new FitViewport(MyMarioGame.V_WIDTH ,
                MyMarioGame.V_HEIGTH);
        gamecam = new OrthographicCamera();
        this.game = game;
        this.batch = game.batch;
        table = new Table();
        stage = new Stage(gameport, game.batch);
        label1 = new Label("YOU WIN", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        table.center();
        table.setFillParent(true);
        table.add(label1).center();
        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(gamecam.combined);
        stage.draw();

        update(delta);
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
        stage.dispose();
    }
    public void update(float dt){
        if(Gdx.input.justTouched()){
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }}
