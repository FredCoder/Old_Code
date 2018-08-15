package com.fren.plane.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import com.fren.plane.main.Controller;
import com.fren.plane.main.MainFrame;

public class MyMissile extends Missile{

	public static int MISSILE_WIDTH = 11;
	public static int MISSILE_HEIGHT = 30;
	public static int MISSILE_MOVEMENT = 15;
	
	Controller controller = null;
	Image face = null;
	
	public MyMissile(int px, int py, Fireable owner, Controller controller){
		this.isMe = true;
		this.height = MISSILE_HEIGHT;
		this.width = MISSILE_WIDTH;
		this.px = px;
		this.py = py;
		
		this.power = 1;
		
		this.owner = owner;
		this.controller = controller;
		face = Toolkit.getDefaultToolkit().createImage("res/missile_t.png");
	}
	
	@Override
	public void onMove(Orientation ori) {
		
		switch(ori){
		case U:
			py -= MISSILE_MOVEMENT;
			break;
		case R:
			px += MISSILE_MOVEMENT;
			break;
		case D:
			py += MISSILE_MOVEMENT;
			break;
		case L:
			px -= MISSILE_MOVEMENT;
			break;
		}
		
		if(px<0 || px>=MainFrame.FRAME_WIDIH || py<0 || py>=MainFrame.FRAME_HEIGHT)
			owner.removeMissile(this);
	}
	
	@Override
	public void onColide() {
		for(int i=0;i<controller.others.size();i++){
			if(this.getRect().intersects(controller.others.get(i).getRect())){
				//colide
				controller.others.get(i).onHit(this.power);
				owner.removeMissile(this);
				
			}
		}
	}

	@Override
	public void onDraw(Graphics g) {
		//before draw, move and then colide-check
		onMove(Orientation.U);
		onColide();
		
		//
		Color oldColor = g.getColor();
		
		g.setColor(Color.GREEN);
		//g.fillRect(px - width/2, py - height/2, width, height);
		g.drawImage(face, px - width/2, py - height/2, null);
		
		g.setColor(oldColor);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(px-width/2, py-height/2, width, height);
	}
	
	
}
