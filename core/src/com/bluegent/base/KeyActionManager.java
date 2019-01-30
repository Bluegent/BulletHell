package com.bluegent.base;

import com.bluegent.utils.GameCfg;

public class KeyActionManager {

	PlayerManager playerManager;
	public KeyActionManager(PlayerManager man)
	{
		playerManager = man;
	}
	public void togglePause()
	{
		GameCfg.Running = !GameCfg.Running;
	}
	public void doBomb() 
	{
		playerManager.bomb();
	}
}
