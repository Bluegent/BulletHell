package com.bluegent.hell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.BaseGame;
import com.bluegent.base.Controls;
import com.bluegent.base.ObjectManager;
import com.bluegent.config.GameCfg;
import com.bluegent.utils.LogicHelper;
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

		rh = new RenderHelper(shapeRenderer,batch);
		Gdx.input.setInputProcessor(this);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/arial.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 15;
		font =  generator.generateFont(parameter);
		generator.dispose();
		
		
	}
	


	@Override
	public void drawSprites() {	
		batch.begin();
		objMan.drawSprites(batch);
		rh.drawText(font, 5, GameCfg.Height - 17, "FPS:"+fps.avgToString()+"/"+GameCfg.FPS);
		rh.drawText(font, 5, GameCfg.Height - 37, "TPS:"+worker.getTickRate()+"/"+(1000/GameCfg.TickMS));
		rh.drawText(font, 5, GameCfg.Height - 57, "Obj:"+objMan.getObjectCount());
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
		font.dispose();
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
