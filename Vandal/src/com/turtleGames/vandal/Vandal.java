package com.turtleGames.vandal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.turtleGames.vandal.screens.LoadingScreen;

public class Vandal extends Game {

	public static FPSLogger fps;
	public AssetManager assets;

	@Override
	public void create() {
		// Settings.load();

		setScreen(new LoadingScreen(this));

		fps = new FPSLogger();
	}

	@Override
	public void dispose() {
		super.dispose();
		getScreen().dispose();
	}

	@Override
	public void render() {
		super.render();
		fps.log();
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
}