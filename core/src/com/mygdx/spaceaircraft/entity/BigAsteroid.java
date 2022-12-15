package com.mygdx.spaceaircraft.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.spaceaircraft.setting.React;


public class BigAsteroid {

    public static final int SPEED = 100;
    public int WIDTH = 32;
    public int HEIGHT = 32;

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

    public void decreaseHealth(){
        bigAsteroidHealth -= 0.5;
    }

    public float getBigAsteroidHealth() {
        return bigAsteroidHealth;
    }

    public void update (float deltaTime){
        y -= SPEED * deltaTime;
        if (y < -HEIGHT)
            remove = true;
        react.move(x,y);


    }



    public void render(SpriteBatch batch){
        batch.draw(texture,x,y,WIDTH,HEIGHT);
    }
    public React getReact(){
        return react;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public int getWIDTH(){
        return this.WIDTH;
    }
    public int getHEIGHT(){
        return this.HEIGHT;
    }
}
