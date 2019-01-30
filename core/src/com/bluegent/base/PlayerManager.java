package com.bluegent.base;

import com.bluegent.entities.PlayerShip;

public class PlayerManager {
	private PlayerShip playerShip;
	PlayerManager(PlayerShip ship)
	{
		playerShip = ship;
	}
	public void bomb()
	{
		playerShip.bomb();
	}
}
