package com.bluegent.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.utils.RenderHelper;

public class RectangleTrail extends Trail{

	private double angle;
	private static final double tolerance = 3;
	public RectangleTrail(Vector2 pos, int size, int count, Color color) {
		super(pos, size, count, color);
		angle = 0;
	}
	
	private boolean isSame()
	{
		for(int i=0;i<positions.size-1;++i)
		{
			if(Math.abs(positions.get(i).x - positions.get(i+1).x)>=tolerance || Math.abs(positions.get(i).y - positions.get(i+1).y)>=tolerance)
				return false;
		}
		return true;
	}
	@Override
	public synchronized void draw(RenderHelper rh)
	{
		if(isSame())
			return;
		for(int i=0;i<positions.size;++i)
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
