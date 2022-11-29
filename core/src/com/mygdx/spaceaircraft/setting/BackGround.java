package com.mygdx.spaceaircraft.setting;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackGround {

    public static final int Default_Speed = 50;
    public static final int Accelarate = 50;
    public static final int Goal_Accelarate = 200;
    Texture img;
    float y1,y2;

    int speed; //pixels
    int goalSpeed;



    public BackGround(){
        img = new Texture("background.jpeg");
        y1 = 0 ;
        y2 = img.getHeight();
    }

    public void update(float deltaTime, SpriteBatch batch){

    }
}
