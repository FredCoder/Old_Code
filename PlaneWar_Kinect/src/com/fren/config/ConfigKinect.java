package com.fren.config;

import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.GestureGenerator;
import org.OpenNI.HandsGenerator;
import org.OpenNI.ImageGenerator;
import org.OpenNI.License;
import org.OpenNI.MapOutputMode;
import org.OpenNI.PixelFormat;
import org.OpenNI.UserGenerator;

import com.fren.kinect.model.OpenNi;
import com.fren.kinect.state.SessionState;
import com.primesense.NITE.SessionManager;

/**
 * 配置kinect硬件参数
 * 
 * @author Fren
 * 
 */
public class ConfigKinect {
	@SuppressWarnings("unused")
	public OpenNi config() {
		OpenNi model = new OpenNi();
		try {
			Context context = new Context();

			// add the NITE Licence
			License license = new License("PrimeSense",
					"0KOIk2JeIBYClPWVnMoRKn5cdY4="); // vendor, key
			context.addLicense(license);

			ImageGenerator imageGen = ImageGenerator.create(context);// 图像生成器
			System.out.println("add image");
			DepthGenerator depthGen = DepthGenerator.create(context);// 深度生成器
			System.out.println("add depth");
			UserGenerator userGen = UserGenerator.create(context);// 用户生成器\
			System.out.println("add user");

			MapOutputMode mapMode = new MapOutputMode(640, 480, 30); // xRes,yRes,FPS

			depthGen.setMapOutputMode(mapMode);

			imageGen.setMapOutputMode(mapMode);
			imageGen.setPixelFormat(PixelFormat.RGB24);// 设置图像色位
			
			context.setGlobalMirror(true); // set mirror mode

			/* 设置手，手势生成器 */
			HandsGenerator hands = HandsGenerator.create(context);
			System.out.println("add hands");
			hands.SetSmoothing(0.1f);
			GestureGenerator gesture = GestureGenerator.create(context);
			System.out.println("add gesture");

			context.startGeneratingAll();
			// set up session manager and points listener
			SessionManager sessionMan = new SessionManager(context,
					"Click,Wave", "RaiseHand");

			model.setContext(context);
			model.setImageGen(imageGen);
			model.setImageMD(imageGen.getMetaData());

			model.setDepthGen(depthGen);
			model.setDepthMD(depthGen.getMetaData());

			model.setUserGen(userGen);
			model.setSceneMD(userGen.getUserPixels(0));// 默认设置id为0的用户

			model.setSessionMan(sessionMan);
			model.setSessionState(SessionState.NOT_IN_SESSION);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		return model;
	}
}
