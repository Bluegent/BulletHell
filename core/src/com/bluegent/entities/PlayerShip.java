package com.bluegent.entities;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.GameObject;
import com.bluegent.base.MyVector;
import com.bluegent.graphics.SpinningRectangle;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.RenderHelper;

public class PlayerShip extends GameObject implements DrawableShape{

	private SpinningRectangle graphic;
	private MyVector velocity;
	private long last;
	
	public PlayerShip(Vector2 pos) {
		super(pos);
		graphic = new SpinningRectangle(30, Color.WHITE, pos);
		velocity = new MyVector(0,0);
		last = System.currentTimeMillis();
	}

	@Override
	public void tick(float deltaT) {
		graphic.tick(deltaT);
		
	}

	@Override
	public void draw(RenderHelper rh) {		
		graphic.draw(rh);
	}
	
	
	public void moveUp()
	{
	}
	
	public void moveDown()
	{
		
	}
	
	public void moveLeft()
	{
		
	}
	
	public void moveRight()
	{
		
	}
}
