package com.mygdx.spaceaircraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.spaceaircraft.SpaceAircraftMain;
import com.mygdx.spaceaircraft.entity.Bullet;


//Create a gameplay in main screen for the game
public class MainScreen implements Screen {

    public static final float SPEED = 300;
    public static final float AIRCRAFT_Animation_Speed = 0.5f;
    public static final int AIRCARFT_HEIGHT_PIXEL = 32;
    public static final int AIRCARFT_WIDTH_PIXEL = 17;
    public static final int AIRCRAFT_WIDTH = AIRCARFT_WIDTH_PIXEL*3;
    public static final int AIRCRAFT_HEIGHT = AIRCARFT_WIDTH_PIXEL*3;

    public static final float ROLL_SWITCH_TIME  = 0.15f;




    Animation[] rolls;



    float x;
    float y;
    float stateTime;
    SpaceAircraftMain game;

    Array<Bullet> bullets;
    float rolltimer;

    int roll;
    public MainScreen (SpaceAircraftMain game){
        this.game = game;
        y = 15;
        x = SpaceAircraftMain.WIDTH/2 - AIRCRAFT_WIDTH/2;
        bullets = new Array<>();

        //Create an 2d array to store the model of the ship animation
        roll = 2;
        rolltimer = 0;
        rolls = new Animation[5];

        TextureRegion[][] rollSpriteSheet =TextureRegion.split(new Texture("ship.png"),AIRCARFT_WIDTH_PIXEL,AIRCARFT_HEIGHT_PIXEL);

        //Roll the model to create animation
        rolls[0] = new Animation(AIRCRAFT_Animation_Speed,rollSpriteSheet[2]);//To the left
        rolls[1] = new Animation(AIRCRAFT_Animation_Speed,rollSpriteSheet[1]);
        rolls[2] = new Animation(AIRCRAFT_Animation_Speed,rollSpriteSheet[0]);
        rolls[3] = new Animation(AIRCRAFT_Animation_Speed,rollSpriteSheet[3]);
        rolls[4] = new Animation(AIRCRAFT_Animation_Speed,rollSpriteSheet[4]);


    }

    @java.lang.Override
    public void show () {


    }

    @java.lang.Override
    public void render (float delta) {
        //Bullet animation
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            bullets.add(new Bullet(x+4));
            bullets.add(new Bullet(x + AIRCRAFT_WIDTH - 4));
        }
        //Update
        Array<Bullet> bulletremove = new Array<Bullet>();

        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            y += SPEED * Gdx.graphics.getDeltaTime();
        }
        if (y+AIRCRAFT_HEIGHT > Gdx.graphics.getHeight()/2){
            y = Gdx.graphics.getHeight()/2 - AIRCRAFT_HEIGHT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y -= SPEED * Gdx.graphics.getDeltaTime();

            if(y<0 ){
                y=0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            x += SPEED * Gdx.graphics.getDeltaTime();
            //right border
            if(x + AIRCRAFT_WIDTH > Gdx.graphics.getWidth()){
                x = Gdx.graphics.getWidth() - AIRCRAFT_WIDTH;
            }
            //make animation looks smoother

            //roll model
            rolltimer += Gdx.graphics.getDeltaTime();
            if(rolltimer > -ROLL_SWITCH_TIME && roll  < 4){
                rolltimer = 0;
                roll++;
            } else{
                if(roll > 2){
                    rolltimer -= Gdx.graphics.getDeltaTime();
                    if(rolltimer < -ROLL_SWITCH_TIME && roll > 0){
                        rolltimer = 0;
                        roll--;
                    }
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            x -= SPEED * Gdx.graphics.getDeltaTime();
            //left border
            if(x<0){
                x=0;
            }
            //make animation looks smoother
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyJustPressed(Input.Keys.LEFT ) && roll >0  ){
                rolltimer = 0 ;
                roll --;
            }
            //roll model
            rolltimer -= Gdx.graphics.getDeltaTime();
            if(rolltimer <  -ROLL_SWITCH_TIME  && roll > 0){
                rolltimer = 0;
                roll--;
            } else{
                if ( roll < 2){
                    rolltimer += Gdx.graphics.getDeltaTime();
                    if(rolltimer > -ROLL_SWITCH_TIME && roll >= 4 ){
                        rolltimer = 0;
                        roll++;
                    }
                }
            }
        }
        stateTime += delta;

        ScreenUtils.clear(0.15f, 0.15f , 0.4f, 1);

        game.batch.begin();
        game.batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime,true), x,y , (float) AIRCRAFT_WIDTH, (float) AIRCRAFT_HEIGHT);
        game.batch.end();

    }

    @java.lang.Override
    public void resize (int width, int height) {

    }

    @java.lang.Override
    public void pause () {

    }

    @java.lang.Override
    public void resume () {

    }

    @java.lang.Override
    public void hide () {

    }

    @java.lang.Override
    public void dispose () {
    }
}
