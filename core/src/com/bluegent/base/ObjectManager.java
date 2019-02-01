package com.bluegent.base;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.entities.PlayerShip;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.GameCfg;
import com.bluegent.utils.RenderHelper;

public class ObjectManager {

	private ArrayList<GameObject> objects;
	private ArrayList<DrawableShape> drawableS;
	@SuppressWarnings("unused")
	private KeyActionManager keyManager;
	private PlayerShip ship;
	
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
		if(Controls.isKeyPressed(Controls.Key.MoveUp))
			ship.moveUp(deltaT);
		if(Controls.isKeyPressed(Controls.Key.MoveDown))
			ship.moveDown(deltaT);
		if(Controls.isKeyPressed(Controls.Key.MoveLeft))
			ship.moveLeft(deltaT);
		if(Controls.isKeyPressed(Controls.Key.MoveRight))
			ship.moveRight(deltaT);
	}
	
	public synchronized void drawSprites(SpriteBatch batch)
	{
	}
	
	public synchronized void drawShapes(RenderHelper rh)
	{
		for(DrawableShape drawMe : drawableS)
			drawMe.draw(rh);	
	}
	public synchronized void update(float deltaT)
	{
		pollControls(deltaT);
		for(int i=0;i<objects.size();++i)
		{
			objects.get(i).tick(deltaT);
		}	
		
		
	}
}
