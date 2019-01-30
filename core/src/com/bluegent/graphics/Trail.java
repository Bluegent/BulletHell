package com.bluegent.graphics;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.GameObject;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.RenderHelper;

public class Trail extends GameObject implements DrawableShape{

	private Queue<Vector2> positions;
	private int trailSize;
	private int trailCount;
	private Color baseColor;
	
	public Trail(Vector2 pos, int size,int count, Color color) {
		super(pos);
		trailSize = size;
		trailCount = count;
		baseColor = color;
		positions = new Queue<Vector2>();
		for(int i=0;i<trailCount;++i )
		{
			positions.addLast(new Vector2());
			positions.get(i).x = pos.x;
			positions.get(i).y = pos.y;	
		}
	}

	@Override
	public synchronized void draw(RenderHelper rh) {
		
		for(int i=0;i<positions.size-1;++i)
		{
			rh.drawLine(positions.get(i+1), positions.get(i), new Color(1,1,1,0.2f), trailSize*((float)i/(float)trailCount));
		}
		
	}

	@Override
	public synchronized void tick(float deltaT) {
		positions.removeFirst();
		positions.addLast(new Vector2(m_position));	
	}

}