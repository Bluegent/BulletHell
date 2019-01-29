package com.bluegent.hell;

import java.util.ArrayList;

import com.bluegent.base.GameObject;
import com.bluegent.utils.GameCfg;

public class PhysicsWorker implements Runnable{
	
	private ArrayList<GameObject> objects;
	private boolean work;
	private long lastTick;
	public PhysicsWorker(ArrayList<GameObject> obj)
	{
		objects = obj;
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
				
				for(int i=0;i<objects.size();++i)
				{
					objects.get(i).tick(time);
				}	
				
				Thread.sleep((long) GameCfg.TickMS);
			}
		}
		catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

}
