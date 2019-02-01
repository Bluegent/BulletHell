package com.bluegent.base;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.entities.PlayerShip;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.GameCfg;
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
		ship = new PlayerShip(new Vector2(GameCfg.Width/2, GameCfg.Height/2),this);
		drawableS = new ArrayList<DrawableShape>();
		PlayerManager manager = new PlayerManager(ship);
		keyManager = new KeyActionManager(manager);
		Controls.setKeyManager(keyManager);
		drawableS.add(ship);
		objects.add(ship);
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
				return objects;
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
	
	
	public synchronized void addDrawable(DrawableShape shape)
	{
		drawableS.add(shape);
	}
	
	public synchronized void addObject(GameObject obj)
	{
		objects.add(obj);
	}
	
}
