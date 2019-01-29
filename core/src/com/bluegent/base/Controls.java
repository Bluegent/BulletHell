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
		Shoot
	}
	public static EnumMap<Key,Integer> keys;
	public static EnumMap<Key,Boolean> keyDown;
	
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
	
	public static boolean setKey(int keyCode, boolean value)
	{		
		boolean retVal = false;
		retVal |= testAndSetKey(keyCode,Key.MoveRight,value);
		retVal |= testAndSetKey(keyCode,Key.MoveUp,value);
		retVal |= testAndSetKey(keyCode,Key.MoveDown,value);
		retVal |= testAndSetKey(keyCode,Key.MoveLeft,value);
		return retVal;
	}
}
