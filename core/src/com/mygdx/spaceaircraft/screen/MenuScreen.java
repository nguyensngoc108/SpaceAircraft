package com.mygdx.spaceaircraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.spaceaircraft.SpaceAircraftMain;

public class MenuScreen implements Screen {

    private static final int Play_Button_Width = 100;
    private static final int Play_Button_Height = 100;
    private static final int Play_Button_Y = 230;
    private static final int Exit_Button_Y = 100;

    private static final int Exit_Button_Width = 100;
    private static final int Exit_Button_Height = 100;


    Texture PlayButtonA;
    Texture PlayButtonB;
    Texture ExitButtonA;
    Texture ExitButtonB;

    SpaceAircraftMain game;
    public MenuScreen(SpaceAircraftMain game){
        //Menu icon
        this.game = game;
        PlayButtonA = new Texture("playButtonA.png");
        PlayButtonB = new Texture("badlogic.jpg");
        ExitButtonA = new Texture("playButtonA.png");
        ExitButtonB = new Texture("badlogic.jpg");

    }

    @java.lang.Override
    public void show () {

    }

    @java.lang.Override
    public void render (float delta) {
        ScreenUtils.clear(0.15f, 0.15f , 0.4f, 1);
        game.batch.begin();

        int x = (SpaceAircraftMain.WIDTH/2 - Play_Button_Width/2);
        int y = (SpaceAircraftMain.WIDTH/2 - Exit_Button_Width/2);
        if(Gdx.input.getX() < x + Play_Button_Width && Gdx.input.getX() > x && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < Play_Button_Y + Play_Button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > Play_Button_Y ){
            game.batch.draw(PlayButtonA,x,Play_Button_Y, Play_Button_Width,Play_Button_Height);
            if(Gdx.input.isTouched()){
                game.setScreen(new MainScreen(game));
            }
        }else {
            game.batch.draw(PlayButtonB,x,Play_Button_Y, Play_Button_Width,Play_Button_Height);
        }
        if(Gdx.input.getX() < y + Exit_Button_Width && Gdx.input.getX() > y && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < Exit_Button_Y + Exit_Button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > Exit_Button_Y ){
            game.batch.draw(ExitButtonA,y,Exit_Button_Y,Exit_Button_Width,Exit_Button_Height);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }else {
            game.batch.draw(ExitButtonB,y,Exit_Button_Y, Exit_Button_Width,Exit_Button_Height);
        }


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
