package com.mygdx.spaceaircraft.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Effect {

    public static final float frameL = 0.2f;
    public static final int Offset = 8;
    public static final int Size = 32;

    private static Animation animation = null;
    float x,y;
    float stateTime;

    public boolean remove = false;

    public Effect(float x, float y){
        this.x = x - Offset;
        this.y = y - Offset;
        stateTime = 0;


        if(animation == null){
            animation = new Animation(frameL, TextureRegion.split(new Texture("effect.png"),Size,Size)[0]);
        }
    }

    public void update(float deltaTime){
        stateTime += deltaTime;
        if(animation.isAnimationFinished(stateTime)){
            remove = true;
        }

    }

    public void render(SpriteBatch batch){
        batch.draw((TextureRegion) animation.getKeyFrame(stateTime,true),x,y);
    }
}
