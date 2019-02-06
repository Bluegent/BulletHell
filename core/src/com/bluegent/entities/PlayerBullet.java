package com.bluegent.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.Collidable;
import com.bluegent.base.ObjectManager;
import com.bluegent.config.BulletCfg;
import com.bluegent.graphics.Trail;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.LogicHelper;
import com.bluegent.utils.RenderHelper;

public class PlayerBullet extends GameObject implements DrawableShape{

	protected double angleInRad;
	protected double speed;
	protected double xComp, yComp;
	protected Trail trail;
	protected long lifeTime;
	private int counter;
	private int direction;
	private static final int counterChange = 1;
	
	public PlayerBullet(Vector2 pos, ObjectManager om, double angle, double speed) {
		super(new Vector2(pos), om);
		updateAngle(angle);
		this.speed = speed;
		trail = new Trail(m_position,5,LogicHelper.getTrailCount(5),Color.WHITE,om);
		trail.setFade(true);
		lifeTime = BulletCfg.bulletLifeTimeMS;
	
		
		counter = counterChange/2;
		direction = 1;
	}

	private void updateAngle(double angle) {
		angleInRad = angle;
		xComp = Math.cos(angle);
		yComp = Math.sin(angle);
	}
	
	public void updateMotion(float deltaT)
	{
		
		/*
		counter += deltaT;
		if(counter>=counterChange)
		{
			counter = 0;
			direction=-1*direction;
		}
		updateAngle(angleInRad+direction*LogicHelper.radian*counter*deltaT*0.1);
		*/
		
	}
	
	public void doCollision(float deltaT)
	{
		Vector2 subStep = new Vector2(m_position.x,m_position.y);
		float travelledDistance = 0;
		ArrayList<GameObject> enemies = parent.getObjectsOfInterest(ObjectManager.ObjectType.EnemyPart);
		
		float xDistComp = (float) (BulletCfg.subStepDistance * xComp);
		float yDistComp = (float) (BulletCfg.subStepDistance * yComp);
		while(travelledDistance < speed*deltaT)
		{	
			subStep.x += xDistComp;
			subStep.y += yDistComp;
			for(GameObject obj : enemies)
			{
				Collidable col = (Collidable)obj;
				boolean temp = col.collidesWith(subStep);
				if(col != null && temp)
				{
					lifeTime = 0;
					this.m_position.x = subStep.x;
					this.m_position.y = subStep.y;
					return;
				}
			}
			travelledDistance+=BulletCfg.subStepDistance;
		}

		this.m_position.x += speed * deltaT * xComp;
		this.m_position.y += speed * deltaT * yComp;
	}
	
	@Override
	public synchronized void tick(float deltaT) {
		if(lifeTime<=0)
		{
			parent.removeBullet(this);
			parent.removeObject(this);
			Explosion exp = new Explosion(new Vector2(m_position.x,m_position.y),parent);
			parent.addObject(exp);
			parent.addDrawable(exp);
			return;
		}
		updateMotion(deltaT);
		
		doCollision(deltaT);

		trail.tick(deltaT);
		lifeTime -= deltaT;
		
	}

	@Override
	public synchronized void draw(RenderHelper rh) {	
		
		if(!RenderHelper.isInViewPort(m_position,5))
			return;
		rh.drawFillRectangle(m_position, BulletCfg.bulletSize, (float) angleInRad, Color.WHITE);
		trail.draw(rh);
	}

}
