package com.bluegent.utils;

import com.badlogic.gdx.utils.Queue;

public class RateCalculator {

	private Queue<Long> samples;
	private double avgPerSecond;
	private static int counter;
	
	private static final int sampleRate = 100;
	private static final int updateTime = 50;
	
	public RateCalculator()
	{
		samples = new Queue<Long>();
		for(int i=0;i<sampleRate;++i)
			samples.addFirst(0L);
		counter = 0;
	}
	
	private void calc()
	{
		double sum=0;
		for(int i=0;i<sampleRate;++i)
			sum+=samples.get(i);
		avgPerSecond = 1000/sum*sampleRate;
		counter = 0;
	}
	public void push(long delta)
	{
		samples.removeFirst();
		samples.addLast(delta);
		++counter;
		if(counter==updateTime)
			calc();
		
	}
	public double getAverage()
	{
		return avgPerSecond;
	}
	
	public String avgToString()
	{
		return String.format("%.0f", avgPerSecond);
	}
}
