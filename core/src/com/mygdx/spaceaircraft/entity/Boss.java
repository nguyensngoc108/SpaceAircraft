package com.mygdx.spaceaircraft.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.spaceaircraft.setting.React;

import java.util.Random;

enum Direction {L,R}

public class Boss implements  Entity{

    public static final int SPEED = 100;
    public static final int WIDTH = 105;
    public static final int HEIGHT = 135;

    private static final Direction[] DIRECTIONS = Direction.values();
    private Direction dir = Direction.R;

    public static Texture[] texture = new Texture[2];



    float x,y;

    double bossHealth = 1;
    React react;

    public boolean remove = false;

    public Boss(Texture texture0,Texture texture1){
        this.x = 200;
        this.y = 580;
        this.react = new React(x,y, WIDTH, HEIGHT);

        texture[0] = texture0;
        texture[1] = texture1;

    }


    @Override
    public void update (float deltaTime){
        checkHitWall();
        switch (dir){
            case L:
                x -= SPEED * deltaTime;
                break;
            case R:
                x += SPEED * deltaTime;
                break;
        }
        react.move(x,y);

    }

    public void checkHitWall(){
        if (x <= -0 || x >= Gdx.graphics.getWidth() - WIDTH){
            switch (dir){
                case L: dir = Direction.R; break;
                case R: dir = Direction.L; break;
            }
        }
    }


    @Override
    public void render(SpriteBatch batch){
        if(x < 50){
            batch.draw(texture[1], x, y,WIDTH,HEIGHT);}
        else if(x >= 50 && x <= 100) {
            batch.draw(texture[0], x, y, WIDTH, HEIGHT);
        } else if(x > 100 && x <= 200){
            batch.draw(texture[1],x,y,WIDTH,HEIGHT);
        }else if(x > 200 && x <= 300){
            batch.draw(texture[0], x, y,WIDTH,HEIGHT);
        }else{batch.draw(texture[1], x, y,WIDTH,HEIGHT);}
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

    public  double getBossHealth(){
        return bossHealth;
    }

    public  void decreaseBossHealth(double health){
        bossHealth -= health;
    }

}
