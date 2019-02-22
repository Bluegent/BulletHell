package com.bluegent.hell.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bluegent.config.GameCfg;
import com.bluegent.hell.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = GameCfg.FPS;
		config.width = GameCfg.Width;
		config.height = GameCfg.Height;
		config.samples = 16;
		new LwjglApplication(new MainGame(), config);
	}
}
