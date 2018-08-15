package com.fren.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class DebugConfig {
	
	
	/**
	 * 判断是否开启飞机移动Debug控制台
	 * @return
	 */
	public static boolean getPlaneMoveConsole(){
		try{
			ResourceBundle rb = ResourceBundle.getBundle("debug");
			if (!Boolean.parseBoolean(rb.getString("plane.move.console")))
				return false;
		}catch(MissingResourceException e){
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	/**
	 * 判断是否开启kinect焦点移动Debug控制台
	 * @return
	 */
	public static boolean getKinectMoveConsole(){
		try{
			ResourceBundle rb = ResourceBundle.getBundle("debug");
			if (!Boolean.parseBoolean(rb.getString("kinect.move.console")))
				return false;
		}catch(MissingResourceException e){
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	/**
	 * 判断是否开启kinectVGA摄像头Debug控制台
	 * @return
	 */
	public static boolean getKinectVgaConsole(){
		try{
			ResourceBundle rb = ResourceBundle.getBundle("debug");
			if (!Boolean.parseBoolean(rb.getString("kinect.vga.console")))
				return false;
		}catch(MissingResourceException e){
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	/**
	 * 判断是否开启kinect骨骼数据Debug控制台
	 * @return
	 */
	public static boolean getKinectTrackerConsole(){
		try{
			ResourceBundle rb = ResourceBundle.getBundle("debug");
			if (!Boolean.parseBoolean(rb.getString("kinect.tracker.console")))
				return false;
		}catch(MissingResourceException e){
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	/**
	 * 判断是否开启kinect启动状态Debug控制台
	 * @return
	 */
	public static boolean getKinectStateConsole(){
		try{
			ResourceBundle rb = ResourceBundle.getBundle("debug");
			if (!Boolean.parseBoolean(rb.getString("kinect.state.console")))
				return false;
		}catch(MissingResourceException e){
			System.out.println(e.getMessage());
		}
		return true;
	}
}
