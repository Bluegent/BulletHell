package com.bluegent.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.ObjectManager;
import com.bluegent.config.GraphicsCfg;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.RenderHelper;

public class Explosion extends GameObject implements DrawableShape
{

	public static final float maxSize= 15;
	public static final float sizeInc = 0.07f;
	private float size = 0;
	private Color color;
	public Explosion(Vector2 pos, ObjectManager om) {
		super(pos, om);
		color = new Color(Color.WHITE);
	}

	@Override
	public synchronized void tick(float deltaT) {
		size+=deltaT*sizeInc;
		if(size > maxSize)
		{
			parent.removeDrawable(this);
			parent.removeObject(this);
		}
		
	}



	@Override
	public synchronized void draw(RenderHelper rh) {
		
		float colorN = 1-size/maxSize;
		switch(GraphicsCfg.expliosionQuality)
		{
		case Alpha:
			
			color.a = colorN;
			rh.drawCircle(m_position,size,color);
			break;
		case Gradient:
			color.r = colorN;
			color.g = colorN;
			color.b = colorN;
			rh.drawCircle(m_position,size,color);
			break;
		case Off:
			break;
		default:
			break;
		
		}	
		
	}
}
