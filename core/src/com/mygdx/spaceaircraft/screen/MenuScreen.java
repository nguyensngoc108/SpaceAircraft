package com.mygdx.spaceaircraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.spaceaircraft.SpaceAircraftMain;

public class MenuScreen implements Screen {

    private static final int LOGO = 575;

    private static final int Adventure_Button_Width = 200;
    private static final int Adventure_Button_Height = 33;
    private static final int Adventure_Button_Y = 190;


    private static final int Endless_Button_Width = 160;
    private static final int Endless_Button_Height = 33;
    private static final int Endless_Button_Y = 135;

    private static final int Quit_Button_Width = 80;
    private static final int Quit_Button_Height = 30;
    private static final int Quit_Button_Y = 75;

    Texture Background;
    Texture Logo;
    Texture Adventure1;
    Texture Adventure2;
    Texture Endless1;
    Texture Endless2;
    Texture Quit1;
    Texture Quit2;

    SpaceAircraftMain game;

    Sound button_Sound;
    public MenuScreen(SpaceAircraftMain game){
        //Menu icon
        this.game = game;

        Logo = new Texture("Logo.png");

        Adventure1 = new Texture("ADV(NotInteract).png");
        Adventure2 = new Texture("ADV(Interact).png");

        Endless1 = new Texture("ENDLESS(NotInteract).png");
        Endless2 = new Texture("ENDLESS(Interact).png");

        Quit1 = new Texture("QUIT(NotInteract).png");
        Quit2 = new Texture("QUIT(Interact).png");

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
        int y = (SpaceAircraftMain.WIDTH/2 - Quit_Button_Width/2);
        int z = (SpaceAircraftMain.WIDTH/2 - Endless_Button_Width/2);

        game.batch.draw(Background,0, 0);

        game.batch.draw(Logo,x - 70 ,LOGO, 350, 100);


        //Adventure button
        if(Gdx.input.getX() < x + Adventure_Button_Width && Gdx.input.getX() > x && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < Adventure_Button_Y + Adventure_Button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > Adventure_Button_Y ){
            game.batch.draw(Adventure2, x, Adventure_Button_Y, Adventure_Button_Width, Adventure_Button_Height);
            if(Gdx.input.isTouched()){
                game.setScreen(new Level1(game));
            }
        }else {
            game.batch.draw(Adventure1, x, Adventure_Button_Y, Adventure_Button_Width, Adventure_Button_Height);
        }

        // Endless Button
        if(Gdx.input.getX() < z + Endless_Button_Width && Gdx.input.getX() > z && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < Endless_Button_Y + Endless_Button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > Endless_Button_Y){
            game.batch.draw(Endless2, x + 22, Endless_Button_Y, Endless_Button_Width, Endless_Button_Height);
            if(Gdx.input.isTouched()){
                game.setScreen(new MainScreen(game));
            }
        }else {
            game.batch.draw(Endless1, x + 22,Endless_Button_Y, Endless_Button_Width, Endless_Button_Height);
        }

        //Quit Button
        if(Gdx.input.getX() < y + Quit_Button_Width && Gdx.input.getX() > y && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < Quit_Button_Y + Quit_Button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > Quit_Button_Y){
            game.batch.draw(Quit2, y, Quit_Button_Y, Quit_Button_Width, Quit_Button_Height);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }else {
            game.batch.draw(Quit1, y, Quit_Button_Y, Quit_Button_Width, Quit_Button_Height);
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
