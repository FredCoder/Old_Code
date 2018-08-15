package com.fren.kinect.model;

import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.DepthMetaData;
import org.OpenNI.ImageGenerator;
import org.OpenNI.ImageMetaData;
import org.OpenNI.SceneMetaData;
import org.OpenNI.UserGenerator;

import com.fren.kinect.state.SessionState;
import com.primesense.NITE.SessionManager;

/**
 * OpenNi 实体
 * @author Fren
 *
 */
public class OpenNi {
	private Context context;
	
	private DepthGenerator depthGen;//深度产生器
	private DepthMetaData depthMD;//深度元数据
	
	private ImageGenerator imageGen;//图像产生器
	private ImageMetaData imageMD;//图片元数据
	
	private UserGenerator userGen;//用户产生器
	private SceneMetaData sceneMD;//用户元数据
	
	private SessionManager sessionMan;//session 管理
	private SessionState sessionState;//session状态
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public DepthGenerator getDepthGen() {
		return depthGen;
	}
	public void setDepthGen(DepthGenerator depthGen) {
		this.depthGen = depthGen;
	}
	public DepthMetaData getDepthMD() {
		return depthMD;
	}
	public void setDepthMD(DepthMetaData depthMD) {
		this.depthMD = depthMD;
	}
	public ImageGenerator getImageGen() {
		return imageGen;
	}
	public void setImageGen(ImageGenerator imageGen) {
		this.imageGen = imageGen;
	}
	public ImageMetaData getImageMD() {
		return imageMD;
	}
	public void setImageMD(ImageMetaData imageMD) {
		this.imageMD = imageMD;
	}
	public UserGenerator getUserGen() {
		return userGen;
	}
	public void setUserGen(UserGenerator userGen) {
		this.userGen = userGen;
	}
	public SceneMetaData getSceneMD() {
		return sceneMD;
	}
	public void setSceneMD(SceneMetaData sceneMD) {
		this.sceneMD = sceneMD;
	}
	public SessionManager getSessionMan() {
		return sessionMan;
	}
	public void setSessionMan(SessionManager sessionMan) {
		this.sessionMan = sessionMan;
	}
	public SessionState getSessionState() {
		return sessionState;
	}
	public void setSessionState(SessionState sessionState) {
		this.sessionState = sessionState;
	}
	
	
}
