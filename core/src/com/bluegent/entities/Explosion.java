package com.bluegent.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.ObjectManager;
import com.bluegent.config.GraphicsCfg;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.RenderHelper;

public class Explosion extends GameObject implements DrawableShape
{

	public static final float maxSize= 40;
	public static final float sizeInc = 0.1f;
	private Color color;
	
	class ExplosionState
	{
		float size;
		public void copyFrom(ExplosionState other) {
			size=other.size;
		}
		
	}
	ExplosionState real,copy,doubleCopy;
	public Explosion(Vector2 pos, ObjectManager om) {
		super(pos, om);
		color = new Color(Color.WHITE);
		
		real = new ExplosionState();
		copy = new ExplosionState();
		doubleCopy = new ExplosionState();
		real.size= 0;
	}

	@Override
	public  void tick(float deltaT) {
		real.size+=deltaT*sizeInc;		
		synchronized(copy)
		{
			copy.copyFrom(real);
		}
		
		
		if(real.size > maxSize)
		{
			parent.removeDrawable(this);
			parent.removeObject(this);
		}
		
	}



	@Override
	public void draw(RenderHelper rh) {
		synchronized(copy)
		{
			doubleCopy.copyFrom(copy);
		}
		
		
		float colorN = 1-doubleCopy.size/maxSize;
		switch(GraphicsCfg.expliosionQuality)
		{
		case Alpha:
			
			color.a = colorN;
			rh.drawCircle(m_position,doubleCopy.size,color);
			break;
		case Gradient:
			color.r = colorN;
			color.g = colorN;
			color.b = colorN;
			rh.drawCircle(m_position,doubleCopy.size,color);
			break;
		case Off:
			break;
		default:
			break;
		
		}	
		
	}
}
