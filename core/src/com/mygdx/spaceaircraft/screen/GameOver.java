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
import com.mygdx.spaceaircraft.SpaceAircraftMain;

public class GameOver implements Screen {
    SpaceAircraftMain game;

    private static final int Banner_width = 350;
    private static final int Banner_height = 100;
    int score,highscore;
    Texture gameOverBanner;
    BitmapFont scoreFont;

    public GameOver(SpaceAircraftMain game,int score){
        this.game = game;
        this.score= score;

        Preferences preferences = Gdx.app.getPreferences("SpaceAircraft");
        this.highscore = preferences.getInteger("HighScore", 0);

        if(score > highscore){
            preferences.putInteger("HighScore", score);
            preferences.flush();
        }

        gameOverBanner = new Texture("game_over.png");
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/scores.fnt"));
    }


    @Override
    public void show () {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();


        game.batch.draw(gameOverBanner, Gdx.graphics.getWidth() / 2- Banner_width/2,Gdx.graphics.getHeight()-Banner_height-15,Banner_width,Banner_height);
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: \n" + score, Color.WHITE,0, Align.left,false);
        GlyphLayout HighscoreLayout = new GlyphLayout(scoreFont, "HighScore: \n" + highscore, Color.WHITE,0, Align.left,false);
        scoreFont.draw(game.batch, scoreLayout,Gdx.graphics.getWidth()/2 - scoreLayout.width/2, Gdx.graphics.getHeight() - Banner_height - 15 * 2);
        scoreFont.draw(game.batch,HighscoreLayout,Gdx.graphics.getWidth()/2 - HighscoreLayout.width/2, Gdx.graphics.getHeight() - Banner_height - scoreLayout.height - 15 * 3);

        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont,"Play Again");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Main Menu");

        float playAgainX =Gdx.graphics.getWidth() / 2 - tryAgainLayout.width/2;
        float playAgainY = Gdx.graphics.getHeight() /2 - tryAgainLayout.height/2;

        float mainMenuX =Gdx.graphics.getWidth() / 2 - mainMenuLayout.width/2;
        float mainMenuY = Gdx.graphics.getHeight() /2 - mainMenuLayout.height/2 - tryAgainLayout.height - 30;

        //Draw button
        scoreFont.draw(game.batch,tryAgainLayout,playAgainX,playAgainY);
        scoreFont.draw(game.batch,mainMenuLayout,mainMenuX,mainMenuY);

        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();



        //Play Again button click
        if(Gdx.input.isTouched()){
            //PlayAgain
            if( touchX > playAgainX && touchX < playAgainX + tryAgainLayout.width && touchY > playAgainY - tryAgainLayout.height && touchY< playAgainY){
                this.dispose();
                game.batch.end();
                game.setScreen(new MainScreen(game));
                return;
            }
            //Menu
            if( touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY){
                this.dispose();
                game.batch.end();
                game.setScreen(new MenuScreen(game));
                return;
            }


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
