package com.bluegent.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.MyVector;

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
	
	public void drawDebugCrosshair(Vector2 pos, Color color)
	{
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(color);
		shapeRenderer.line(0, pos.y, GameCfg.Width,pos.y);
		shapeRenderer.line(pos.x, 0, pos.x, GameCfg.Height);
		shapeRenderer.end();
	}
	
	public void drawDebugCrosshair(float x, float y, Color color)
	{
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(color);
		shapeRenderer.line(0,y, GameCfg.Width,y);
		shapeRenderer.line(x, 0, x, GameCfg.Height);
		shapeRenderer.end();
	}
	
	
	public void drawRectangle(Vector2 pos, float radius, float angle, Color color)
	{
		float size = radius * 2;
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(pos.x-radius, pos.y -radius, radius,  radius, size, size , 1.0f, 1.0f, angle);
		shapeRenderer.end();
	}
	
	public void drawFillRectangle(Vector2 pos, float radius, float angle, Color color)
	{
		float size = radius * 2;
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(pos.x-radius, pos.y -radius, radius,  radius, size, size , 1.0f, 1.0f, angle);
		shapeRenderer.end();
	}
	
	public void drawForceLine(MyVector force, Vector2 origin, Color color, float width)
	{
		drawLine(origin,new Vector2(origin.x+(float)force.getX(),origin.y+(float)force.getY()),color,width);
	}
}
