package com.mygdx.spaceaircraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.spaceaircraft.SpaceAircraftMain;

public class GameOver implements Screen {
    SpaceAircraftMain game;

    private static final int button_Width = 220;
    private static final int button_Height = 35;
    private static final int playAgain_Y = 230;
    private static final int MainMenu_Y = 160;
    private static final int Score_Width = 20;
    private static final int Score_Y = 550;

    private int HighScore;

    int score;

    private Texture backGround;
    private Texture playAgain1;
    private Texture playAgain2;
    private Texture mainMenu1;
    private Texture mainMenu2;

    BitmapFont scoreFont;

    public GameOver(SpaceAircraftMain game,int score){
        this.game = game;
        this.score= score;
        playAgain1 = new Texture("PLayAgain(NoInteract).png");
        playAgain2 = new Texture("PLayAgain(Interact).png");
        mainMenu1 = new Texture("MainMenu(NoInteract).png");
        mainMenu2 = new Texture("MainMenu(Interact).png");
        backGround = new Texture("BGGameOver.png");


        Preferences preferences = Gdx.app.getPreferences("SpaceAircraft");
        this.HighScore = preferences.getInteger("HighScore", 0);

        if(score > HighScore){
            preferences.putInteger("HighScore", score);
            preferences.flush();
        }

        scoreFont = new BitmapFont(Gdx.files.internal("fonts/scores.fnt"));
    }


    @Override
    public void show () {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.4f, 1);
            game.batch.begin();

            game.batch.draw(backGround, 0, 0);
        //Draw button
        int x = (SpaceAircraftMain.WIDTH / 2 - button_Width / 2);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: \n" + score, Color.WHITE,0, Align.left,false);
        GlyphLayout HighscoreLayout = new GlyphLayout(scoreFont, "HighScore: \n" + HighScore, Color.WHITE,0, Align.left,false);
        scoreFont.draw(game.batch, scoreLayout,Score_Width, Score_Y);
        scoreFont.draw(game.batch,HighscoreLayout, 20, 470);


        //playAgain button
        if (Gdx.input.getX() < x + button_Width && Gdx.input.getX() > x && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < playAgain_Y + button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > playAgain_Y){
            game.batch.draw(playAgain2, SpaceAircraftMain.WIDTH/2 - button_Width/2, playAgain_Y, button_Width, button_Height);
            if (Gdx.input.isTouched()) {
                    game.setScreen(new MainScreen(game));
                }
        } else {
            game.batch.draw(playAgain1, SpaceAircraftMain.WIDTH/2- button_Width/2, playAgain_Y, button_Width, button_Height);
        }

        //mainMenu button
        if (Gdx.input.getX() < x + button_Width && Gdx.input.getX() > x && SpaceAircraftMain.HEIGHT - Gdx.input.getY() < MainMenu_Y + button_Height && SpaceAircraftMain.HEIGHT - Gdx.input.getY() > MainMenu_Y) {
            game.batch.draw(mainMenu2, SpaceAircraftMain.WIDTH/2 - button_Width/2, MainMenu_Y, button_Width, button_Height);
            if (Gdx.input.isTouched()) {
                game.setScreen(new MenuScreen(game));
            }
        } else {
            game.batch.draw(mainMenu1, SpaceAircraftMain.WIDTH/2 - button_Width/2, MainMenu_Y, button_Width, button_Height);
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
