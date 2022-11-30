package com.mygdx.spaceaircraft.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.spaceaircraft.setting.React;

import java.util.Random;

enum Direction {L,R}

public class Boss {

    public static final int SPEED = 100;
    public static final int WIDTH = 105;
    public static final int HEIGHT = 135;

    private static final Direction[] DIRECTIONS = Direction.values();
    private Direction dir = Direction.R;

    public static Texture[] texture;



    float x,y;

    float bossHealth = 1;
    React react;

    public boolean remove = false;

    public Boss(){
        this.x = 200;
        this.y = 570;
        this.react = new React(x,y, WIDTH, HEIGHT);

        if (texture == null){
            texture =new Texture[2];
            texture[0] = new Texture("boss1.png");
            texture[1] = new Texture("boss2.png");
        }
    }



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
        if (x <= -5 || x >= 370){
            switch (dir){
                case L: dir = Direction.R; break;
                case R: dir = Direction.L; break;
            }
        }
    }



    public void render(SpriteBatch batch){
        if(x < 50){
            batch.draw(texture[1], x, y);}
        else if(x >= 50 && x <= 100) {
            batch.draw(texture[0], x, y);
        } else if(x > 100 && x <= 200){
            batch.draw(texture[1],x,y);
        }else if(x > 200 && x <= 300){
            batch.draw(texture[0], x, y);
        }else{batch.draw(texture[1], x, y);}
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

    public  float getBossHealth(){
        return bossHealth;
    }

    public  void decreaseBossHealth(){
        bossHealth -= 0.02;
    }

}
