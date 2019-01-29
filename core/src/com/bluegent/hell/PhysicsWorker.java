package com.bluegent.hell;

import com.bluegent.base.ObjectManager;
import com.bluegent.utils.GameCfg;
import com.bluegent.utils.LogicHelper;

public class PhysicsWorker implements Runnable{
	
	private ObjectManager objectManager;
	private boolean work;
	private long lastTick;
	
	public PhysicsWorker(ObjectManager om)
	{
		objectManager = om;
		work = true;
		lastTick = System.currentTimeMillis();
	}
	
	public void stop()
	{ 
		work = false;	
	}
	
	
	@Override
	public void run() {
		long now;
		float time;
		try {
			while(work)
			{
				now = System.currentTimeMillis();
				time = now-lastTick;
				lastTick = now;
				
				objectManager.update(LogicHelper.getTimeModifier(time));
					
				Thread.sleep(GameCfg.TickMS);
			}
		}
		catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

}
