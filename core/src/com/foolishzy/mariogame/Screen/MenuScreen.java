package com.foolishzy.mariogame.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.foolishzy.mariogame.MyMarioGame;

/**
 * Created by foolishzy on 2016/1/28.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class MenuScreen implements com.badlogic.gdx.Screen {
    private MyMarioGame game;
    private Stage stage;
    private Table table;
    private FitViewport gameport;
    private OrthographicCamera gamecam;
    public MenuScreen(MyMarioGame game){
        this.game = game;

        gameport = new FitViewport(MyMarioGame.V_WIDTH, MyMarioGame.V_HEIGTH );
        gamecam = new OrthographicCamera();
        Label label1 = new Label("MarioGame", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label label2 = new Label("touch the bottom to move", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label label3 = new Label("touch the top to jump", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label label4 = new Label("HAVE FUN", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table = new Table();
        stage = new Stage(gameport, game.batch);
        table.center();
        table.setFillParent(true);
        table.add(label1).center();
        table.row();
        table.add(label2).center();
        table.row();
        table.add(label3).center();
        table.row();
        table.add(label4).center();
        stage.addActor(table);
    }




    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gamecam.combined);
        stage.draw();
        update(delta);
    }
    public void update(float dt){
        if(Gdx.input.justTouched()){
            game.setScreen(new playScreen(game));
            dispose();
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
        stage.dispose();
    }
}
