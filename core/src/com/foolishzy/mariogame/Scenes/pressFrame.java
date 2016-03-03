package com.foolishzy.mariogame.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.foolishzy.mariogame.MyMarioGame;
import com.foolishzy.mariogame.Sprites.Mario;

import java.text.Format;

/**
 * Created by foolishzy on 2016/1/11.
 * <p/>
 * funcction:在屏幕上现实加入的变量
 *
 * <p/>
 * others:
 */
public class pressFrame {
    private Table tab;
    public Stage stage;
//    需要显示的变量
    private Array<Object> varsObjects;
//    需要显示的变量的现实format，显示的格式
    private Array<String> varsFormats;
    private Viewport pressport;
    public pressFrame(SpriteBatch sb){
        pressport=new FitViewport(MyMarioGame.V_WIDTH,MyMarioGame.V_HEIGTH,new OrthographicCamera());

        varsObjects = new Array<Object>();
        varsFormats = new Array<String>();
        tab=new Table();
        tab.setFillParent(true);
        tab.bottom();
        stage= new Stage(pressport,sb);

    }
    public void addVars(Object object,String format ){
        varsObjects.add(object);
        varsFormats.add(format);


    }

    public void setLabel(int cols){
//        cols 每一行显示的列数
        tab.clear();
        Label lab;
        for (int i = 0; i < varsObjects.size; i++) {
            lab = new Label(String.format(varsFormats.get(i),varsObjects.get(i)),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

            if(((i + 1) %cols)==0) {  tab.row();}
            tab.add(lab).expandX();

        }
        stage.addActor(tab);
        varsObjects.clear();
        varsFormats.clear();
    }

    public void dispose(){
        stage.dispose();
    }
}
