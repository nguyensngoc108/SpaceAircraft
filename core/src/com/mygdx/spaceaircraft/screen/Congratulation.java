package com.mygdx.spaceaircraft.screen;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.spaceaircraft.SpaceAircraftMain;
import com.mygdx.spaceaircraft.entity.*;
import com.mygdx.spaceaircraft.setting.React;

public class Congratulation implements Screen {

    SpaceAircraftMain game;
    private static final int button_Width = 220;
    private static final int button_Height = 32;
    private static final int PlayAgain_Y = 240;
    private static final int MainMenu_Y = 180;
    private static final int Quit_Button_Width = 80;
    private static final int Quit_Button_Height = 30;
    private static final int Quit_Button_Y = 120;




    private Texture Background;
    private Texture PlayAgain1;
    private Texture PlayAgain2;
    private Texture MainMenu1;
    private Texture MainMenu2;
    private Texture Quit1;
    private Texture Quit2;


    public Congratulation(SpaceAircraftMain game) {
        this.game = game;

        PlayAgain1 = new Texture("PLayAgain(NoInteract).png");
        PlayAgain2 = new Texture("PLayAgain(Interact).png");

        MainMenu1 = new Texture("MainMenu(NoInteract).png");
        MainMenu2 = new Texture("MainMenu(Interact).png");

        Quit1 = new Texture("QUIT(NotInteract).png");
        Quit2 = new Texture("QUIT(Interact).png");

        Background = new Texture("BGMissionComplete.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f , 0.4f, 1);
        game.batch.begin();


        game.batch.draw(Background, 0, 0);


        //Draw button
        int x = (SpaceAircraftMain.WIDTH / 2 - button_Width / 2);
        int y = (SpaceAircraftMain.WIDTH/2 - Quit_Button_Width/2);


        //PlayAgain button
        if (Gdx.input.getX() < x + button_Width && Gdx.input.getX() > x && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < PlayAgain_Y + button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > PlayAgain_Y) {
            game.batch.draw(PlayAgain2, SpaceAircraftMain.WIDTH / 2 - button_Width / 2, PlayAgain_Y, button_Width, button_Height);
            if (Gdx.input.isTouched()) {
                game.setScreen(new BossScreen(game));
            }
        } else {
            game.batch.draw(PlayAgain1, SpaceAircraftMain.WIDTH/2- button_Width/2, PlayAgain_Y, button_Width, button_Height);
        }

        //MainMenu button
        if (Gdx.input.getX() < x + button_Width && Gdx.input.getX() > x && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < MainMenu_Y + button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > MainMenu_Y) {
            game.batch.draw(MainMenu2, SpaceAircraftMain.WIDTH/2 - button_Width/2, MainMenu_Y, button_Width, button_Height);
            if (Gdx.input.isTouched()) {
                game.setScreen(new MenuScreen(game));
            }
        } else {
            game.batch.draw(MainMenu1, SpaceAircraftMain.WIDTH/2 - button_Width/2, MainMenu_Y, button_Width, button_Height);
        }

        //Quit Button
        if (Gdx.input.getX() < y + Quit_Button_Width && Gdx.input.getX() > y && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < Quit_Button_Y + Quit_Button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > Quit_Button_Y){
            game.batch.draw(Quit2, y, Quit_Button_Y, Quit_Button_Width, Quit_Button_Height);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(Quit1, y, Quit_Button_Y, Quit_Button_Width, Quit_Button_Height);
        }
        game.batch.end();
    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}