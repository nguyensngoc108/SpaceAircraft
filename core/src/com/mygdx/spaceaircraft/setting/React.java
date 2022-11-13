package com.mygdx.spaceaircraft.setting;

public class React {
    float x,y;
    int  width, height;

    public React(float x, float y, int width, int height ){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y){
        this.x = x;
        this.y = y;
    }

    public boolean collidesWith(React react){
        return x  < react.x + react.width && y < react.y + react.height && x + width > react.x && y + height > react.y;
    }
}
