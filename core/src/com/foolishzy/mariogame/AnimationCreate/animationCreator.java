package com.foolishzy.mariogame.AnimationCreate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by foolishzy on 2016/1/2.
 * <p/>
 * funcction:切割texture来创建一个动画，完成后使用getanimation（）返回一个animation
 * 默认frameDuration=1f/30f
 * 未设置play-mode
 * <p/>
 * others:
 */
public class animationCreator {
    private static final float DURATION=1f/6f;
    private int totalcols;
    private int totalrows;
    private int startrow;
    private int startcol;
    private int endrow;
    private int endcol;
    private Texture texture;
    private String texturepath;
    private Animation animation;
//------------------------textureregion.split--------------------------------------------------------
    public animationCreator(Texture texture, int totalrows, int totalcols) {
        this.texture=texture;
        this.totalcols=totalcols;
        this.totalrows=totalrows;
        TextureRegion[][] sheet = TextureRegion.split(texture, texture.getWidth() / totalcols, texture.getHeight() / totalrows);
        TextureRegion[] keyFrames = new TextureRegion[totalcols * totalrows];
        int index = 0;
        for (int i = 0; i < totalrows; i++) {
            for (int i1 = 0; i1 < totalcols; i1++) {
                keyFrames[index++] = sheet[i][i1];
            }
        }
        animation = new Animation(DURATION, keyFrames);
    }

    public animationCreator(String texturepath, int totalrows, int totalcols) {
        this.totalcols=totalcols;
        this.totalrows=totalrows;
        this.texturepath=texturepath;
        Texture textureFromString = new Texture(Gdx.files.internal(texturepath));
        TextureRegion[][] sheet = TextureRegion.split(textureFromString, textureFromString.getWidth() / totalcols, textureFromString.getHeight() / totalrows);
        TextureRegion[] keyFrames = new TextureRegion[totalcols * totalrows];
        int index = 0;
        for (int i = 0; i < totalrows; i++) {
            for (int i1 = 0; i1 < totalcols; i1++) {
                keyFrames[index++] = sheet[i][i1];
            }
        }
        animation = new Animation(DURATION, keyFrames);
    }

    public animationCreator(Texture texture, int totalrows, int totalcols, int startrow,int endrow, int startcol,int endcol) {
        if (!isIndexOutofBounds(startcol,endcol)&&!isIndexOutofBounds(startrow,endrow)) {
            this.texture=texture;
            this.totalcols=totalcols;
            this.totalrows=totalrows;
            this.startcol=startcol;
            this.endcol=endcol;
            this.startrow=startrow;
            this.endrow=endrow;
            TextureRegion[][] sheet = TextureRegion.split(texture, texture.getWidth() / totalcols, texture.getHeight() / totalrows);
            TextureRegion[] keyFrames = new TextureRegion[(endcol - startcol + 1) * (endrow - startrow + 1)];
            int index = 0;
            for (int i = startrow - 1; i < endrow; i++) {
                for (int i1 = startcol - 1; i1 < endcol; i1++) {
                    keyFrames[index++] = sheet[i][i1];
                }
            }
            animation = new Animation(DURATION, keyFrames);

        }

    }

    public animationCreator(String texturepath, int totalrows, int totalcols,int startrow,int endrow, int startcol,int endcol) {
        if (!isIndexOutofBounds(startcol,endcol)&&!isIndexOutofBounds(startrow,endrow)) {
            this.totalcols=totalcols;
            this.totalrows=totalrows;
            this.startcol=startcol;
            this.endcol=endcol;
            this.startrow=startrow;
            this.endrow=endrow;
            this.texturepath=texturepath;
            Texture textureFromString = new Texture(Gdx.files.internal(texturepath));
            TextureRegion[][] sheet = TextureRegion.split(textureFromString, textureFromString.getWidth() / totalcols, textureFromString.getHeight() / totalrows);
            TextureRegion[] keyFrames = new TextureRegion[(endcol - startcol + 1) * (endrow - startrow + 1)];
            int index = 0;
            for (int i = startrow - 1; i < endrow; i++) {
                for (int i1 = startcol - 1; i1 < endcol; i1++) {
                    keyFrames[index++] = sheet[i][i1];
                }
            }
            animation = new Animation(DURATION, keyFrames);

        }
    }
    public animationCreator(TextureAtlas textureAtlas,String pngName,int startNum,int endNum){
        /*
            to create the animation by the name and the num series of picture
            such as: walkman01.png wlakman02.png walkman03.png ect...
        */
        int length=endNum-startNum+1;
        Array<TextureAtlas.AtlasRegion> keyFrames=new Array<TextureAtlas.AtlasRegion>(length);
        if (!isIndexOutofBounds(startNum,endNum)) {
            for (int i = 0; i < length; i++) {
                keyFrames.add(textureAtlas.findRegion(pngName+String.valueOf(startNum+i)));
            }
        }
        animation=new Animation(DURATION,keyFrames);
    }

    private boolean isIndexOutofBounds(int start,int end) {
        /*
        if the end num > the start num or not
         */
        if (start > end)
            Gdx.app.log("start_position>end_position in animationCreator", "please check animationonCreator", new IndexOutOfBoundsException());
        return start>end;
    }
    public Animation getAnimation() {
        return animation;
    }

}

