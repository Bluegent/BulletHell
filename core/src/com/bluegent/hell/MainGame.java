package com.bluegent.hell;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.GameObject;
import com.bluegent.utils.GameCfg;
import com.bluegent.utils.RenderHelper;

public class MainGame extends BaseGame {
	SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private RenderHelper rh;
	private ArrayList<GameObject> objects;
	private SpinningRectangle rect;
	private PhysicsWorker worker;
	private Thread physicsThread;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		rect = new SpinningRectangle(30,Color.WHITE, new Vector2(GameCfg.Width/2, GameCfg.Height/2));
		shapeRenderer = new ShapeRenderer();
		
		objects = new ArrayList<GameObject>();
		worker = new PhysicsWorker(objects);
		
		physicsThread = new Thread(worker);
		physicsThread.start();
		objects.add(rect);
		rh = new RenderHelper(shapeRenderer);
		Gdx.input.setInputProcessor(this);
	}
	


	@Override
	public void drawSprites() {	
	}

	@Override
	public void drawShapes() {
		rect.draw(rh);	
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

}
