package com.mygdx.spaceaircraft.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.spaceaircraft.setting.React;


public class BigAsteroid implements Entity {

    public static final int SPEED = 100;
    public int WIDTH;
    public int HEIGHT;


    public Texture texture;



    float x,y;
    float bigAsteroidHealth = 1;
    React react;

    public boolean remove = false;


    public BigAsteroid(float x, float y, Texture texture, int WIDTH, int HEIGHT){


        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.react = new React(x,y, WIDTH, HEIGHT);

        this.texture = texture;
    }

    public void decreaseHealth(float amo){
        bigAsteroidHealth -= amo;
    }

    public float getBigAsteroidHealth() {
        return bigAsteroidHealth;
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
