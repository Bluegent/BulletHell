package com.bluegent.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.utils.RenderHelper;

public class RectangleTrail extends Trail{

	private double angle;
	public RectangleTrail(Vector2 pos, int size, int count, Color color) {
		super(pos, size, count, color);
		angle = 0;
	}
	
	@Override
	public synchronized void draw(RenderHelper rh)
	{
		for(int i=0;i<positions.size-1;++i)
		{
			rh.drawRectangle( positions.get(i), 15.0f*((float)i/(float)trailCount), (float) angle, baseColor);
		}
	}
	
	@Override
	public synchronized void tick(float deltaT) {
		super.tick(deltaT);
		angle -= 360.0f  * deltaT;
	}

}
