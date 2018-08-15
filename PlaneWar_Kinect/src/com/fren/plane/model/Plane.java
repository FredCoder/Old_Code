package com.fren.plane.model;

import java.awt.Graphics;
import java.awt.Point;

public abstract class Plane implements BasicObject, Movable{
	
	protected boolean isMe;
	protected int px,py;
	protected int height,width;
	protected int blood;
	protected int power;//decide omit the power of missile
	
	public abstract void onDraw(Graphics g);
	public abstract void onMove(Orientation ori);
	public abstract void onMove(Point point);
	public abstract void onHit(int power);
}
