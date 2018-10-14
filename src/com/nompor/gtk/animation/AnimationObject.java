package com.nompor.gtk.animation;

public class AnimationObject implements AnimationTarget{
	private double opacity;
	private double translateX,translateY;
	private double scaleX,scaleY;
	private double rotate;
	public AnimationObject(){
		setOpacity(1f);
		scaleX = 1;
		scaleY = 1;
	}
	public AnimationObject(double translateX, double translateY){
		this();
		this.translateX = translateX;
		this.translateY = translateY;
	}
	public double getRotate() {
		return rotate;
	}
	public void setRotate(double rotate) {
		this.rotate = rotate;
	}
	public double getScaleX() {
		return scaleX;
	}
	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}
	public double getScaleY() {
		return scaleY;
	}
	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}
	public double getOpacity() {
		return opacity;
	}
	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}
	public double getTranslateX() {
		return translateX;
	}
	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}
	public double getTranslateY() {
		return translateY;
	}
	public void setTranslateY(double translateY) {
		this.translateY = translateY;
	}
}