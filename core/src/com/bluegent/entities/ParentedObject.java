package com.bluegent.entities;

import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.ObjectManager;

public class ParentedObject extends GameObject{
	
	private GameObject parent;
	private Vector2 offset;
	
	public ParentedObject(ObjectManager om, GameObject parent,Vector2 offset) {
		super(new Vector2(), om);
		this.parent = parent;
		this.offset = offset;
	}
	
	public void updatePosition()
	{
		m_position.x = parent.m_position.x + offset.x;
		m_position.y = parent.m_position.y + offset.y;
	}
	
	@Override
	public synchronized void tick(float deltaT) {
		updatePosition();
		
	}

}
