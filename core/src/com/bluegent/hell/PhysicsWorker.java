package com.bluegent.hell;

import com.bluegent.base.ObjectManager;
import com.bluegent.config.GameCfg;
import com.bluegent.utils.RateCalculator;

public class PhysicsWorker implements Runnable{
	
	private ObjectManager objectManager;
	private boolean work;
	private long lastTick;
	private RateCalculator tickRate;
	
	
	public synchronized String getTickRate()
	{
		return tickRate.avgToString();
	}
	
	
	private synchronized void pushRate(long rate)
	{
		tickRate.push(rate);
	}
	
	public PhysicsWorker(ObjectManager om)
	{
		objectManager = om;
		work = true;
		lastTick = System.currentTimeMillis();
		tickRate = new RateCalculator();
	}
	
	public void stop()
	{ 
		work = false;	
	}
	
	
	@Override
	public void run() {
		long now;
		long difference = 0;
		float time;
		try {
			
			while(work)
			{		
				now = System.currentTimeMillis();
				time = now-lastTick;
				pushRate((long)time);
				lastTick = now;
				
				if(GameCfg.Running)
				{
					objectManager.update(time);
					
				}
				long after = System.currentTimeMillis();
				difference = now-after;
				Thread.sleep(GameCfg.TickMS-difference);
			}
		}
		catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

}
