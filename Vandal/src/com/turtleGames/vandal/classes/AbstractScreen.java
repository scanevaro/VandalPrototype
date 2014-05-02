package com.turtleGames.vandal.classes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {

	protected Game game;

	public AbstractScreen(Game game) {
		this.game = game;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}