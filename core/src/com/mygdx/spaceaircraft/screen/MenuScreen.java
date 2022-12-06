package com.mygdx.spaceaircraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.spaceaircraft.SpaceAircraftMain;

public class MenuScreen implements Screen {

    private static final int Adventure_Button_Width = 100;
    private static final int Adventure_Button_Height = 100;
    private static final int Adventure_Button_Y = 230;
    private static final int Exit_Button_Y = 100;
    private static final int LOGO = 550;
    private static final int Exit_Button_Width = 100;
    private static final int Exit_Button_Height = 100;

    private static final int Endless_Button = 100;

    private static final int Endless_Button_Width = 100;

    private static final int Endless_Button_Height = 100;
    private static final int Endless_Button_Y = 200;

    Texture Adventure;
    Texture Endless;
    Texture ExitButtonA;
    Texture ExitButtonB;
    Texture Background;
    Texture Logo;
    SpaceAircraftMain game;

    Sound button_Sound;
    public MenuScreen(SpaceAircraftMain game){
        //Menu icon
        this.game = game;
        Adventure = new Texture("Adventure.jpg");
        Endless = new Texture("Endless.jpg");
        ExitButtonA = new Texture("Quit.jpg");
        ExitButtonB = new Texture("Quit.jpg");

        Logo = new Texture("Logo.png");

        Background = new Texture("BG2.png");
        //Make sound for button
        button_Sound = Gdx.audio.newSound(Gdx.files.internal("button_Sound.wav"));

    }

    @java.lang.Override
    public void show () {

    }

    @java.lang.Override
    public void render (float delta) {
        ScreenUtils.clear(0.15f, 0.15f , 0.4f, 1);
        game.batch.begin();

        int x = (SpaceAircraftMain.WIDTH/2 - Adventure_Button_Width/2);
        int y = (SpaceAircraftMain.WIDTH/2 - Exit_Button_Width/2);
        int z = (SpaceAircraftMain.WIDTH/2 - Endless_Button/2);

        game.batch.draw(Background,0, 0);

        game.batch.draw(Logo,x - 100,LOGO, 300, 100);


        //Adventure button
        if(Gdx.input.getX() < x + Adventure_Button_Width && Gdx.input.getX() > x && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < Adventure_Button_Y + Adventure_Button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > Adventure_Button_Y ){
            game.batch.draw(Adventure,x - 20 ,Adventure_Button_Y - 10 , Adventure_Button_Width + 50,Adventure_Button_Height - 20);
            if(Gdx.input.isTouched()){
                game.setScreen(new Level2(game));
            }
        }else {
            game.batch.draw(Adventure,x - 20 ,Adventure_Button_Y - 10, Adventure_Button_Width + 40,Adventure_Button_Height -30 );
        }

        // Endless Button
        if(Gdx.input.getX() < z + Endless_Button && Gdx.input.getX() > z && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < Endless_Button_Y + Endless_Button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > Endless_Button_Y - 30){
            game.batch.draw(Endless,x - 20 ,Endless_Button_Y - 70 , Endless_Button_Width + 50,Endless_Button_Height - 20);
            if(Gdx.input.isTouched()){
                game.setScreen(new MainScreen(game));
            }
        }else {
            game.batch.draw(Endless,x - 20 ,Endless_Button_Y - 70, Endless_Button_Width + 40,Endless_Button_Height -30 );
        }

        //Exit Button
        if(Gdx.input.getX() < y + Exit_Button_Width && Gdx.input.getX() > y && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < Exit_Button_Y + Exit_Button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > Exit_Button_Y - 100){
            game.batch.draw(ExitButtonA,y - 15,Exit_Button_Y -50 ,Exit_Button_Width + 30,Exit_Button_Height - 40);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }else {
            game.batch.draw(ExitButtonB,y - 17 ,Exit_Button_Y - 50, Exit_Button_Width + 30,Exit_Button_Height - 40);
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
