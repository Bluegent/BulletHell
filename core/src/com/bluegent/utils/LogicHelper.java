package com.bluegent.utils;

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
}
