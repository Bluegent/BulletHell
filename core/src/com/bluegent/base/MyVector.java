package com.bluegent.base;

import java.util.ArrayList;

public class MyVector {
	private double x,y;
	private double angleInRad;
	private double magnitude;
	

	private void calc()
	{
		x = (float) (magnitude * Math.cos(angleInRad));
		y = (float) (magnitude * Math.sin(angleInRad));
	}
	
	public MyVector(double mag, double angle)
	{
		magnitude = mag;
		angleInRad = angle;
		calc();
		
	}
	public MyVector(MyVector other) {
		angleInRad = other.angleInRad;
		magnitude = other.magnitude;
		calc();
	}
	public void setMagnitude(double mag)
	{
		magnitude = mag;
		calc();
	}
	public void setAngle(double angle)
	{
		angleInRad = angle;
		calc();
	}
	public double getAngle()
	{
		return angleInRad;
	}
	public double getMagnitude()
	{
		return magnitude;
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	
	public static MyVector composeForces(ArrayList<MyVector> forces)
	{
		MyVector result = new MyVector(0,0);
		double hSum = 0.0, vSum = 0.0;
		for(MyVector f: forces)
		{
			hSum+= f.x;
			vSum+= f.y;
		}
		if(hSum==0.0)
			hSum=0.000000001;
		
		result.setAngle(Math.atan2(vSum,hSum));

		result.setMagnitude(Math.sqrt(Math.pow(hSum, 2)+Math.pow(vSum, 2)));

		
		return result;
	}
	
	
	public void add(MyVector other)
	{
		magnitude = Math.sqrt(Math.pow(x+other.x, 2)+Math.pow(y+other.y, 2));
		angleInRad = Math.atan2(y+other.y,x+other.x);
	}
}
