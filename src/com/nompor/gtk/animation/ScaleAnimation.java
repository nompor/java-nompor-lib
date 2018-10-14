package com.nompor.gtk.animation;

public class ScaleAnimation extends AbstractFrameCounterAnimation{

	private ScaleTarget target;
	private double addSCX,addSCY;
	public ScaleAnimation(ScaleTarget target, double addSCX, double addSCY) {
		this.target = target;
		this.addSCX = addSCX;
		this.addSCY = addSCY;
	}
	public ScaleAnimation(ScaleTarget target, double addSC) {
		this(target, addSC, addSC);
	}

	@Override
	public void updateFrame() {
		target.setScaleX(target.getScaleX() + addSCX);
		target.setScaleY(target.getScaleY() + addSCY);
	}
}