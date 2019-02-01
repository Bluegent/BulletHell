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
	private int dodgeMod;
	
	private static final Color coneColor = new Color(1,1,1,0.5f);
	private static final double coneCutoff = 0;
	
	public PlayerShip(Vector2 pos, ObjectManager om) {
		super(pos,om);
		graphic = new SpinningRectangle(30, Color.WHITE, pos,parent);
		trail = new RectangleTrail(pos,15,LogicHelper.getTrailCount(20),Color.WHITE,parent);
		trail.setFade(true);
		velocity = new MyVector(0,0);
		cooldownMS = 0;
		isShooting = false;
		accuracyCone = 0;
		dodgeMod = 1;
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
		m_position.x += (float) velocity.getX() * deltaT;
		m_position.y += (float) velocity.getY() * deltaT;
		graphic.tick(deltaT);
		trail.tick(deltaT);
		double decel = velocity.getMagnitude()-velocity.getMagnitude()*0.009*deltaT;
		velocity.setMagnitude(decel);
		cooldownMS-=deltaT;
		if(cooldownMS<0)
			cooldownMS = 0;
		
		accuracyCone*=0.995;
		if(accuracyCone <=0.01)
			accuracyCone=0;
		
		
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
		if(accuracyCone <= coneCutoff)
			return;
		MyVector left =  new MyVector(70,(accuracyCone)*LogicHelper.radian+Math.PI/2);
		MyVector right =  new MyVector(70,(-accuracyCone)*LogicHelper.radian+Math.PI/2);
		
		coneColor.a = (float) ((accuracyCone-coneCutoff)/(BulletCfg.accuracyCone-coneCutoff));
		rh.drawForceLine(left, m_position, coneColor, 1);
		rh.drawForceLine(right, m_position, coneColor, 1);
	}
	
	@Override
	public synchronized void draw(RenderHelper rh) {		
		graphic.draw(rh);
		trail.draw(rh);
		drawFireCone(rh);
		
	}
	
	public void moveUp(float deltaT)
	{
		velocity.add(new MyVector(0.01*deltaT,Math.PI/2));
	}
	
	public void moveRight(float deltaT)
	{
		velocity.add(new MyVector(0.01*deltaT,0));
		dodgeMod = -1;
	}	
	public void moveDown(float deltaT)
	{
		velocity.add(new MyVector(-0.01*deltaT,Math.PI/2));
		
	}
	
	public void moveLeft(float deltaT)
	{
		velocity.add(new MyVector(0.01*deltaT,Math.PI));
		dodgeMod = 1;
	}
	
	public void debugMove(float deltaT)
	{
		velocity.add(new MyVector(dodgeMod*1,Math.PI));
	}
	
	public void shootRelease()
	{
		isShooting = false;
	}
	
	public void shoot(float deltaT)
	{
		if(cooldownMS!=0)
			return;
		for(int i=0;i<BulletCfg.bulletsPerShot;++i)
			shootBullet(deltaT);
		cooldownMS = BulletCfg.shootCDMs;
		isShooting = true;
	}
	
	public void shootBullet(float deltaT)
	{
		double angle = LogicHelper.getConeAngle(accuracyCone);
		PlayerBullet bullet = new PlayerBullet(m_position,parent,angle,2);
		
		velocity.add(new MyVector(0.02,Math.PI+angle));
		parent.addDrawable(bullet);
		parent.addObject(bullet);
		accuracyCone+=BulletCfg.accuracyLoss;
		if(accuracyCone > BulletCfg.accuracyCone)
			accuracyCone = BulletCfg.accuracyCone;
		
	}
	
	public void bomb()
	{
		debugMove(0);
	}
}
