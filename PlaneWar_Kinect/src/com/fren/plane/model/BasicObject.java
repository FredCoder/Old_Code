package com.fren.plane.model;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface BasicObject {

	public void onDraw(Graphics g);

	public Rectangle getRect();
}
