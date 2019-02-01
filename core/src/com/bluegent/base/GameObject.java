package com.bluegent.base;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject{
	protected Vector2 m_position;
	protected ObjectManager parent;
	public GameObject(Vector2 pos, ObjectManager om)
	{
		m_position = pos;
		parent = om;
	}
	public abstract  void tick(float deltaT);
	public Vector2 getPosition()
	{
		return m_position;
	}
}
