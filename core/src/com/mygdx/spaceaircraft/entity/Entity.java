package com.mygdx.spaceaircraft.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.spaceaircraft.setting.React;

public interface Entity {

    float x = 0,y = 0;


    public  void update(float deltaTime);

    public  void render(SpriteBatch batch);
    public React getReact();

    public float getX();

    public float getY();
}
