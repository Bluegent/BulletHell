package com.bluegent.hell;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private ArrayList<GameObject> objects;
	private SpinningRectangle rect;
	private PhysicsWorker worker;
	private Thread physicsThread;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		rect = new SpinningRectangle(30,Color.WHITE);
		shapeRenderer = new ShapeRenderer();
		
		objects = new ArrayList<GameObject>();
		worker = new PhysicsWorker(objects);
		
		physicsThread = new Thread(worker);
		physicsThread.start();
		objects.add(rect);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		batch.end();
		rect.draw(new Vector2(GameCfg.Width/2,GameCfg.Height/2), shapeRenderer);
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.line(new Vector2(GameCfg.Width/2,0), new Vector2(GameCfg.Width/2,GameCfg.Height));

	
		shapeRenderer.line(new Vector2(0,GameCfg.Height/2), new Vector2(GameCfg.Width,GameCfg.Height/2));
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
