package com.bluegent.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.ObjectManager;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.RenderHelper;

public class ShipWing extends ParentedObject implements DrawableShape{

	private Vector2 pos1,pos2,pos3;
	private int flipMult;
	private Color baseColor;
	
	public static final float triangleHeight = 35;
	public static final float triangleWidth = 15;
	public static final float bottomCornerOffset = -5;
	public static final float topCornerOffset = 3;
	
	public ShipWing(ObjectManager om, GameObject parent, Vector2 offset, Color color) {
		super(om, parent, offset);
		pos1 = new Vector2();
		pos2 = new Vector2();
		pos3 = new Vector2();
		flipMult = 1;
		baseColor = color;
	}

	public void setFlipped(boolean flipped)
	{
		flipMult = flipped?-1:1;
	}
	
	@Override
	public synchronized void draw(RenderHelper rh) {
		
		pos1.x = m_position.x;
		pos1.y = m_position.y - bottomCornerOffset;
		pos2.x = m_position.x - flipMult * topCornerOffset;
		pos2.y = m_position.y + triangleHeight;
		pos3.x = m_position.x + flipMult * triangleWidth;
		pos3.y = m_position.y + bottomCornerOffset;	
		
		rh.drawTriangle(pos1, pos2, pos3, baseColor, 1);
		
	}

}