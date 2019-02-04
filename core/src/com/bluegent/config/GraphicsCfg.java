package com.bluegent.config;

import com.badlogic.gdx.graphics.Color;

public class GraphicsCfg {
	public enum Trail
	{
		Off,
		Simple,
		Gradient,
		Alpha
	}
	public static final Trail lineTrails = Trail.Simple;
	public static final Trail rectangleTrails = Trail.Gradient;
	
	public static final Color BGColor = Color.BLACK;
}
