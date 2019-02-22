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
		public Queue<Color> colors;
		public double angle;
		
		public RectangleState()
		{
			positions = new Queue<Vector2>();
			colors = new Queue<Color>();
			angle = 0;
		}
		public void copyFrom(RectangleState state)
		{
			positions.clear();
			colors.clear();
			angle = state.angle;
			for(int i=0;i<state.positions.size;++i)
			{
				positions.addLast(new Vector2(state.positions.get(i).x,state.positions.get(i).y));
				colors.addLast(new Color(state.colors.get(i)));
			}
		}
	}
	
	RectangleState tick,drawState,copy;
	
	public RectangleTrail(Vector2 pos, int size, int count, Color color,ObjectManager om) {
		super(pos, size, count, color,om);
		
		tick = new RectangleState();
		drawState = new RectangleState();
		copy = new RectangleState();
		tick.angle = 0;
		
		for(int i=0;i<trailCount;++i )
		{
			tick.positions.addLast(new Vector2());
			tick.positions.get(i).x = pos.x;
			tick.positions.get(i).y = pos.y;	
			tick.colors.addLast(Color.BLACK);
		}
	}
	
	private boolean isSame()
	{
		for(int i=0;i<copy.positions.size-1;++i)
		{
			if(Math.abs(copy.positions.get(i).x - copy.positions.get(i+1).x)>=tolerance || Math.abs(copy.positions.get(i).y - copy.positions.get(i+1).y)>=tolerance)
				return false;
		}
		return true;
	}
	
	@Override
	public void draw(RenderHelper rh)
	{
		synchronized(drawState)
		{
			copy.copyFrom(drawState);
		}
		
		Color base = new Color(0x66ff66ff);
		Color gradientTo /* =Color.BLACK;*/=new Color(0x00AA66FF);
		//new Color(copy.colors.get(i));
		for(int i=0;i<copy.positions.size;++i)
		{
			Color use = new Color(1,1,1,1);
			switch(GraphicsCfg.rectangleTrails)
			{
			case Gradient:
			{
				
				float color  = ((float)i/(float)trailCount);
				use.r = (color*base.r + (1f-color)*gradientTo.r)/2f;
				use.g = (color*base.g + (1f-color)*gradientTo.g)/2f;
				use.b = (color*base.b + (1f-color)*gradientTo.b)/2f;
				//use.r = color;
				//use.g = color;
				//use.b = color;
				rh.drawStar( copy.positions.get(i), trailSize*((float)i/(float)trailCount),0.4f,5, (float) copy.angle, use);
				break;
			}
			case Alpha:
			{
				use.a  = ((float)i/(float)trailCount);
				rh.drawShit( copy.positions.get(i), trailSize*((float)i/(float)trailCount), (float) copy.angle, use);
				break;
			}

			case Simple:
			{
				rh.drawShit( copy.positions.get(i), trailSize*((float)i/(float)trailCount), (float) copy.angle, baseColor);
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
		tick.colors.removeFirst();
		tick.colors.addLast(new Color((float)Math.random(),(float)Math.random(),(float)Math.random(),1f));
		tick.angle -= 0.00360f  * deltaT;
		synchronized(drawState)
		{
			drawState.copyFrom(tick);
		}
	}

}
