package com.bluegent.hell;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.BaseGame;
import com.bluegent.base.Controls;
import com.bluegent.base.GameObject;
import com.bluegent.entities.PlayerShip;
import com.bluegent.interfaces.DrawableShape;
import com.bluegent.utils.GameCfg;
import com.bluegent.utils.RenderHelper;

public class MainGame extends BaseGame {
	SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private RenderHelper rh;
	private ArrayList<GameObject> objects;
	private ArrayList<DrawableShape> drawableS;
	private PlayerShip ship;
	private PhysicsWorker worker;
	private Thread physicsThread;
	
	@Override
	public void create () {
		
		Controls.initKeys();
		batch = new SpriteBatch();
		ship = new PlayerShip(new Vector2(GameCfg.Width/2, GameCfg.Height/2));
		shapeRenderer = new ShapeRenderer();
		
		objects = new ArrayList<GameObject>();
		drawableS = new ArrayList<DrawableShape>();
		worker = new PhysicsWorker(objects);
		
		physicsThread = new Thread(worker);
		physicsThread.start();
		objects.add(ship);
		drawableS.add(ship);
		rh = new RenderHelper(shapeRenderer);
		Gdx.input.setInputProcessor(this);
	}
	


	@Override
	public void drawSprites() {	
	}

	@Override
	public void drawShapes() {
		for(DrawableShape drawMe : drawableS)
			drawMe.draw(rh);	
	}
	
	@Override
	public void dispose () {
		worker.stop();
		try {
			physicsThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		batch.dispose();
		shapeRenderer.dispose();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return Controls.setKey(keycode,true);	
	}

	@Override
	public boolean keyUp(int keycode) {
		return Controls.setKey(keycode,false);	
	}

}
