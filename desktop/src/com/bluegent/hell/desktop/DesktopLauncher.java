package com.bluegent.hell.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bluegent.hell.MainGame;
import com.bluegent.utils.GameCfg;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = GameCfg.FPS;
		config.width = GameCfg.Width;
		config.height = GameCfg.Height;
		new LwjglApplication(new MainGame(), config);
	}
}
