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


//Create a gameplay in main screen for the game
public class BossScreen implements Screen {

    public static final float SPEED = 350;
    public static final float AIRCRAFT_Animation_Speed = 0.5f;
    public static final int AIRCARFT_HEIGHT_PIXEL = 32;
    public static final int AIRCARFT_WIDTH_PIXEL = 17;
    public static final int AIRCRAFT_WIDTH = AIRCARFT_WIDTH_PIXEL*3;
    public static final int AIRCRAFT_HEIGHT = AIRCARFT_WIDTH_PIXEL*3;

    public static final float ROLL_SWITCH_TIME  = 0.15f;
    public static final float SHOOT_TIME = 0.3f;

    public static final float Min_Asteroid_Spawn_Time = 0.4f;
    public static final float Max_Asteroid_Spawn_Time = 0.7f;
    public static final float Min_BigAsteroid_Spawn_Time = 0.2f;
    public static final float Max_BigAsteroid_Spawn_Time = 1f;




    Animation[] rolls;




    float x;
    float y;
    float stateTime;



    float rolltimer;
    float shootTimer;
    float asteroidSpawnTime;
    float bigAsteroidSpawnTime;
    SpaceAircraftMain game;

    ArrayList<Bullet> bullets;
    ArrayList<Asteroid> asteroids;
    ArrayList<BigAsteroid> bigAsteroids;
    ArrayList<Effect> effects;

    React playerReact;
    Texture blank;

    Boss boss;


    Random random;
    float health = 1;

    int roll;
    public BossScreen(SpaceAircraftMain game){
        this.game = game;
        y = 15;
        x = SpaceAircraftMain.WIDTH/2 - AIRCRAFT_WIDTH/2;
        shootTimer = 0;

        random = new Random();
        asteroidSpawnTime = random.nextFloat() * (Max_Asteroid_Spawn_Time - Min_Asteroid_Spawn_Time) + Min_Asteroid_Spawn_Time;
        bigAsteroidSpawnTime = random.nextFloat() * (Max_BigAsteroid_Spawn_Time - Min_BigAsteroid_Spawn_Time) + Min_BigAsteroid_Spawn_Time;

        //Create an array for objectives
        bullets = new ArrayList<Bullet>();
        asteroids = new ArrayList<Asteroid>();
        bigAsteroids = new ArrayList<BigAsteroid>();
        effects = new ArrayList<Effect>();
        blank = new Texture("blank.png");
        boss = new Boss();
        playerReact = new React(0,0,AIRCRAFT_WIDTH,AIRCRAFT_HEIGHT);





        //Create an 2d array to store the model of the ship animation
        roll = 2;
        rolltimer = 0;
        rolls = new Animation[5];

        TextureRegion[][] rollSpriteSheet =TextureRegion.split(new Texture("ship.png"),AIRCARFT_WIDTH_PIXEL,AIRCARFT_HEIGHT_PIXEL);

        //Roll the model to create animation
        rolls[0] = new Animation(AIRCRAFT_Animation_Speed,rollSpriteSheet[2]);//To the left
        rolls[1] = new Animation(AIRCRAFT_Animation_Speed,rollSpriteSheet[1]);
        rolls[2] = new Animation(AIRCRAFT_Animation_Speed,rollSpriteSheet[0]);//No tilt
        rolls[3] = new Animation(AIRCRAFT_Animation_Speed,rollSpriteSheet[3]);
        rolls[4] = new Animation(AIRCRAFT_Animation_Speed, rollSpriteSheet[4]);//To the right


    }


    @java.lang.Override
    public void show () {


    }

    @java.lang.Override
    public void render (float delta) {

        //Boss movement
        boss.update(delta);
        //Bullet animation
        shootTimer += delta;
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= SHOOT_TIME){
            shootTimer = 0;

            int offset = 4;
            if(roll == 1 || roll == 3)
                offset = 8;

            if (roll == 2 || roll == 4)
                offset = 16;

            bullets.add(new Bullet(x+ 45, y + offset));
            bullets.add(new Bullet(x + AIRCRAFT_WIDTH - 50, y + offset));
        }

        //Update
        ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        for(Bullet bullet: bullets){
            bullet.update(delta);
            if(bullet.remove)
                bulletsToRemove.add(bullet);
        }
        bullets.removeAll(bulletsToRemove);

        //Effect update
        ArrayList<Effect> removeEffect = new ArrayList<>();
        for(Effect effect: effects){
            effect.update(delta);
            if(effect.remove)
                removeEffect.add(effect);
        }
        effects.removeAll(removeEffect);

//        //Objectives spawn
        asteroidSpawnTime -= delta;
        if (asteroidSpawnTime <= 0){
            asteroidSpawnTime = random.nextFloat() * (Max_Asteroid_Spawn_Time - Min_Asteroid_Spawn_Time) + Min_Asteroid_Spawn_Time;
            asteroids.add(new Asteroid(random.nextInt(Gdx.graphics.getWidth() - Asteroid.WIDTH)));
        }


        //Objectives update
        ArrayList<Asteroid> removeAsteroid = new ArrayList<Asteroid>();
        for(Asteroid asteroid: asteroids){
            asteroid.update(delta);
            if (asteroid.remove)
                asteroids.removeAll(removeAsteroid);
        }


        //Objective spawn
        bigAsteroidSpawnTime -= delta;
        if (bigAsteroidSpawnTime <= 0){
            bigAsteroidSpawnTime = random.nextFloat() * (Max_BigAsteroid_Spawn_Time - Min_BigAsteroid_Spawn_Time) + Min_BigAsteroid_Spawn_Time;
            bigAsteroids.add(new BigAsteroid(random.nextInt(Gdx.graphics.getWidth() - BigAsteroid.WIDTH)));
        }
        //Objectives update
        ArrayList<BigAsteroid> removeBigAsteroid = new ArrayList<BigAsteroid>();
        for(BigAsteroid bigAsteroid: bigAsteroids){
            bigAsteroid.update(delta);
            if (bigAsteroid.remove)
                bigAsteroids.removeAll(removeBigAsteroid);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            x -= SPEED * Gdx.graphics.getDeltaTime();
            //left border
            if(x<0)
                x=0;

            //make animation looks smoother
            if(!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyJustPressed(Input.Keys.LEFT ) && roll >  0  ){
                rolltimer = 0;
                roll --;

            }

            //role model
            rolltimer -= Gdx.graphics.getDeltaTime();
            if(Math.abs(rolltimer) > ROLL_SWITCH_TIME && roll >  0){
                rolltimer -= ROLL_SWITCH_TIME;
                roll--;

                if(roll <0)
                    roll = 0;
            }
        } else{
            if(roll < 2) {
                //roll model
                rolltimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rolltimer) > ROLL_SWITCH_TIME && roll < 4) {
                    rolltimer -= ROLL_SWITCH_TIME;
                    roll++;

                }
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += SPEED * Gdx.graphics.getDeltaTime();
            //right border
            if (x + AIRCRAFT_WIDTH > Gdx.graphics.getWidth()) {
                x = Gdx.graphics.getWidth() - AIRCRAFT_WIDTH;
            }
            //make animation looks smoother
            if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.RIGHT ) && roll >  0  ){
                rolltimer = 0 ;
                roll --;

            }

            //role model
            rolltimer += Gdx.graphics.getDeltaTime();
            if (Math.abs(rolltimer) > ROLL_SWITCH_TIME && roll < 4) {
                rolltimer -= ROLL_SWITCH_TIME;
                roll++;
            }
        } else{
            if(roll > 2){
                rolltimer -= Gdx.graphics.getDeltaTime();
                if(Math.abs(rolltimer) > ROLL_SWITCH_TIME && roll > 0){
                    rolltimer -= ROLL_SWITCH_TIME;
                    roll--;

                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            y += SPEED * Gdx.graphics.getDeltaTime();
        }
        if (y+AIRCRAFT_HEIGHT > Gdx.graphics.getHeight()/4){
            y =  Gdx.graphics.getHeight()/4 - AIRCRAFT_HEIGHT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y -= SPEED * Gdx.graphics.getDeltaTime();
            //Make sure the ship won't get up of the screen
            if(y<0 ){
                y=0;
            }
        }

        //Update react objectives
        playerReact.move(x,y);
        //Loop
        for(Bullet bullet: bullets){
            for(Asteroid asteroid: asteroids){
                if(bullet.getReact().collidesWith(asteroid.getReact())){
                    bulletsToRemove.add(bullet);
                    removeAsteroid.add(asteroid);
                    effects.add(new Effect(asteroid.getX(),asteroid.getY()));

                }
            }
        }
        asteroids.removeAll(removeAsteroid);
        bullets.removeAll(bulletsToRemove);

        for(Bullet bullet: bullets){
            for(BigAsteroid bigAsteroid: bigAsteroids){
                if(bullet.getReact().collidesWith(bigAsteroid.getReact())){
                    bulletsToRemove.add(bullet);
                    bigAsteroid.decreaseHealth();
                    if(bigAsteroid.getBigAsteroidHealth() <= 0){
                        removeBigAsteroid.add(bigAsteroid);
                        effects.add(new Effect(bigAsteroid.getX(),bigAsteroid.getY()));
                    }


                }
            }
        }
        bigAsteroids.removeAll(removeBigAsteroid);
        bullets.removeAll(bulletsToRemove);


        for (Bullet bullet: bullets){
            if(bullet.getReact().collidesWith(boss.getReact())){
                bulletsToRemove.add(bullet);
                boss.decreaseBossHealth();
                effects.add(new Effect(boss.getX(),boss.getY()));

                //Game Over
                if (health <= 0){
                    this.dispose();
                    game.setScreen(new GameOver(game,0));
                    return;
                }
            }
        }
        bullets.removeAll(bulletsToRemove);


        for (Asteroid asteroid: asteroids){
            if(asteroid.getReact().collidesWith(playerReact)){
                removeAsteroid.add(asteroid);
                effects.add(new Effect(asteroid.getX(),asteroid.getY()));
                health -= 0.15;

                //Game Over
                if (health <= 0){
                    this.dispose();
                    game.setScreen(new GameOver(game,0));
                    return;
                }
            }
        }
        asteroids.removeAll(removeAsteroid);

        for (BigAsteroid bigAsteroid: bigAsteroids){
            if(bigAsteroid.getReact().collidesWith(playerReact)){
                removeBigAsteroid.add(bigAsteroid);
                effects.add(new Effect(bigAsteroid.getX(),bigAsteroid.getY()));
                health -= 0.35;

                //Game Over
                if (health <= 0){
                    this.dispose();
                    game.setScreen(new GameOver(game,0));
                    return;
                }
            }
        }
        bigAsteroids.removeAll(removeBigAsteroid);

        stateTime += delta;

        ScreenUtils.clear(0.15f, 0.15f , 0.4f, 1);
        game.batch.begin();


        for(Bullet bullet: bullets){
            bullet.render(game.batch);
        }

        for(Asteroid asteroid: asteroids){
            asteroid.render(game.batch);
        }

        for(BigAsteroid bigAsteroid: bigAsteroids){
            bigAsteroid.render(game.batch);
        }

        boss.render(game.batch);

        for(Effect effect: effects){
            effect.render(game.batch);
        }

        if(health > 0.6f)
            game.batch.setColor(Color.GREEN);
        else if (health > 0.2f)
            game.batch.setColor(Color.ORANGE);
        else
            game.batch.setColor(Color.RED);
        game.batch.draw(blank, 0 ,0 , Gdx.graphics.getWidth() * health,5);
        game.batch.draw(blank, 0 ,715 , Gdx.graphics.getWidth() * boss.getBossHealth() ,5);
        game.batch.setColor(Color.WHITE);
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
