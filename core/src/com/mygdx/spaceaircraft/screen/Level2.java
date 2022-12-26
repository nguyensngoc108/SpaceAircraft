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
    public static final float SHOOT_TIME = 0.7f;

    public static final float Min_Asteroid_Spawn_Time = 0.4f;
    public static final float Max_Asteroid_Spawn_Time = 0.7f;

    public static final float Max_SepAsteroid_Spawn_Time = 0.8f;

    public static final float Min_SepAsteroid_Spawn_Time = 0.2f;

    public  static  final int level =2;




    Animation[] rolls;



    float x;
    float y;
    float stateTime;

    Boss boss;
    float rolltimer;
    float shootTimer;
    float asteroidSpawnTime;
    float sepAsteroidSpawnTime;


    SpaceAircraftMain game;

    ArrayList<Bullet> bullets;
    ArrayList<BigAsteroid> asteroids;
    ArrayList<Effect> effects;
    ArrayList<BigAsteroid> sepAsteroids;
    ArrayList<BigAsteroid> smallAsteroids;

    React playerReact;
    Texture blank;
    Texture Background;
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
        sepAsteroidSpawnTime = random.nextFloat() *(Max_SepAsteroid_Spawn_Time - Min_SepAsteroid_Spawn_Time) + Max_SepAsteroid_Spawn_Time;


        //Create an array for objectives
        bullets = new ArrayList<Bullet>();
        asteroids = new ArrayList<BigAsteroid>();
        effects = new ArrayList<Effect>();
        blank = new Texture("blank.png");
        playerReact = new React(0,0,AIRCRAFT_WIDTH,AIRCRAFT_HEIGHT);
        sepAsteroids = new ArrayList<BigAsteroid>();
        smallAsteroids = new ArrayList<BigAsteroid>();
        boss = new Boss(new Texture("bossNgoc1.png"), new Texture("bossNgoc2.png"));
        Background = new Texture("BackgroundLevel2.jpg");



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

        //update boss
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
            asteroids.add(new BigAsteroid(random.nextInt(Gdx.graphics.getWidth() - 16), Gdx.graphics.getHeight(), new Texture("asteroid1.png"),16,16));

        }

        //Objectives update
        ArrayList<BigAsteroid> removeAsteroid = new ArrayList<BigAsteroid>();
        for(BigAsteroid asteroid: asteroids){
            asteroid.update(delta);
            if (asteroid.remove) {
                asteroids.removeAll(removeAsteroid);
            }
        }

        //Objective spawn
        sepAsteroidSpawnTime -= delta;
        if (sepAsteroidSpawnTime <= 0){
            sepAsteroidSpawnTime = random.nextFloat() * (Max_SepAsteroid_Spawn_Time - Min_SepAsteroid_Spawn_Time) + Min_SepAsteroid_Spawn_Time;
            sepAsteroids.add(new BigAsteroid(random.nextInt(Gdx.graphics.getWidth() - 32), Gdx.graphics.getHeight(), new Texture("Asteroid2.png"),32,32));
        }

        //Objectives updates
        ArrayList<BigAsteroid> removeSepAsteroid = new ArrayList<BigAsteroid>();
        for(BigAsteroid sepAsteroid: sepAsteroids){
            sepAsteroid.update(delta);
            if (sepAsteroid.remove)
                sepAsteroids.removeAll(removeSepAsteroid);
        }

        //Small asteroid update
        ArrayList<BigAsteroid> removeSmallAsteroid = new ArrayList<BigAsteroid>();
        for (BigAsteroid smallAsteroid : smallAsteroids) {
            smallAsteroid.update(delta);
            if (smallAsteroid.remove)
                smallAsteroids.removeAll(removeSmallAsteroid);
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
        //bullet hit asteroid
        for(Bullet bullet: bullets){
            for(BigAsteroid asteroid: asteroids){
                if(bullet.getReact().collidesWith(asteroid.getReact())){
                    bulletsToRemove.add(bullet);
                    removeAsteroid.add(asteroid);
                    effects.add(new Effect(asteroid.getX(),asteroid.getY()));
                }
            }
        }
        asteroids.removeAll(removeAsteroid);
        bullets.removeAll(bulletsToRemove);

        //bullet sepAsteroid and seperate into 2 smaller astetroid
        for(Bullet bullet: bullets){
            for(BigAsteroid sepAsteroid: sepAsteroids){
                if(bullet.getReact().collidesWith(sepAsteroid.getReact())){
                    bulletsToRemove.add(bullet);
                    removeSepAsteroid.add(sepAsteroid);
                    effects.add(new Effect(sepAsteroid.getX(),sepAsteroid.getY()));

                    //Spawn small asteroid
                    smallAsteroids.add(new BigAsteroid(sepAsteroid.getX() + 10, sepAsteroid.getY() - 10,new Texture("SmallAsteroid.png"),16,16));
                    smallAsteroids.add(new BigAsteroid(sepAsteroid.getX() - 10, sepAsteroid.getY(),new Texture("SmallAsteroid.png"),16,16));
                }
            }
        }
        sepAsteroids.removeAll(removeSepAsteroid);
        bullets.removeAll(bulletsToRemove);



        //bullet hit smallAsteroid
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

        //sepAsteroid hit player
        for (BigAsteroid sepAsteroid: sepAsteroids){
            if(sepAsteroid.getReact().collidesWith(playerReact)){
                removeSepAsteroid.add(sepAsteroid);
                effects.add(new Effect(sepAsteroid.getX(),sepAsteroid.getY()));
                health -= 0.2;

                //Game Lost
                if (health <= 0){
                    this.dispose();
                    game.setScreen(new GameLost(game,level));
                    return;
                }
            }
        }
        sepAsteroids.removeAll(removeSepAsteroid);

        // smallAsteroid hit player
        for (BigAsteroid smallAsteroid: smallAsteroids){
            if(smallAsteroid.getReact().collidesWith(playerReact)){
                removeSmallAsteroid.add(smallAsteroid);
                effects.add(new Effect(smallAsteroid.getX(),smallAsteroid.getY()));
                health -= 0.1;

                //Game Over
                if (health <= 0){
                    this.dispose();
                    game.setScreen(new GameLost(game,level));
                    return;
                }
            }
        }
        smallAsteroids.removeAll(removeSmallAsteroid);

        //asteroid hit player
        for (BigAsteroid asteroid: asteroids){
            if(asteroid.getReact().collidesWith(playerReact)){
                removeAsteroid.add(asteroid);
                effects.add(new Effect(asteroid.getX(),asteroid.getY()));
                health -= 0.15;

                //Game Lost
                if (health <= 0){
                    this.dispose();
                    game.setScreen(new GameLost(game, level));
                    return;
                }
            }
        }
        asteroids.removeAll(removeAsteroid);

        // bullets hit boss
        for(Bullet bullet : bullets){
            if(bullet.getReact().collidesWith(boss.getReact())){
                bulletsToRemove.add(bullet);
                boss.decreaseBossHealth(0.05);

                //Game complete
                if(boss.getBossHealth() <= 0){
                    this.dispose();
                    game.setScreen(new LevelComplete(game,level));
                    return;
                }
            }
        }
        bullets.removeAll(bulletsToRemove);

        // remove sepAsteroid and asteroid that are near
        for(BigAsteroid sepAsteroid : sepAsteroids){
            for(BigAsteroid asteroid : asteroids){
                if( sepAsteroid.getReact().collidesWith(asteroid.getReact())){
                    removeSepAsteroid.add(sepAsteroid);
                    removeAsteroid.add(asteroid);
                }
            }
        }
        asteroids.removeAll(removeAsteroid);
        sepAsteroids.removeAll(removeSepAsteroid);



        stateTime += delta;

        ScreenUtils.clear(0.15f, 0.15f , 0.4f, 1);
        game.batch.begin();

        game.batch.draw(Background, 0, 0);

        for(Bullet bullet: bullets){
            bullet.render(game.batch);
        }

        for(BigAsteroid asteroid: asteroids){
            asteroid.render(game.batch);
        }

        for(BigAsteroid sepAsteroid: sepAsteroids){
            sepAsteroid.render(game.batch);
        }

        for(BigAsteroid smallAsteroid: smallAsteroids){
            smallAsteroid.render(game.batch);
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
        game.batch.draw(blank, 0 ,715 , Gdx.graphics.getWidth() * (float) boss.getBossHealth() ,5);
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
