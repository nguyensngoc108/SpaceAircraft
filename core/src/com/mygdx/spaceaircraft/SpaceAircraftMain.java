package com.mygdx.spaceaircraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.spaceaircraft.screen.MainScreen;
import com.mygdx.spaceaircraft.screen.MenuScreen;

public class SpaceAircraftMain extends Game {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 720;
	public SpriteBatch batch;

	@Override
	public void create () {

		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}

}
