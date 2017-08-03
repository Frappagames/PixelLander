package com.frappagames.pixellander.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.frappagames.pixellander.PixelLander;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "PIXEL LANDER";
		config.width = 512;
		config.height = 512;
		new LwjglApplication(new PixelLander(), config);
	}
}
