package com.bluegent.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.ObjectManager;
import com.bluegent.config.DebugCfg;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.LogicHelper;
import com.bluegent.utils.RenderHelper;

public class HitBox extends GameObject implements DrawableShape{

	public Vector2 lowerLeft,upperRight;
	public float width, height;
	public HitBox(Vector2 pos, ObjectManager om, float width, float height) {
		super(pos, om);
		lowerLeft = new Vector2();
		upperRight = new Vector2();
		this.width = width/2;
		this.height = height/2;
	}

	public boolean collidesWith(Vector2 point)
	{
		return LogicHelper.squareCollide(point, lowerLeft, upperRight);
	}
	
	public boolean collidesWith(HitBox box)
	{
		
		return false;
	}
	@Override
	public synchronized void draw(RenderHelper rh) {
		if(DebugCfg.drawHitBoxes)
			rh.drawRectangle(lowerLeft, upperRight, Color.RED, 1);
		
	}

	@Override
	public synchronized void tick(float deltaT) {
		lowerLeft.x = m_position.x - width;
		lowerLeft.y = m_position.y - height;
		upperRight.x = m_position.x + width;
		upperRight.y = m_position.y + height;
		
	}

}
