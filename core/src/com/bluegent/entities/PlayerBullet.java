package com.bluegent.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.GameObject;
import com.bluegent.base.ObjectManager;
import com.bluegent.graphics.Trail;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.BulletCfg;
import com.bluegent.utils.LogicHelper;
import com.bluegent.utils.RenderHelper;

public class PlayerBullet extends GameObject implements DrawableShape{

	protected double angleInRad;
	protected double speed;
	protected double xComp, yComp;
	protected Trail trail;
	protected long lifeTime;
	
	
	
	
	public PlayerBullet(Vector2 pos, ObjectManager om, double angle, double speed) {
		super(new Vector2(pos), om);
		updateAngle(angle);
		this.speed = speed;
		trail = new Trail(m_position,5,LogicHelper.getTrailCount(7),Color.WHITE,om);
		trail.setFade(true);
		lifeTime = BulletCfg.bulletLifeTimeMS;
	}

	private void updateAngle(double angle) {
		angleInRad = angle;
		xComp = Math.cos(angle);
		yComp = Math.sin(angle);
		
	}
	@Override
	public synchronized void tick(float deltaT) {
		if(lifeTime<=0)
		{
			parent.removeDrawable(this);
			parent.removeObject(this);
			return;
		}
		this.m_position.x += speed * xComp * deltaT;
		this.m_position.y += speed * yComp * deltaT;
		
		
		for(int i=0;i<100;++i)
		{
			if(RenderHelper.isInViewPort(m_position, 5))
			{
				parent.getObjectsOfInterest(ObjectManager.ObjectType.EnemyPart);
			}
		}
		
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
