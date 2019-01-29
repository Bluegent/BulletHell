package com.bluegent.hell;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.GameObject;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.LogicHelper;
import com.bluegent.utils.RenderHelper;

public class SpinningRectangle extends GameObject implements DrawableShape{
	private int m_size;
	private int m_radius;
	private Color m_color;
	private float m_angle;
	
	public SpinningRectangle(int size, Color color, Vector2 pos)
	{
		super(pos);
		m_size = size;
		m_radius = m_size/2;
		m_color = color;
		m_angle = 0;
	}
	
	public synchronized void updateAngle(float deltaT)
	{
		m_angle += 360.0f * LogicHelper.getTimeModifier(deltaT);
	}
	
	@Override
	public void tick(float deltaT)
	{
		updateAngle(deltaT);
	}
	
	public void draw(RenderHelper rh)
	{
		rh.drawRectangle(m_position, m_radius, m_angle, m_color);;
	}
}
