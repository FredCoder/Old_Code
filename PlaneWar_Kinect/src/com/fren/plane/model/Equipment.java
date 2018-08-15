package com.fren.plane.model;

public abstract class Equipment implements BasicObject, Movable, Colidable{

	public static final int TYPE_BOMB = 0;
	public static final int TYPE_POWER = 1;
	
	protected int type;
	protected int px;
	protected int py;
	protected int width;
	protected int height;
	
	public abstract int getType();
}
