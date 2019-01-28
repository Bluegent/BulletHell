package com.bluegent.hell;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class RenderHelper {
	private ShapeRenderer shapeRenderer;
	
	public RenderHelper(ShapeRenderer sr)
	{
		shapeRenderer = sr;
	}
	public void filledCircle(Vector2 position, float radius,Color fill) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(fill);
	    shapeRenderer.circle(position.x, position.y, radius);
	    shapeRenderer.end();
	}

	public void lineCircle(Vector2 position, float radius,Color border,float lineWidth) {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(border);
	    shapeRenderer.circle(position.x, position.y, radius);
	    shapeRenderer.end();
	}
	void circle(Vector2 position, float radius,Color border,float lineWidth) {
		filledCircle(position,radius+lineWidth/2,border);
		filledCircle(position,radius-lineWidth/2,GameCfg.BGColor);
	}
	public void circleWithBorder(Vector2 position, float radius,Color border, Color fill,float lineWidth)
	{
		/*
		filledCircle(position,radius+lineWidth/2,border);
		filledCircle(position,radius-lineWidth/2,fill);*/
		lineCircle(position,radius,border,2);
	}
	
	public void drawLine(Vector2 start, Vector2 end, Color color, float width)
	{
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
	    shapeRenderer.rectLine(start, end, width); 
	    shapeRenderer.end();
	}
	
}
