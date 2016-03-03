package com.foolishzy.mariogame.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.foolishzy.mariogame.MyMarioGame;

import javax.rmi.CORBA.Tie;

import javafx.stage.Stage;

/**
 * Created by foolishzy on 2016/1/7.
 * <p/>
 * funcction:
 * <p/>
 * others:
 */
public class Hud {
    public com.badlogic.gdx.scenes.scene2d.Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;
    private int dragFlagScrore;
    private int totalScore;
    private boolean addDragFlag;
    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label marioLabel;
    public Hud(SpriteBatch sb){
        worldTimer=300;
        timeCount=0;
        score=0;
        addDragFlag = false;
        viewport=new FitViewport(MyMarioGame.V_WIDTH,MyMarioGame.V_HEIGTH,new OrthographicCamera());
        stage=new com.badlogic.gdx.scenes.scene2d.Stage(viewport,sb);

        Table table=new Table();
        table.top();
        table.setFillParent(true);

        //%03d 三位数字
        countdownLabel=new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel=new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        timeLabel=new Label("TIME",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        levelLabel=new Label("1-1",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel=new Label("WORLD",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel=new Label("MARIO",new Label.LabelStyle(new BitmapFont(),Color.WHITE));

        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }
    public void update(float delttime){
        timeCount += delttime;
        if (timeCount >= 1){
            worldTimer --;
            countdownLabel.setText(String.format("%03d",worldTimer));
            timeCount = 0;
        }
        if (addDragFlag && score != totalScore){
            score += 10;
            scoreLabel.setText(String.format("%06d", score));
//            MyMarioGame.ASSET_MANAGER.get("audio/finalScore.ogg", Sound.class).play();
            Gdx.app.log("final drag flag score","play the sound");
        }
    }

    public void addscore(int vaule){
        score += vaule;
        scoreLabel.setText(String.format("%06d",score));
    }
    public void dispose(){
        stage.dispose();
    }
    public void addDragFlagscore(int score){
        addDragFlag = true;
        dragFlagScrore = score;
        totalScore = score + this.score;
    }
}
