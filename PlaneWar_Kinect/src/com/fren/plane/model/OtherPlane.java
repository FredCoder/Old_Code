package com.fren.plane.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import com.fren.plane.main.Controller;

public class OtherPlane extends Plane implements Movable{

	public static final int PLANE_WIDTH = 70;
	public static final int PLANE_HEIGHT = 44;
	public static final int PLANE_MOVEMENT = 4;
	
	Image face = null;
	Controller controller = null;
	
	public OtherPlane(int px, int py, Controller controller) {
		this.isMe = false;
		this.height = PLANE_HEIGHT;
		this.width = PLANE_WIDTH;

		this.px = px;
		this.py = py;
		this.blood = 1;
		this.power = 1;
		
		this.controller = controller;
		
		face = Toolkit.getDefaultToolkit().createImage("res/otherPlane_t.png");
	}
	@Override
	public void onDraw(Graphics g) {
		//before draw, move first
		onMove(Orientation.D);
		
		//draw
		Color oldColor = g.getColor();
		
		g.setColor(Color.RED);
		//g.fillRect(px - width/2, py - height/2, width, height);
		g.drawImage(face, px - width/2, py - height/2, null);
		g.setColor(oldColor);
	}
	
	@Override
	public void onMove(Point point){
		py = point.y;
		px = point.x;
	}

	@Override
	public void onMove(Orientation ori) {
		switch(ori){
		case U:
			py -= PLANE_MOVEMENT;
			break;
		case R:
			px += PLANE_MOVEMENT;
			break;
		case D:
			py += PLANE_MOVEMENT;
			break;
		case L:
			px -= PLANE_MOVEMENT;
			break;
		}
	}
	
	@Override
	public Rectangle getRect() {
		return new Rectangle( px-width/2, py-height/2,width, height);
	}
	
	@Override
	public void onHit(int power) {
		this.blood -= power;
		if(this.blood <= 0){
			controller.others.remove(this);
			controller.explosions.add(new Explosion(px, py, controller));				
			controller.incScore(100);
		}
	}

	

}
