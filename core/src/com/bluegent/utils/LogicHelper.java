package com.bluegent.utils;

public class LogicHelper {

	public static final double radian = Math.PI/180.0f;
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
		return (int)((float)perSecond*10.0f/GameCfg.TickMS);
	}
	
	public static double getConeAngle()
	{
		return (BulletCfg.accuracyCone-Math.random()*BulletCfg.accuracyCone*2) * radian + Math.PI/2;
	}
}
