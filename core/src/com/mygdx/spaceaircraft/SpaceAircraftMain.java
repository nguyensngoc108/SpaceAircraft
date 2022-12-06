package com.mygdx.spaceaircraft;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.spaceaircraft.screen.BossScreen;
import com.mygdx.spaceaircraft.screen.GameOver;
import com.mygdx.spaceaircraft.screen.MenuScreen;
import com.mygdx.spaceaircraft.setting.BackGround;
import com.mygdx.spaceaircraft.setting.Setting;
public class SpaceAircraftMain extends Game {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 720;
	public SpriteBatch batch;
	private Music BGM_SOUND;
	public BackGround scrollingBackground;


	@Override
	public void create () {
		Music BGM_SOUND = Gdx.audio.newMusic(Gdx.files.internal("bgm.mp3"));
		batch = new SpriteBatch();
<<<<<<< HEAD
		this.scrollingBackground = new BackGround();
		this.setScreen(new GameOver(this,0));
=======
		this.setScreen(new BossScreen(this));
>>>>>>> 37ce9b7386c992af1a8338c9fef0f9b4dea79d7a


//		BGM_SOUND.setVolume(0.2f);
//		BGM_SOUND.setLooping(true);
//		BGM_SOUND.play();




	}

	@Override
	public void resize (int width,int height) {
		this.scrollingBackground = new BackGround();
		super.resize(width,height);


	}
	@Override
	public void render () {
		super.render();


	}

	@Override
	public void dispose () {

	}

	public float getGameSetting(){
		return getGameSetting();
	}


}
