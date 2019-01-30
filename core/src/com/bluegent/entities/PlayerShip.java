package com.bluegent.entities;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.GameObject;
import com.bluegent.base.MyVector;
import com.bluegent.graphics.SpinningRectangle;
import com.bluegent.graphics.Trail;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.RenderHelper;

public class PlayerShip extends GameObject implements DrawableShape{

	private SpinningRectangle graphic;
	private Trail trail;
	private MyVector velocity;
	
	private MyVector[] moves;
	
	public PlayerShip(Vector2 pos) {
		super(pos);
		graphic = new SpinningRectangle(30, Color.WHITE, pos);
		trail = new Trail(pos,10,20,Color.WHITE);
		velocity = new MyVector(0,0);
		moves = new MyVector[4];
		moves[0] = new MyVector(100,Math.PI/2); //up
		moves[1] = new MyVector(100,0); //right
		moves[2] = new MyVector(-100,Math.PI/2); //down
		moves[3] = new MyVector(100,Math.PI); //left
	}

	@Override
	public void tick(float deltaT) {
		
		
		m_position.x += (float) velocity.getX();
		m_position.y += (float) velocity.getY();
		graphic.tick(deltaT);
		trail.tick(deltaT);
			
		velocity.setMagnitude(velocity.getMagnitude() * 0.92);
	}

	@Override
	public void draw(RenderHelper rh) {		
		graphic.draw(rh);
		trail.draw(rh);
	}
	
	
	public void moveUp(float deltaT)
	{
		velocity.add(new MyVector(100*deltaT,Math.PI/2));
	}
	
	public void moveRight(float deltaT)
	{
		velocity.add(new MyVector(100*deltaT,0));
	}	
	public void moveDown(float deltaT)
	{
		velocity.add(new MyVector(-100*deltaT,Math.PI/2));
	}
	
	public void moveLeft(float deltaT)
	{
		velocity.add(new MyVector(100*deltaT,Math.PI));
	}
}
