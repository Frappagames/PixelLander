package com.frappagames.pixellander;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.frappagames.pixellander.Tools.Assets;
import com.frappagames.pixellander.Tools.Settings;
import com.frappagames.pixellander.Screens.GameScreen;

public class PixelLander extends Game {
	public static final int WIDTH = 64;
	public static final int HEIGHT = 64;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();

		Assets.load();
		Settings.load();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		Assets.dispose();
	}
}
