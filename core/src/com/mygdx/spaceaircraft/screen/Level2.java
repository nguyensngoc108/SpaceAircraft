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
public class Level2 implements Screen {

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

    public static final float Max_Asteroid2_Spawn_Time = 0.7f;

    public static final float Min_Asteroid2_Spawn_Time = 0.4f;

    public static final float Max_SmallAsteroid_Spawn_Time = 0.7f;

    public static final float Min_SmallAsteroid_Spawn_Time = 0.4f;



    Animation[] rolls;



    float x;
    float y;
    float stateTime;

    Boss boss;
    float rolltimer;
    float shootTimer;
    float asteroidSpawnTime;
    float asteroid2SpawnTime;
    float smallAsteroidSpawnTime;

    SpaceAircraftMain game;

    ArrayList<Bullet> bullets;
    ArrayList<Asteroid> asteroids;
    ArrayList<Effect> effects;
    ArrayList<BigAsteroid> asteroids2;
    ArrayList<BigAsteroid> smallAsteroids;

    React playerReact;
    Texture blank;

    Random random;
    float health = 1;

    int roll;
    public Level2(SpaceAircraftMain game){
        this.game = game;
        y = 15;
        x = SpaceAircraftMain.WIDTH/2 - AIRCRAFT_WIDTH/2;
        shootTimer = 0;

        random = new Random();
        asteroidSpawnTime = random.nextFloat() * (Max_Asteroid_Spawn_Time - Min_Asteroid_Spawn_Time) + Min_Asteroid_Spawn_Time;
        asteroid2SpawnTime = random.nextFloat() *(Max_Asteroid2_Spawn_Time - Min_Asteroid2_Spawn_Time) + Max_Asteroid2_Spawn_Time;
        smallAsteroidSpawnTime = random.nextFloat() *(Max_SmallAsteroid_Spawn_Time - Min_SmallAsteroid_Spawn_Time) + Max_Asteroid2_Spawn_Time;

        //Create an array for objectives
        bullets = new ArrayList<Bullet>();
        asteroids = new ArrayList<Asteroid>();
        effects = new ArrayList<Effect>();
        blank = new Texture("blank.png");
        playerReact = new React(0,0,AIRCRAFT_WIDTH,AIRCRAFT_HEIGHT);
        asteroids2 = new ArrayList<BigAsteroid>();
        smallAsteroids = new ArrayList<BigAsteroid>();
        boss = new Boss();




        //Create a 2d array to store the model of the ship animation
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
            if (asteroid.remove) {
                asteroids.removeAll(removeAsteroid);
            }
        }

        //Objective spawn
        asteroid2SpawnTime -= delta;
        if (asteroid2SpawnTime <= 0){
            asteroid2SpawnTime = random.nextFloat() * (Max_Asteroid2_Spawn_Time - Min_Asteroid2_Spawn_Time) + Min_Asteroid2_Spawn_Time;
            asteroids2.add(new BigAsteroid(random.nextInt(Gdx.graphics.getWidth() - Asteroid.WIDTH), Gdx.graphics.getHeight(), new Texture("Asteroid2.png"),32,32));
        }

        //Objectives updates
        ArrayList<BigAsteroid> removeAsteroid2 = new ArrayList<BigAsteroid>();
        for(BigAsteroid asteroid2: asteroids2){
            asteroid2.update(delta);
            if (asteroid2.remove)
                asteroids2.removeAll(removeAsteroid2);
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
        if (y+AIRCRAFT_HEIGHT > Gdx.graphics.getHeight()/2){
            y =  Gdx.graphics.getHeight()/2 - AIRCRAFT_HEIGHT;
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

        //collide with bigasteroid and seperate into 2 smaller astetroid
        for(Bullet bullet: bullets){
            for(BigAsteroid asteroid2: asteroids2){
                if(bullet.getReact().collidesWith(asteroid2.getReact())){
                    bulletsToRemove.add(bullet);
                    removeAsteroid2.add(asteroid2);
                    effects.add(new Effect(asteroid2.getX(),asteroid2.getY()));

                    //Spawn small asteroid
                    smallAsteroids.add(new BigAsteroid(asteroid2.getX() + 10, asteroid2.getY(),new Texture("SmallAsteroid.png"),16,16));
                    smallAsteroids.add(new BigAsteroid(asteroid2.getX() - 10, asteroid2.getY(),new Texture("SmallAsteroid.png"),16,16));
                }
            }
        }
        asteroids2.removeAll(removeAsteroid2);
        bullets.removeAll(bulletsToRemove);

        //Small asteroid update
        ArrayList<BigAsteroid> removeSmallAsteroid = new ArrayList<BigAsteroid>();
        for (BigAsteroid smallAsteroid : smallAsteroids) {
            smallAsteroid.update(delta);
            if (smallAsteroid.remove)
                smallAsteroids.removeAll(removeSmallAsteroid);
        }

        //Collide with small asteroid
        for(Bullet bullet: bullets) {
            for (BigAsteroid smallAsteroid : smallAsteroids) {
                if (bullet.getReact().collidesWith(smallAsteroid.getReact())) {
                    bulletsToRemove.add(bullet);
                    removeSmallAsteroid.add(smallAsteroid);
                    effects.add(new Effect(smallAsteroid.getX(), smallAsteroid.getY()));
                }
            }
        }
        smallAsteroids.removeAll(removeSmallAsteroid);
        bullets.removeAll(bulletsToRemove);

        //Aircraft collides with asteroid2
        for (BigAsteroid asteroid2: asteroids2){
            if(asteroid2.getReact().collidesWith(playerReact)){
                removeAsteroid2.add(asteroid2);
                effects.add(new Effect(asteroid2.getX(),asteroid2.getY()));
                health -= 0.2;

                //Game Over
                if (health <= 0){
                    this.dispose();
                    game.setScreen(new GameOver(game,0));
                    return;
                }
            }
        }
        asteroids2.removeAll(removeAsteroid2);

        //Aircraft collides with small asteroid
        for (BigAsteroid smallAsteroid: smallAsteroids){
            if(smallAsteroid.getReact().collidesWith(playerReact)){
                removeSmallAsteroid.add(smallAsteroid);
                effects.add(new Effect(smallAsteroid.getX(),smallAsteroid.getY()));
                health -= 0.1;

                //Game Over
                if (health <= 0){
                    this.dispose();
                    game.setScreen(new GameOver(game,0));
                    return;
                }
            }
        }
        smallAsteroids.removeAll(removeSmallAsteroid);

        //Aircraft collides with normal asteroid
        for (Asteroid asteroid: asteroids){
            if(asteroid.getReact().collidesWith(playerReact)){
                removeAsteroid.add(asteroid);

                health -= 0.01;

                //Game Over
                if (health <= 0){
                    this.dispose();
                    game.setScreen(new GameOver(game, 0));
                    return;
                }
            }
        }
        stateTime += delta;

        ScreenUtils.clear(0.15f, 0.15f , 0.4f, 1);
        game.batch.begin();

        for(Bullet bullet: bullets){
            bullet.render(game.batch);
        }

        for(Asteroid asteroid: asteroids){
            asteroid.render(game.batch);
        }

        for(BigAsteroid asteroid2: asteroids2){
            asteroid2.render(game.batch);
        }

        for(BigAsteroid smallAsteroid: smallAsteroids){
            smallAsteroid.render(game.batch);
        }



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
