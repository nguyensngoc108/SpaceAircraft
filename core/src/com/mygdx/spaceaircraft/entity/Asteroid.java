package com.mygdx.spaceaircraft.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.spaceaircraft.setting.React;


public class Asteroid implements Entity {

    public static final int SPEED = 100;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;


    public static Texture texture;

    float x,y;
    React react;

    public boolean remove = false;

    public Asteroid(float x){
        this.x = x;
        this.y = Gdx.graphics.getHeight();
        this.react = new React(x,y, WIDTH, HEIGHT);

        if (texture == null){
            texture =new Texture("asteroid.png");
        }
    }


    @Override
    public void update (float deltaTime){
        y -= SPEED * deltaTime;
        if (y < -HEIGHT)
            remove = true;
                react.move(x,y);
                
    }


    @Override
    public void render(SpriteBatch batch){

        batch.draw(texture,x,y,WIDTH,HEIGHT);
    }
    @Override
    public React getReact(){
        return react;
    }
    @Override
    public float getX(){
        return x;
    }
    @Override
    public float getY(){
        return y;
    }

}
