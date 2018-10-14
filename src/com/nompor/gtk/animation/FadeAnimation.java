package com.nompor.gtk.animation;

public class FadeAnimation extends AbstractFrameCounterAnimation{

	private OpacityTarget target;
	private double addOpacity;
	private boolean isEnd;
	public FadeAnimation(OpacityTarget target, double addOpacity) {
		this.target = target;
		this.addOpacity = addOpacity;
	}

	@Override
	public void updateFrame() {
		double op = target.getOpacity() + addOpacity;
		if ( op >= 1 ) {
			op = 1f;
			isEnd = true;
		} else if ( op <= 0 ) {
			op = 0;
			isEnd = true;
		}
		target.setOpacity(op);
	}

	public boolean isEnd() {
		return super.isEnd() || isEnd;
	}
}