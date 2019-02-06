package com.bluegent.base;

import com.badlogic.gdx.math.Vector2;

public interface Collidable {
	public boolean collidesWith(Vector2 point);
	public boolean collidesWith(Collidable col);
}
