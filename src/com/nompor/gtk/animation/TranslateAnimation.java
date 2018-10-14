package com.nompor.gtk.animation;

public class TranslateAnimation extends AbstractFrameCounterAnimation{

	private TranslateTarget target;
	private double addX,addY;
	public TranslateAnimation(TranslateTarget target, double addX, double addY) {
		this.target = target;
		this.addX = addX;
		this.addY = addY;
	}

	@Override
	public void updateFrame() {
		target.setTranslateX(target.getTranslateX() + addX);
		target.setTranslateY(target.getTranslateY() + addY);
	}
}
