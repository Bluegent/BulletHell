package com.bluegent.base;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
	protected Vector2 m_position;
	
	public GameObject(Vector2 pos)
	{
		m_position = pos;
	}
	public abstract  void tick(float deltaT);
}
