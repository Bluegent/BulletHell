package com.bluegent.hell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bluegent.base.BaseGame;
import com.bluegent.base.Controls;
import com.bluegent.base.ObjectManager;
import com.bluegent.utils.GameCfg;
import com.bluegent.utils.RenderHelper;

public class MainGame extends BaseGame {
	SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private RenderHelper rh;
	private ObjectManager objMan;
	private PhysicsWorker worker;
	private Thread physicsThread;
	private BitmapFont font;
	
	@Override
	public void create () {
		
		Controls.initKeys();
		batch = new SpriteBatch();
		
		shapeRenderer = new ShapeRenderer();
		
		objMan = new ObjectManager();
		
		worker = new PhysicsWorker(objMan);
		
		physicsThread = new Thread(worker);
		physicsThread.start();

		rh = new RenderHelper(shapeRenderer);
		Gdx.input.setInputProcessor(this);
		
		font =  new BitmapFont(Gdx.files.internal("data/default.fnt"),Gdx.files.internal("data/default.png"),false);
	}
	


	@Override
	public void drawSprites() {	
		batch.begin();
		objMan.drawSprites(batch);
		String controls = "U:"+Controls.isKeyPressed(Controls.Key.MoveUp) +"\nD:"+Controls.isKeyPressed(Controls.Key.MoveDown)+"\nL:"+Controls.isKeyPressed(Controls.Key.MoveLeft)+"\nR:"+Controls.isKeyPressed(Controls.Key.MoveRight);
		font.draw(batch, controls,0,GameCfg.Height);
		batch.end();
	}

	@Override
	public void drawShapes() {
		objMan.drawShapes(rh);
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
