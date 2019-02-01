package com.bluegent.entities;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.GameObject;
import com.bluegent.base.MyVector;
import com.bluegent.base.ObjectManager;
import com.bluegent.graphics.RectangleTrail;
import com.bluegent.graphics.SpinningRectangle;
import com.bluegent.graphics.Trail;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.BulletCfg;
import com.bluegent.utils.GameCfg;
import com.bluegent.utils.LogicHelper;
import com.bluegent.utils.RenderHelper;

public class PlayerShip extends GameObject implements DrawableShape{

	private SpinningRectangle graphic;
	private Trail trail;
	private MyVector velocity;
	private static final double maxSpeed = 1300.0;
	private long cooldownMS;
	private boolean isShooting;
	private double accuracyCone;
	
	private MyVector[] moves;
	
	public PlayerShip(Vector2 pos, ObjectManager om) {
		super(pos,om);
		graphic = new SpinningRectangle(30, Color.WHITE, pos,parent);
		trail = new RectangleTrail(pos,15,LogicHelper.getTrailCount(20),Color.WHITE,parent);
		velocity = new MyVector(0,0);
		moves = new MyVector[4];
		moves[0] = new MyVector(100,Math.PI/2); //up
		moves[1] = new MyVector(100,0); //right
		moves[2] = new MyVector(-100,Math.PI/2); //down
		moves[3] = new MyVector(100,Math.PI); //left	
		cooldownMS = 0;
		isShooting = false;
		accuracyCone = 0;
	}

	private void clampVelocity(float deltaT)
	{
		double maxWithTime =  maxSpeed * deltaT;
		if(velocity.getMagnitude() >= maxWithTime)
		{
			velocity.setMagnitude(maxWithTime);
		}
	}
	@Override
	public synchronized void tick(float deltaT) {

		clampVelocity(deltaT);
		m_position.x += (float) velocity.getX();
		m_position.y += (float) velocity.getY();
		graphic.tick(deltaT);
		trail.tick(deltaT);
		double decel =velocity.getMagnitude()-velocity.getMagnitude()*9*deltaT;
		velocity.setMagnitude(decel);
		cooldownMS-=LogicHelper.getMSFromModifier(deltaT);
		if(cooldownMS<0)
			cooldownMS = 0;
		if(isShooting)
		{
			accuracyCone+=10*deltaT;
			if(accuracyCone > BulletCfg.accuracyCone)
				accuracyCone = BulletCfg.accuracyCone;
		}
		else
		{
			accuracyCone*=0.99;
			if(accuracyCone <=0.01)
				accuracyCone=0;
		}
		
	}

	@SuppressWarnings("unused")
	private void drawVelocity(RenderHelper rh)
	{
		Vector2 forceV =  new Vector2((float)velocity.getX()/GameCfg.TickMS*100.0f,(float)velocity.getY()/GameCfg.TickMS*100.0f);
		forceV.add(m_position);
		rh.drawLine(m_position, forceV , Color.RED, 2);
	}
	
	private void drawFireCone(RenderHelper rh)
	{
		if(accuracyCone < 3)
			return;
		MyVector left =  new MyVector(100,(accuracyCone)*LogicHelper.radian+Math.PI/2);
		MyVector right =  new MyVector(100,(-accuracyCone)*LogicHelper.radian+Math.PI/2);
		rh.drawForceLine(left, m_position, Color.WHITE, 1);
		rh.drawForceLine(right, m_position, Color.WHITE, 1);
	}
	
	@Override
	public synchronized void draw(RenderHelper rh) {		
		graphic.draw(rh);
		trail.draw(rh);
		drawFireCone(rh);
		
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
	
	public void debugMove(float deltaT)
	{
		velocity.add(new MyVector(10000000,Math.PI));
	}
	
	public void shootRelease()
	{
		isShooting = false;
	}
	
	public void shoot(float deltaT)
	{
		if(cooldownMS!=0)
			return;
		for(int i=0;i<3;++i)
			shootBullet(deltaT);
		cooldownMS = BulletCfg.shootCDMs;
		isShooting = true;
	}
	
	public void shootBullet(float deltaT)
	{
		
		PlayerBullet bullet = new PlayerBullet(m_position,parent,LogicHelper.getConeAngle(accuracyCone),2000);
		parent.addDrawable(bullet);
		parent.addObject(bullet);
		
	}
	
	public void bomb()
	{
		debugMove(0);
	}
}
