package com.bluegent.utils;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.bluegent.base.MyVector;
import com.bluegent.config.GameCfg;
import com.bluegent.config.GraphicsCfg;

public class RenderHelper {
	private ShapeRenderer shapeRenderer;
	
	private SpriteBatch batch;
	
	public RenderHelper(ShapeRenderer sr, SpriteBatch sb)
	{
		shapeRenderer = sr;
		batch = sb;
	}
	
	
	public void drawText(BitmapFont font, float x, float y, String str)
	{
		font.draw(batch, str, x, y);
	}
	
	public static boolean isInViewPort(Vector2 position, float bounding)
	{
		if(position.x - bounding > GameCfg.Width*1.5)
			return false;
		if(position.x + bounding < GameCfg.Width * -0.5)
			return false;
		if(position.y - bounding > GameCfg.Height*1.5)
			return false;
		if(position.y + bounding < GameCfg.Height * -0.5)
			return false;
		return true;
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
		filledCircle(position,radius-lineWidth/2,GraphicsCfg.BGColor);
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
	
	
	public void drawShit(Vector2 pos, float radius, float angle, Color color)
	{
		float size = radius * 2;
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(color);
		//shapeRenderer.rect(pos.x-radius, pos.y -radius, radius,  radius, size, size , 1.0f, 1.0f, angle);
		shapeRenderer.circle(pos.x, pos.y, size);
		
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
	
	public void drawFillRectangleB(Vector2 pos, float radius, float angle, Color bg, Color line)
	{
		drawFillRectangle(pos,radius,angle,bg);
		drawRectangle(pos,radius,angle,line);
	}
	
	
	public void drawForceLine(MyVector force, Vector2 origin, Color color, float width)
	{
		drawLine(origin,new Vector2(origin.x+(float)force.getX(),origin.y+(float)force.getY()),color,width);
	}
	
	
	public void drawFilledTriangle(Vector2 pos1, Vector2 pos2, Vector2 pos3, Color color,int thickness)
	{
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
		shapeRenderer.triangle(pos1.x,pos1.y,pos2.x,pos2.y,pos3.x,pos3.y);
		shapeRenderer.end();
	}
	
	public void drawTriangle(Vector2 pos1, Vector2 pos2, Vector2 pos3, Color color,int thickness)
	{
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(color);
		shapeRenderer.triangle(pos1.x,pos1.y,pos2.x,pos2.y,pos3.x,pos3.y);
		shapeRenderer.end();
	}
	
	public void drawTriangleB(Vector2 pos1, Vector2 pos2, Vector2 pos3,Color bgColor, Color line,int thickness)
	{
		drawFilledTriangle(pos1,pos2,pos3,bgColor,thickness);
		drawTriangle(pos1,pos2,pos3,line,thickness);
	}
	
	public void drawRectangle(Vector2 lowerLeft, Vector2 upperRight, Color color, int lineThickness)
	{
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(lowerLeft.x,lowerLeft.y,0,0,upperRight.x-lowerLeft.x,upperRight.y-lowerLeft.y,1,1,0);
		shapeRenderer.end();
	}
	
	public void drawCircle(Vector2 origin, float radius, Color color)
	{
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(color);
		shapeRenderer.circle(origin.x, origin.y, radius,(int)(5*Math.random()+5));
		shapeRenderer.end();
	}
	
	
	public void drawStar(Vector2 origin, float radius, float dipRadius, int points,float angle, Color color)
	{
		shapeRenderer.begin(ShapeType.Line);
		float angleIncrements = (float)(Math.PI*2/points);
		float[] vertices = new float[points*4];
		for(int i=0;i<points;++i)
		{
			float currentAngle = angle*(float)LogicHelper.radian+angleIncrements*i;
			float currentHalf = angle*(float)LogicHelper.radian+angleIncrements*i+angleIncrements/2;
			vertices[i*4] = origin.x+radius*(float)Math.cos(currentAngle);
			vertices[i*4+1] = origin.y+radius*(float)Math.sin(currentAngle);
			vertices[i*4+2] =  origin.x+radius*dipRadius*(float)Math.cos(currentHalf);
			vertices[i*4+3] = origin.y+ radius*dipRadius*(float)Math.sin(currentHalf);
			
		}
		shapeRenderer.setColor(color);
		shapeRenderer.polygon(vertices);
		shapeRenderer.circle(origin.x,origin.y,radius*0.9f);
		
		shapeRenderer.end();
	}
	
	
	
	public void drawGear(Vector2 origin, float radius, float dipRadius, int points,float angle, Color color)
	{
		shapeRenderer.begin(ShapeType.Line);
		float angleIncrements = (float)(Math.PI*2/points);
		shapeRenderer.setColor(color);
		
		for(int i=0;i<points;++i)
		{
		
			float currentAngle = angle*(float)LogicHelper.radian+angleIncrements*i-angleIncrements/4;
			float currentHalf = angle*(float)LogicHelper.radian+angleIncrements*i+angleIncrements/4;
			float startX, startY, endX,endY;
			startX = origin.x+radius*(float)Math.cos(currentAngle);
			startY = origin.y+radius*(float)Math.sin(currentAngle);
			endX = origin.x+radius*(float)Math.cos(currentHalf-angleIncrements/6);
			endY = origin.y+radius*(float)Math.sin(currentHalf-angleIncrements/6);
			
			shapeRenderer.line(startX, startY, endX, endY);
			
			currentAngle+= angleIncrements/2;
			currentHalf+= angleIncrements/2;
			
			float astartX, astartY, aendX,aendY;
			astartX = origin.x+radius*dipRadius*(float)Math.cos(currentAngle);
			astartY = origin.y+radius*dipRadius*(float)Math.sin(currentAngle);
			aendX = origin.x+radius*dipRadius*(float)Math.cos(currentHalf-angleIncrements/6);
			aendY = origin.y+radius*dipRadius*(float)Math.sin(currentHalf-angleIncrements/6);
			
			shapeRenderer.line(astartX, astartY, aendX, aendY);
			
			shapeRenderer.line(endX, endY, astartX, astartY);
			currentAngle+= angleIncrements/2;
			startX = origin.x+radius*(float)Math.cos(currentAngle);
			startY = origin.y+radius*(float)Math.sin(currentAngle);
			shapeRenderer.line(aendX, aendY, startX, startY);
			
		}
		
		shapeRenderer.circle(origin.x,origin.y,radius*dipRadius*0.3f);
		
		shapeRenderer.end();
	}
}
