package com.fren.plane.model;

import java.awt.Graphics;

public abstract class Missile implements BasicObject, Movable, Colidable{

	protected boolean isMe;
	protected int width;
	protected int height;
	protected int px;
	protected int py;
	protected int power;
	
	protected Fireable owner = null;
	
	@Override
	public void onMove(Orientation ori) {
		
	}

	@Override
	public void onDraw(Graphics g) {
		
	}
	
}
