package com.fren.plane.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import com.fren.plane.main.Controller;
import com.fren.plane.main.MainFrame;

public class MyPlane extends Plane implements Fireable,Colidable{

	public static final int PLANE_WIDTH = 80;
	public static final int PLANE_HEIGHT = 80;
	public static final int PLANE_MOVEMENT = 10;
	
	private ArrayList<Missile> missiles = new ArrayList<Missile>();
	private int readyFire = 0;
	
	Controller controller = null;
	Image face = null;
	
	public MyPlane(Controller controller) {
		this.isMe = true;
		this.height = PLANE_HEIGHT;
		this.width = PLANE_WIDTH;
		this.px = MainFrame.FRAME_WIDIH/2;
		this.py = MainFrame.FRAME_HEIGHT - PLANE_HEIGHT;
		
		this.blood = 1;
		this.power = 1;
		
		this.controller = controller; 
		face = Toolkit.getDefaultToolkit().createImage("res/myPlane_t.png");
		
	}
	
	@Override
	public void onMove(Point point){
		py = point.y;
		px = point.x;
		onColide();
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
		onColide();
	}
	

	@Override
	public void onFire(int power) {
		if((++readyFire)%10==0){
			readyFire = 0;
			for(int i=0;i<power;i++){
				int offset = (i-power/2)*(MyMissile.MISSILE_WIDTH);
				missiles.add(new MyMissile(this.px + offset, this.py - PLANE_HEIGHT/2, this, controller));
			}
		}
	}


	@Override
	public void onDraw(Graphics g) {
		//before draw., move and fire
		//move is user task
		onFire(power);
		
		//draw plane
		Color oldColor = g.getColor();
		
		g.setColor(Color.GREEN);
		//g.fillRect(px - width/2, py - height/2, width, height);
		g.drawImage(face, px - width/2, py - height/2, null);
		
		g.setColor(oldColor);
		//draw missile
		for(int i=0;i<missiles.size();i++)
			missiles.get(i).onDraw(g);
	}
	
	
	@Override
	public void onHit(int power) {
		this.blood -= power;
		if(this.blood <= 0){
			//controller.myPlane = null;
			//controller.explosions.add(new Explosion(px, py, controller));
			controller.explosions.add(new Explosion(px, py, controller));	
			controller.setGameState(Controller.GAMESTATE_STOP);
		}
		System.out.println("Blood:"+blood);
	}


	public void addMissile(Missile m){
		missiles.add(m);
	}
	public void removeMissile(Missile m){
		missiles.remove(m);
	}
	
	@Override
	public Rectangle getRect() {
		return new Rectangle(px-width/2, py-height/2,width, height);
	}
	
	public void changePower(int power){
		this.power = power;
	}

	/**
	 * colid check
	 */
	@Override
	public void onColide() {
		for(int i=0;i< controller.others.size();i++){
			if(this.getRect().intersects(controller.others.get(i).getRect())){
				this.onHit(controller.others.get(i).power);
				controller.others.get(i).onHit(this.power);
			}
		}
	}
	
	public int getPower(){
		return power;
	}
	
}
