package com.bluegent.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.bluegent.base.ObjectManager;
import com.bluegent.config.GraphicsCfg;
import com.bluegent.utils.RenderHelper;

public class RectangleTrail extends Trail{

	
	private static final double tolerance = 3;
	
	class RectangleState extends State{
		public Queue<Vector2> positions;
		public double angle;
		
		public RectangleState()
		{
			positions = new Queue<Vector2>();
			angle = 0;
		}
		public void copyFrom(RectangleState state)
		{
			positions.clear();
			angle = state.angle;
			for(Vector2 vec : state.positions)
				positions.addLast(new Vector2(vec.x,vec.y));
		}
	}
	
	RectangleState tick,draw,copy;
	
	public RectangleTrail(Vector2 pos, int size, int count, Color color,ObjectManager om) {
		super(pos, size, count, color,om);
		
		tick = new RectangleState();
		draw = new RectangleState();
		copy = new RectangleState();
		tick.angle = 0;
		
		for(int i=0;i<trailCount;++i )
		{
			tick.positions.addLast(new Vector2());
			tick.positions.get(i).x = pos.x;
			tick.positions.get(i).y = pos.y;	
		}
	}
	
	private boolean isSame()
	{
		for(int i=0;i<cState.positions.size-1;++i)
		{
			if(Math.abs(cState.positions.get(i).x - cState.positions.get(i+1).x)>=tolerance || Math.abs(cState.positions.get(i).y - cState.positions.get(i+1).y)>=tolerance)
				return false;
		}
		return true;
	}
	
	@Override
	public void draw(RenderHelper rh)
	{
		synchronized(draw)
		{
			copy.copyFrom(draw);
		}
		if(isSame())
			return;
		Color use = new Color(baseColor);
		for(int i=0;i<copy.positions.size;++i)
		{
			switch(GraphicsCfg.rectangleTrails)
			{
			case Gradient:
			{
				float color  = ((float)i/(float)trailCount);
				use.r = baseColor.r * color;
				use.b = baseColor.b * color;
				use.g = baseColor.g * color;
				rh.drawRectangle( copy.positions.get(i), trailSize*((float)i/(float)trailCount), (float) copy.angle, use);
				break;
			}
			case Alpha:
			{
				use.a  = ((float)i/(float)trailCount);
				rh.drawRectangle( copy.positions.get(i), trailSize*((float)i/(float)trailCount), (float) copy.angle, use);
				break;
			}

			case Simple:
			{
				rh.drawRectangle( copy.positions.get(i), trailSize*((float)i/(float)trailCount), (float) copy.angle, baseColor);
				break;
			}
			default:
				break;
			}
		}
		
	}
	
	@Override
	public  void tick(float deltaT) {
		tick.positions.removeFirst();
		tick.positions.addLast(new Vector2(m_position));	
		tick.angle -= 0.360f  * deltaT;
		synchronized(draw)
		{
			draw.copyFrom(tick);
		}
	}

}
