package com.bluegent.graphics;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.ObjectManager;
import com.bluegent.config.GraphicsCfg;
import com.bluegent.entities.GameObject;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.RenderHelper;

public class Trail extends GameObject implements DrawableShape{

	//protected Queue<Vector2> positions;
	protected int trailSize;
	protected int trailCount;
	protected Color baseColor;
	protected boolean trailFade;
	protected State dState, tState;
	
	class State{
		public Queue<Vector2> positions;
		
		public State()
		{
			positions = new Queue<Vector2>();
		}
		public void copyFrom(State state)
		{
			positions.clear();
			for(Vector2 vec : state.positions)
				positions.addLast(new Vector2(vec.x,vec.y));
		}
	}

	public void setColor(Color color)
	{
		baseColor = color;
	}
	
	public void setFade(boolean fade)
	{
		trailFade = fade;
	}
	
	public Trail(Vector2 pos, int size,int count, Color color,ObjectManager om) {
		super(pos,om);
		trailSize = size;
		trailCount = count==0?1:count;
		baseColor = color;
		dState = new State();
		tState = new State();
		
		trailFade = false;
		for(int i=0;i<trailCount;++i )
		{
			tState.positions.addLast(new Vector2());
			tState.positions.get(i).x = pos.x;
			tState.positions.get(i).y = pos.y;	
		}
	}

	@Override
	public void draw(RenderHelper rh) {	
		
		synchronized(tState)
		{
			dState.copyFrom(tState);
		}
		Color use = new Color(baseColor);
		//rh.drawLine(dState.positions.first(), dState.positions.last(), use, trailSize);
		
		for(int i=0;i<dState.positions.size-1;++i)
		{
			if(!RenderHelper.isInViewPort(dState.positions.get(i), trailSize))
				continue;
			switch(GraphicsCfg.lineTrails)
			{
			case Gradient:
			{
				float color  = ((float)i/(float)trailCount);
				use.r = baseColor.r * color;
				use.g = baseColor.g * color;
				use.b = baseColor.b * color;
				rh.drawLine(dState.positions.get(i+1), dState.positions.get(i), use, trailSize*((float)i/(float)trailCount));
				break;
			}
			case Simple:
			{
				rh.drawLine(dState.positions.get(i+1), dState.positions.get(i), baseColor, trailSize*((float)i/(float)trailCount));
				break;
			}
			case Alpha: 
			{
				use.a = ((float)i/(float)trailCount);
				rh.drawLine(dState.positions.get(i+1), dState.positions.get(i), use, trailSize*((float)i/(float)trailCount));
				break;
			}
			default:
				break;
			}
		}
		
	}

	@Override
	public void tick(float deltaT) {
		tState.positions.removeFirst();
		tState.positions.addLast(new Vector2(m_position));	
	}

}
