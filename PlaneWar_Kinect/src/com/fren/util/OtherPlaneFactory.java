package com.fren.util;

import java.util.ArrayList;
import java.util.Random;

import com.fren.plane.main.Controller;
import com.fren.plane.main.MainFrame;
import com.fren.plane.model.OtherPlane;
import com.fren.plane.model.OtherPlane2;
import com.fren.plane.model.Plane;

public class OtherPlaneFactory {
	
	public static Plane getPlane(Controller controller){
		Random rand = new Random();
		int px = rand.nextInt(MainFrame.FRAME_WIDIH);
		int py = OtherPlane.PLANE_HEIGHT/2;
		
		int type = rand.nextInt(10);
		Plane op = null;
		if(type<9)
			op = new OtherPlane(px,py,controller);
		else
			op = new OtherPlane2(px, py, controller);
			
		return op;
	}
	
	public static void getPlanes(int num, ArrayList<Plane> others, Controller controller){
		
		for(int i=0;i<num;i++){
			others.add(getPlane(controller));
		}
		
	}
}
