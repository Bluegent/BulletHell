package com.bluegent.utils;

import com.badlogic.gdx.math.Vector2;
import com.bluegent.config.GameCfg;

public class LogicHelper {

	public static final double radian = Math.PI/180.0f;
	public static final double halfPI = Math.PI/2;
	public static float getTimeModifier(float deltaT)
	{
		return deltaT/1000.0f;
	}
	
	public static long getMSFromModifier(float deltaT)
	{
		return (long)(deltaT*1000);
	}
	
	public static int getTrailCount(int perSecond)
	{
		return (int)(Math.ceil((float)perSecond*10.0f/GameCfg.TickMS))+1;
	}
	
	public static double getConeAngle(double cone)
	{
		return (cone-Math.random()*cone*2) * radian + Math.PI/2;
	}
	
	public static boolean circleCollide(Vector2 point, Vector2 circle, float radius)
	{
		double distance = Math.sqrt(Math.pow(point.x+circle.x, 2)+Math.pow(point.y+circle.y, 2));
		return distance<=radius;
	}
	
	public static boolean between(float value, float lower, float upper)
	{
		return value>=lower && value <=upper;
	}
	
	public static boolean squareCollide(Vector2 point, Vector2 ll, Vector2 ur)
	{
		if(between(point.x,ll.x,ur.x) && between(point.y,ll.y,ur.y))
			return true;
		return false;
	}
	
	
	public static void testColls()
	{
		long last = System.currentTimeMillis();
		long cCol=0, sCol=0;
		for(int i=0;i<10000;++i)
		{
			for(int j=0;j<1000;++j)
			{
			boolean col =LogicHelper.circleCollide(new Vector2((float)Math.random()*GameCfg.Height,(float)Math.random()*GameCfg.Height), new Vector2((float)Math.random()*GameCfg.Height,(float)Math.random()*GameCfg.Height),(float)Math.random()*GameCfg.Height);
			if(col)
				++cCol;
			}
		}
		long nowMs = System.currentTimeMillis();
		System.out.println("Circle coll: "+(nowMs-last)+" ms");
		last=System.currentTimeMillis();
		for(int i=0;i<10000;++i)
		{
			for(int j=0;j<1000;++j)
			{
			boolean col = LogicHelper.squareCollide(new Vector2((float)Math.random()*GameCfg.Height,(float)Math.random()*GameCfg.Height), new Vector2((float)Math.random()*GameCfg.Height,(float)Math.random()*GameCfg.Height),new Vector2((float)Math.random()*GameCfg.Height,(float)Math.random()*GameCfg.Height));
			if(col)
				sCol++;
			}
		}
		nowMs = System.currentTimeMillis();
		System.out.println("Square coll: "+(nowMs-last)+" ms");
		
		System.out.println("ccol : "+cCol+" scol: "+sCol);
	}
}
