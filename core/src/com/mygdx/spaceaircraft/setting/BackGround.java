package com.mygdx.spaceaircraft.setting;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.mygdx.spaceaircraft.SpaceAircraftMain;

public class BackGround {

    public static final int Default_Speed = 80;
    public static final int Accelarate = 50;
    public static final int Goal_Accelarate = 200;
    Texture img;
    float y1,y2;

    int speed; //pixels
    int goalSpeed;
    float imgScale;
    boolean speedFix;



    public BackGround(){
        img = new Texture("stars_background.png");
        y1 = 0 ;
        y2 = img.getHeight();
        speed = 0;
        goalSpeed = Default_Speed;
        imgScale = 0;
        speedFix = true;
    }

    public void update(float deltaTime, SpriteBatch batch){
        if(speed < goalSpeed){
            speed += Goal_Accelarate * deltaTime;
            if(speed > goalSpeed)
                speed = goalSpeed;
        } else if (speed > goalSpeed){
            speed -= Goal_Accelarate * deltaTime;
            if(speed < goalSpeed)
                speed = goalSpeed;
        }

        if(!speedFix)
            speed += Accelarate * deltaTime;

        y1 -= speed * deltaTime;
        y2 -= speed * deltaTime;

        //Once image reach the bottom, put it back to top
        if(y1 +img.getHeight() *imgScale <= 0){
            y1 = y2 + img.getHeight() *imgScale;
        }
        if(y2 +img.getHeight() *imgScale <= 0) {
            y2 = y1 + img.getHeight() * imgScale;
        }
        batch.draw(img,0,y1, SpaceAircraftMain.WIDTH,img.getHeight()*imgScale);
        batch.draw(img,0,y2, SpaceAircraftMain.WIDTH,img.getHeight()*imgScale);

    }

    public void resize(int w,int h){
        imgScale = w/img.getWidth();
    }

    public void setSpeed(){
        this.goalSpeed = goalSpeed;
    }

    public void setSpeedFix(boolean speedFix){
        this.speedFix = speedFix;
    }


}
