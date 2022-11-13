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
    private static final int LOGO = 500;
    private static final int Exit_Button_Width = 100;
    private static final int Exit_Button_Height = 100;


    Texture PlayButtonA;
    Texture PlayButtonB;
    Texture ExitButtonA;
    Texture ExitButtonB;

    Texture Background;

    Texture Logo;
    SpaceAircraftMain game;
    public MenuScreen(SpaceAircraftMain game){
        //Menu icon
        this.game = game;
        PlayButtonA = new Texture("playButtonA.png");
        PlayButtonB = new Texture("playButtonB.png");
        ExitButtonA = new Texture("exitButtonA.png");
        ExitButtonB = new Texture("exitButtonB.png");

        Logo = new Texture("Logo.png");


        Background = new Texture("Space.png");

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

        game.batch.draw(Background,0, 0);

        game.batch.draw(Logo,x - 100,LOGO, 300, 100);
        if(Gdx.input.getX() < x + Play_Button_Width && Gdx.input.getX() > x && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < Play_Button_Y + Play_Button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > Play_Button_Y ){
            game.batch.draw(PlayButtonA,x -30 ,Play_Button_Y + 10, Play_Button_Width + 50,Play_Button_Height);
            if(Gdx.input.isTouched()){
                game.setScreen(new MainScreen(game));
            }
        }else {
            game.batch.draw(PlayButtonB,x - 110 ,Play_Button_Y - 10, Play_Button_Width+ 210,Play_Button_Height + 30 );
        }
        if(Gdx.input.getX() < y + Exit_Button_Width && Gdx.input.getX() > y && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < Exit_Button_Y + Exit_Button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > Exit_Button_Y ){
            game.batch.draw(ExitButtonA,y - 55,Exit_Button_Y + 28 ,Exit_Button_Width + 97,Exit_Button_Height - 16);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }else {
            game.batch.draw(ExitButtonB,y - 23 ,Exit_Button_Y + 40, Exit_Button_Width + 33,Exit_Button_Height - 43);
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
