package com.bluegent.hell;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class SpinningRectangle implements GameObject{
	private int m_size;
	private int m_radius;
	private Color m_color;
	private float m_angle;
	
	public SpinningRectangle(int size, Color color)
	{
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
	
	public void draw(Vector2 pos, RenderHelper rh)
	{
		rh.drawRectangle(pos, m_radius, m_angle, m_color);;
	}
}
