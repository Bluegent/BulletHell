package com.bluegent.base;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.bluegent.utils.GraphicsCfg;
import com.bluegent.utils.RateCalculator;

public abstract class BaseGame extends ApplicationAdapter implements InputProcessor{
	protected RateCalculator fps;
	private long lastRender;
	
	
	public BaseGame()
	{
		lastRender = System.currentTimeMillis();
		fps = new RateCalculator();
	}
	public abstract void drawSprites();
	public abstract void drawShapes();
	
	@Override
	public void render () {
		long now = System.currentTimeMillis();		
		fps.push(now-lastRender);
		lastRender = now;
		
		Gdx.gl.glClearColor(GraphicsCfg.BGColor.r, GraphicsCfg.BGColor.g, GraphicsCfg.BGColor.b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		
		drawSprites();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		drawShapes();	
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
