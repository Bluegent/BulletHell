package com.bluegent.utils;

import com.badlogic.gdx.utils.Queue;

public class RateCalculator {

	private Queue<Long> samples;
	private double avgPerSecond;
	
	private static final int sampleRate = 100;
	
	public RateCalculator()
	{
		samples = new Queue<Long>();
		for(int i=0;i<sampleRate;++i)
			samples.addFirst(0L);
	}
	
	private void calc()
	{
		double sum=0;
		for(int i=0;i<sampleRate;++i)
			sum+=samples.get(i);
		avgPerSecond = 1000/sum*sampleRate;
	}
	public void push(long delta)
	{
		samples.removeFirst();
		samples.addLast(delta);
		calc();
		
	}
	public double getAverage()
	{
		return avgPerSecond;
	}
	
	public String avgToString()
	{
		return String.format("%.2f", avgPerSecond);
	}
}
