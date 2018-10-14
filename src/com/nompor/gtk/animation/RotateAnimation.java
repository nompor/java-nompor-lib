package com.nompor.gtk.animation;

public class RotateAnimation extends AbstractFrameCounterAnimation {


	private RotateTarget target;
	private double addRotate;
	public RotateAnimation(RotateTarget target, double addRotate) {
		this.target = target;
		this.addRotate = addRotate;
	}
	public RotateAnimation(RotateTarget target, int angle) {
		this(target, Math.toRadians(angle));
	}

	@Override
	public void updateFrame() {
		target.setRotate(target.getRotate() + addRotate);
	}
}
