package com.bluegent.base;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.config.GameCfg;
import com.bluegent.entities.GameObject;
import com.bluegent.entities.HitBox;
import com.bluegent.entities.PlayerShip;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.RenderHelper;

public class ObjectManager {

	public enum ObjectType
	{
		PlayerBullet,
		Ship,
		EnemyBullet,
		EnemyPart
	}
	
	private ArrayList<GameObject> objects;
	private ArrayList<DrawableShape> drawableS;
	private ArrayList<DrawableShape> bullets;
	private ArrayList<GameObject> enemies;
	@SuppressWarnings("unused")
	private KeyActionManager keyManager;
	private PlayerShip ship;
	
	public int getObjectCount()
	{
		return objects.size();
	}
	
	public ObjectManager()
	{
		objects = new ArrayList<GameObject>();
		enemies = new ArrayList<GameObject>();
		ship = new PlayerShip(new Vector2(GameCfg.Width/2, GameCfg.Height/2),this);
		drawableS = new ArrayList<DrawableShape>();
		bullets = new ArrayList<DrawableShape>();
		PlayerManager manager = new PlayerManager(ship);
		keyManager = new KeyActionManager(manager);
		Controls.setKeyManager(keyManager);
		drawableS.add(ship);
		objects.add(ship);
		HitBox testBox = new HitBox(new Vector2(GameCfg.Width/2,GameCfg.Height-100),this,50,50);
		enemies.add(testBox);
		objects.add(testBox);
		drawableS.add(testBox);
	}
	
	public void pollControls(float deltaT)
	{
		
		if(Controls.isKeyPressed(Controls.Key.Shoot))
		{
			ship.shoot(deltaT);
		}
		else
		{
			ship.shootRelease();
		}
		
		if(Controls.isKeyPressed(Controls.Key.MoveUp))
			ship.moveUp(deltaT);
		if(Controls.isKeyPressed(Controls.Key.MoveDown))
			ship.moveDown(deltaT);
		if(Controls.isKeyPressed(Controls.Key.MoveLeft))
			ship.moveLeft(deltaT);
		if(Controls.isKeyPressed(Controls.Key.MoveRight))
			ship.moveRight(deltaT);
	}
	
	public ArrayList<GameObject> getObjectsOfInterest(ObjectType type)
	{
		switch(type)
		{
			case EnemyPart:
			{
				return enemies;
			}
		default:
			break;
		}
		return null;
	}
	
	public synchronized void drawSprites(SpriteBatch batch)
	{
	}
	
	public synchronized void drawShapes(RenderHelper rh)
	{
		for(DrawableShape bullet : bullets)
		{
			bullet.draw(rh);	
		}
		for(DrawableShape drawMe : drawableS)
		{
			drawMe.draw(rh);	
		}
	}
	public synchronized void update(float deltaT)
	{
		pollControls(deltaT);
		for(int i=0;i<objects.size();++i)
		{
			objects.get(i).tick(deltaT);
		}	
		
		
	}
	
	public synchronized void removeDrawable(DrawableShape shape)
	{
		if(drawableS.contains(shape))
			drawableS.remove(shape);
	}
	public synchronized void removeObject(GameObject obj)
	{
		if(objects.contains(obj))
			objects.remove(obj);
	}
	
	public synchronized void removeBullet(DrawableShape shape)
	{
		if(bullets.contains(shape))
			bullets.remove(shape);
	}
	
	public synchronized void addBullet(DrawableShape shape)
	{
		bullets.add(shape);
	}
	
	public synchronized void addDrawable(DrawableShape shape)
	{
		drawableS.add(shape);
	}
	
	public synchronized void addObject(GameObject obj)
	{
		objects.add(obj);
	}
	
}
