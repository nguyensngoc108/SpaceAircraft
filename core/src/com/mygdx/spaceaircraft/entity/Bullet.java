package com.mygdx.spaceaircraft.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.spaceaircraft.setting.React;

public class Bullet {
    React react;
    public static final int SPEED = 500;
    public static final int Width = 3;
    public static final int Height = 12;

    public static Texture texture;

    float x,y;

    public boolean remove = false;



    public Bullet(float x,float y){
        this.x = x;
        this.y = y;
        this.react = new React(x,y, Width, Height);

        if (texture == null){
            texture =new Texture("bullets.png");
        }
    }
    public void update (float deltaTime){
        y += SPEED * deltaTime;
        if (y > Gdx.graphics.getHeight())
            remove = true;
        react.move(x,y);



    }



    public void render(SpriteBatch batch){
        batch.draw(texture,x,y);
    }

    public React getReact(){
        return react;
    }

}


