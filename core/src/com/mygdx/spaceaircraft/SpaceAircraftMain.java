package com.mygdx.spaceaircraft;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.spaceaircraft.entity.Boss;
import com.mygdx.spaceaircraft.screen.*;
import com.mygdx.spaceaircraft.setting.Setting;
import jdk.tools.jmod.Main;

public class SpaceAircraftMain extends Game {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 720;
	public SpriteBatch batch;
	private Music BGM_SOUND;


	@Override
	public void create () {
		Music BGM_SOUND = Gdx.audio.newMusic(Gdx.files.internal("bgm.mp3"));
		batch = new SpriteBatch();


		this.setScreen(new MenuScreen(this));





		BGM_SOUND.setVolume(0.2f);
		BGM_SOUND.setLooping(true);
		BGM_SOUND.play();


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
