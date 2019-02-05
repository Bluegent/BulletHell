package com.bluegent.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.ObjectManager;
import com.bluegent.config.GraphicsCfg;
import com.bluegent.entities.GameObject;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.RenderHelper;

public class SpinningRectangle extends GameObject implements DrawableShape{
	private int m_size;
	private int m_radius;
	private Color m_color;
	private float m_angle;
	
	public SpinningRectangle(int size, Color color, Vector2 pos,ObjectManager om)
	{
		super(pos,om);
		m_size = size;
		m_radius = m_size/2;
		m_color = color;
		m_angle = 0;
	}
	
	public synchronized void updateAngle(float deltaT)
	{
		m_angle -= 0.360f  * deltaT;
	}
	
	@Override
	public void tick(float deltaT)
	{
		updateAngle(deltaT);
	}
	
	public synchronized void draw(RenderHelper rh)
	{
		rh.drawFillRectangleB(m_position, m_radius, m_angle,GraphicsCfg.BGColor, m_color);
	}
}
