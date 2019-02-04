package com.bluegent.entities;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.GameObject;
import com.bluegent.base.MyVector;
import com.bluegent.base.ObjectManager;
import com.bluegent.config.BulletCfg;
import com.bluegent.config.GameCfg;
import com.bluegent.config.ShipCfg;
import com.bluegent.graphics.RectangleTrail;
import com.bluegent.graphics.SpinningRectangle;
import com.bluegent.graphics.Trail;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.LogicHelper;
import com.bluegent.utils.RenderHelper;

public class PlayerShip extends GameObject implements DrawableShape{

	private SpinningRectangle graphic;
	private Trail trail;
	private MyVector velocity;
	private long cooldownMS;

	private double accuracyCone;
	private int dodgeMod;
	private boolean isInvulnerable;
	private long invulTimer;
	private long invulCD;
	
	private static final Color coneColor = new Color(1,1,1,0.5f);
	private static final double coneCutoff = 0;
	
	public PlayerShip(Vector2 pos, ObjectManager om) {
		super(pos,om);
		graphic = new SpinningRectangle(30, Color.WHITE, pos,parent);
		trail = new RectangleTrail(pos,15,LogicHelper.getTrailCount(20),Color.WHITE,parent);
		trail.setFade(true);
		velocity = new MyVector(0,0);
		cooldownMS = 0;
		accuracyCone = 0;
		dodgeMod = 1;
		
		isInvulnerable = false;
		invulTimer = 0;
		invulCD = 0;
	}

	private void clampVelocity(float deltaT)
	{
		double maxWithTime =  ShipCfg.maxSpeed * deltaT;
		if(velocity.getMagnitude() >= maxWithTime)
		{
			velocity.setMagnitude(maxWithTime);
		}
	}
	
	/**
	 * ONLY CALL ONCE
	 * @param deltaT
	 */
	private void handleTimings(float deltaT) 
	{
		cooldownMS-=deltaT;
		if(cooldownMS<0)
			cooldownMS = 0;
		
		invulCD -=deltaT;
		if(invulCD <0)
			invulCD =0;
		
		
		isInvulnerable = invulTimer>0;
		
		if(isInvulnerable)
			trail.setColor(Color.CYAN);
		else
			trail.setColor(Color.WHITE);
		
		invulTimer-=deltaT;
		if(invulTimer<0)
			invulTimer=0;
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
		
		handleTimings(deltaT);
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
		MyVector left =  new MyVector(700,(accuracyCone)*LogicHelper.radian+LogicHelper.halfPI);
		MyVector right =  new MyVector(700,(-accuracyCone)*LogicHelper.radian+LogicHelper.halfPI);
		
		coneColor.a = (float) ((accuracyCone-coneCutoff)/(BulletCfg.accuracyCone-coneCutoff));
		rh.drawForceLine(left, m_position, coneColor, 1);
		rh.drawForceLine(right, m_position, coneColor, 1);
	}
	
	@Override
	public synchronized void draw(RenderHelper rh) {		
		trail.draw(rh);
		drawFireCone(rh);	
		graphic.draw(rh);		
	}
	
	public void moveUp(float deltaT)
	{
		velocity.add(new MyVector(ShipCfg.moveSpeed*deltaT,LogicHelper.halfPI));
	}
	
	public void moveRight(float deltaT)
	{
		velocity.add(new MyVector(ShipCfg.moveSpeed*deltaT,0));
		dodgeMod = -1;
	}	
	public void moveDown(float deltaT)
	{
		velocity.add(new MyVector(-1*ShipCfg.moveSpeed*deltaT,LogicHelper.halfPI));
		
	}
	
	public void moveLeft(float deltaT)
	{
		velocity.add(new MyVector(ShipCfg.moveSpeed*deltaT,Math.PI));
		dodgeMod = 1;
	}
	
	
	public void shootRelease()
	{
	}
	
	public void shoot(float deltaT)
	{
		if(cooldownMS!=0)
			return;
		for(int i=0;i<BulletCfg.bulletsPerShot;++i)
			shootBullet(deltaT);
		cooldownMS = BulletCfg.shootCDMs;
	}
	
	public void shootBullet(float deltaT)
	{
		double angle = LogicHelper.getConeAngle(accuracyCone);
		PlayerBullet bullet = new PlayerBullet(m_position,parent,angle,2);
		
		velocity.add(new MyVector(ShipCfg.shotRecoil,Math.PI+angle));
		parent.addDrawable(bullet);
		parent.addObject(bullet);
		
		accuracyCone+=BulletCfg.accuracyLoss;
		if(accuracyCone > BulletCfg.accuracyCone)
			accuracyCone = BulletCfg.accuracyCone;
		
	}
	
	public void dodge()
	{
		if(invulCD!=0)
			return;
		invulCD = ShipCfg.dodgeCooldown;
		invulTimer = ShipCfg.dodgeInvulMS;
		velocity.add(new MyVector(dodgeMod*1.5,Math.PI));
	}
}
