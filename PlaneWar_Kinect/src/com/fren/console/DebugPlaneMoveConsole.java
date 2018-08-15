package com.fren.console;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.fren.config.DebugConfig;



/**
 * 配置飞机移动坐标控制台[只是鼠标移动坐标，也许需要飞机的移动坐标，用kinect之后，鼠标是不移动的。]
 * @author Fren
 *
 */
public class DebugPlaneMoveConsole extends JFrame{
	private static final long serialVersionUID = 3716476488801479347L;

	JFrame debug;
	JLabel label = new JLabel("0,0", JLabel.CENTER);
	
	public DebugPlaneMoveConsole(JFrame debug){
		boolean flag = DebugConfig.getPlaneMoveConsole();
		if (!flag) {
			return;
		}
		this.setLocation(debug.getWidth() + debug.getLocation().x, 0);
		this.setSize(200, 50);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.add(label);
		this.addKeyListener(new MyKeyListener(this));
		this.setTitle("飞机移动坐标控制台");

		this.debug = debug;

		this.setVisible(true);
		
		Thread thread = new Thread(new DebugThread(debug));
		thread.start();
	}
	
	class MyKeyListener implements KeyListener {
		JFrame frame;
		
		public MyKeyListener(JFrame frame){
			this.frame = frame;
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				frame.dispose();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
	
	class DebugThread implements Runnable {

		JFrame debug;

		public DebugThread(JFrame debug) {
			this.debug = debug;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(30);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (debug.getMousePosition() != null){
					label.setText(debug.getMousePosition().x + "," + debug.getMousePosition().y);
				}
			}
		}
	}
}
