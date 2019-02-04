package com.bluegent.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.ObjectManager;
import com.bluegent.config.GraphicsCfg;
import com.bluegent.utils.RenderHelper;

public class RectangleTrail extends Trail{

	private double angle;
	private static final double tolerance = 3;
	
	
	
	public RectangleTrail(Vector2 pos, int size, int count, Color color,ObjectManager om) {
		super(pos, size, count, color,om);
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
		Color use = new Color(baseColor);
		for(int i=0;i<positions.size;++i)
		{
			switch(GraphicsCfg.rectangleTrails)
			{
			case Gradient:
			{
				float color  = ((float)i/(float)trailCount);
				use.r = baseColor.r * ((float)i/(float)trailCount);
				use.b = baseColor.g * ((float)i/(float)trailCount);;
				use.g = baseColor.b * ((float)i/(float)trailCount);;
				rh.drawRectangle( positions.get(i), trailSize*((float)i/(float)trailCount), (float) angle, use);
				break;
			}
			case Alpha:
			{
				use.a  = ((float)i/(float)trailCount);
				rh.drawRectangle( positions.get(i), trailSize*((float)i/(float)trailCount), (float) angle, use);
				break;
			}

			case Simple:
			{
				rh.drawRectangle( positions.get(i), trailSize*((float)i/(float)trailCount), (float) angle, baseColor);
				break;
			}
			default:
				break;
			}
		}
		
	}
	
	@Override
	public synchronized void tick(float deltaT) {
		super.tick(deltaT);
		angle -= 0.360f  * deltaT;
	}

}
