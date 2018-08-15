package com.fren.kinect.model;

/**
 * 窗体的宽高
 * @author Fren
 *
 */
public class Size {
	private int imWidth;
	private int imHeight;
	
	public Size(int imWidth, int imHeight) {
		super();
		this.imWidth = imWidth;
		this.imHeight = imHeight;
	}
	public int getImWidth() {
		return imWidth;
	}
	public void setImWidth(int imWidth) {
		this.imWidth = imWidth;
	}
	public int getImHeight() {
		return imHeight;
	}
	public void setImHeight(int imHeight) {
		this.imHeight = imHeight;
	}
}
