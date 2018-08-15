package com.fren.plane.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.fren.plane.main.Controller;

public class Explosion implements BasicObject{

	private int radiu[] = {4,8,15,25,38,54,50,40,27,15,10};
	private int max = 11;
	private int cur = 0;
	private int px;
	private int py;
	Controller controller = null;
	
	public Explosion(int px, int py, Controller controller) {
		cur = 0;
		this.px = px;
		this.py = py;
		
		this.controller = controller;
	}
	
	@Override
	public void onDraw(Graphics g) {
		Color oldColor = g.getColor();
		
		g.setColor(Color.ORANGE);
		g.fillOval(px - radiu[cur]/2, py - radiu[cur]/2, radiu[cur], radiu[cur]);
		cur++;
		if(cur == max){
			controller.explosions.remove(this);
		}
		
		g.setColor(oldColor);
	}

	@Override
	public Rectangle getRect() {
		return null;
	}

}
