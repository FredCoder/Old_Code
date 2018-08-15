package com.fren.plane.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import com.fren.plane.main.Controller;
import com.fren.plane.main.MainFrame;

public class PowerEquipment extends Equipment{

	public static final int EQUIP_WIDTH = 41;
	public static final int EQUIP_HEIGHT = 55;
	public static final int EQUIP_MOVEMENT = 20;
	
	private boolean isLive;
	private int timer;    // these two is for time of last
	private int flag = 1; // using for draw
	private int lastPower;
	
	private Controller controller = null;
	
	private Image face = null;
	
	public PowerEquipment(int px, int py, Controller controller) {
		this.type = Equipment.TYPE_POWER;
		this.px = px;
		this.py = py;
		this.width = EQUIP_WIDTH;
		this.height = EQUIP_HEIGHT;
		this.isLive = true;
		this.timer = 300;
		
		this.controller = controller;
		
		face = Toolkit.getDefaultToolkit().createImage("res/equipment2_t.png");
	}
	
	@Override
	public void onDraw(Graphics g) {
		//before : onMove()  
		if(isLive){
			if(1 == flag && py<=MainFrame.FRAME_HEIGHT/3){
				onMove(Orientation.D);
			}else if(1 == flag && py>MainFrame.FRAME_HEIGHT/3){
				onMove(Orientation.U);
				flag = 2;
			}else if(2 == flag && py>EQUIP_HEIGHT){
				onMove(Orientation.U);
			}else if(2 == flag && py<=EQUIP_HEIGHT){
				onMove(Orientation.D);
				flag = 3;
			}else if(3 == flag){
				onMove(Orientation.D);
			}
			
			g.drawImage(face, px - width/2, py - height/2, null);
		}
		else{
			if(--timer<0){
				System.out.println("little...");
				controller.equipments.remove(this);
				controller.myPlane.changePower(lastPower);
			}
				
		}
			
		
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle( px-width/2, py-height/2,width, height);
	}

	@Override
	public void onColide() {
		if(getRect().intersects(controller.myPlane.getRect())){
			lastPower = controller.myPlane.getPower();
			controller.myPlane.changePower(2);
			isLive = false;
		}
	}

	@Override
	public void onMove(Orientation ori) {
		//after move:onColide
		switch(ori){
		case U:
			py -= EQUIP_MOVEMENT;
			break;
		case R:
			px += EQUIP_MOVEMENT;
			break;
		case D:
			py += EQUIP_MOVEMENT;
			break;
		case L:
			px -= EQUIP_MOVEMENT;
			break;
		}
		if(px<0 || px>=MainFrame.FRAME_WIDIH || py<0 || py>=MainFrame.FRAME_HEIGHT)
			controller.equipments.remove(this);
		onColide();
	}

	@Override
	public int getType() {
		return this.type;
	}
}
