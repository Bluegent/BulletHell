package com.bluegent.base;

import java.util.EnumMap;

import com.badlogic.gdx.Input;

public class Controls {
	public static enum Key
	{
		MoveUp,
		MoveDown,
		MoveLeft,
		MoveRight,
		Shoot,
		Bomb,
		Pause
	}
	public static EnumMap<Key,Integer> keys;
	public static EnumMap<Key,Boolean> keyDown;
	private static KeyActionManager manager;
	
	public static void setKeyManager(KeyActionManager man)
	{
		manager =  man;
	}
	
	public static void setKey(Key key, int keycode)
	{
		keys.put(key, keycode);
		keyDown.put(key, false);
	}
	
	public static void initKeys()
	{
		keyDown = new  EnumMap<Key,Boolean>(Key.class);
		keys = new  EnumMap<Key,Integer>(Key.class);
		
		setKey(Key.MoveUp, Input.Keys.W);
		setKey(Key.MoveLeft, Input.Keys.A);
		setKey(Key.MoveDown, Input.Keys.S);
		setKey(Key.MoveRight, Input.Keys.D);
		setKey(Key.Pause, Input.Keys.ESCAPE);
		setKey(Key.Shoot, Input.Keys.J);
		setKey(Key.Bomb, Input.Keys.K);
		
	}
	public static synchronized boolean isKeyPressed(Key key)
	{
		if(keyDown.containsKey(key))
			return keyDown.get(key);
		else
			return false;
	}
	public static synchronized boolean testAndSetKey(int keyCode, Key key,boolean value)
	{
		if(keyCode == keys.get(key))
		{
			keyDown.put(key,value);
			return true;
		}
		return false;
	}
	
	
	public static boolean setKey(int keyCode, boolean keyDown)
	{		
		boolean retVal = false;
		
		//keyDowns
		retVal |= testAndSetKey(keyCode,Key.MoveRight,keyDown);
		retVal |= testAndSetKey(keyCode,Key.MoveUp,keyDown);
		retVal |= testAndSetKey(keyCode,Key.MoveDown,keyDown);
		retVal |= testAndSetKey(keyCode,Key.MoveLeft,keyDown);
		retVal |= testAndSetKey(keyCode,Key.Shoot,keyDown);
		
		
		
		//normal keys
		if(keyCode == keys.get(Key.Pause) && !keyDown) {
			if(manager !=null)
				manager.togglePause();
			retVal = true;
		}
		
		if(keyCode == keys.get(Key.Bomb) && !keyDown) {
			if(manager !=null)
				manager.doBomb();
			retVal = true;
		}
		
		
		return retVal;
	}
}
