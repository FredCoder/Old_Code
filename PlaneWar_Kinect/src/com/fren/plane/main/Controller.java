package com.fren.plane.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import org.OpenNI.Point3D;

import com.fren.plane.model.BombEquipment;
import com.fren.plane.model.Equipment;
import com.fren.plane.model.Explosion;
import com.fren.plane.model.MyPlane;
import com.fren.plane.model.Orientation;
import com.fren.plane.model.Plane;
import com.fren.plane.model.PowerEquipment;
import com.fren.util.OtherPlaneFactory;

public class Controller {
	
	public static final int GAMESTATE_START = 0;
	public static final int GAMESTATE_PAUSE = 1;
	public static final int GAMESTATE_STOP = 2;

	public MyPlane myPlane = null;
	public ArrayList<Plane> others = new ArrayList<Plane>();
	public ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	public ArrayList<Equipment> equipments = new ArrayList<Equipment>();
	private int readyOther;
	private int readyPowerful ;
	private int score ;
	
	private Image pauseFace = null;
	private Image bombNumImg = null;
	
	private int gameState;
	
	public Controller() {
		
		pauseFace = Toolkit.getDefaultToolkit().createImage("res/pause_t.png");
		bombNumImg = Toolkit.getDefaultToolkit().createImage("res/equipment11_t.png");
		
		init();
	}
	
	public void init(){
		gameState = GAMESTATE_PAUSE;
		score = 0;
		readyOther = 0;
		readyPowerful = 0;
		
		others.clear();
		explosions.clear();
		equipments.clear();
		
		myPlane = new MyPlane(this);
		OtherPlaneFactory.getPlanes(3, others, this);
		
	}
	
	public void incScore(int inc){
		this.score += inc;
	}
	
	public void onKinectListener(Point3D point){
		myPlane.onMove(new Point(((int)(point.getX())) + 150 ,((int)(point.getY()))+150));
	}
	
	
	public void onMouseListener(MouseEvent e){
		myPlane.onMove(new Point(e.getX() , e.getY()));
	}

	public void onKeyListener(KeyEvent e) {
		Orientation ori = null;
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			ori = Orientation.U;
			break;
		case KeyEvent.VK_LEFT:
			ori = Orientation.L;
			break;
		case KeyEvent.VK_DOWN:
			ori = Orientation.D;
			break;
		case KeyEvent.VK_RIGHT:
			ori = Orientation.R;
			break;
		case KeyEvent.VK_SPACE:
			for(int i=0;i<equipments.size();i++){
				Equipment eq = equipments.get(i);
				if(eq.getType() == Equipment.TYPE_BOMB){
					((BombEquipment)eq).onBomb();
					break;
				}
			}
			break;
		case KeyEvent.VK_P:
			gameState = GAMESTATE_PAUSE;
			break;
		case KeyEvent.VK_S:
			gameState = GAMESTATE_START;
			break;
		case KeyEvent.VK_R:
			init();
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(1);
			break;
		default:
			ori = Orientation.O;
			break;
		}
		if(ori != null)
			myPlane.onMove(ori);
	}

	public void onDraw(Graphics g) {
		//schedular produce plane-other
		if((++readyOther)%10==0){
			readyOther = 0;
			Random rand = new Random();
			OtherPlaneFactory.getPlanes(rand.nextInt(2), others, this);
		}
		//schedular produce equipment
		if((++readyPowerful)%600==0){
			Random rand = new Random();
			if(rand.nextBoolean())
				equipments.add(new PowerEquipment(rand.nextInt(MainFrame.FRAME_WIDIH), 0, this));
			else
				equipments.add(new BombEquipment(rand.nextInt(MainFrame.FRAME_WIDIH), 0, this));
		}
		
		
		Color oldColor = g.getColor();
		
		myPlane.onDraw(g);
		for(int i=0;i<others.size();i++)
			others.get(i).onDraw(g);
		for(int i=0;i<explosions.size();i++)
			explosions.get(i).onDraw(g);
		
		int bombNum = 0;
		for(int i=0;i<equipments.size();i++){
			Equipment e = equipments.get(i); 
			e.onDraw(g);
			if(e.getType() == Equipment.TYPE_BOMB){
				if(!((BombEquipment)e).isLive()){
					bombNum++;
				}
			}
		}
		if(bombNum != 0){
			g.drawImage(bombNumImg, 10, MainFrame.FRAME_HEIGHT-50, null);
			g.drawString(""+bombNum, 85, MainFrame.FRAME_HEIGHT-22);
		}
			
		
		g.setColor(Color.BLACK);
		g.drawImage(pauseFace, 10, 30, null);
		g.drawString(""+score, 60, 50);
		g.setColor(oldColor);
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
	
	public int getScore(){
		return score;
	}
	
}
