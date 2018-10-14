package com.nompor.gtk.animation;

public class AbstractChangeViewAnimation implements ChangeViewAnimation{

	private Animation changeIn;
	private Animation changeOut;

	public AbstractChangeViewAnimation(Animation changeIn, Animation changeOut) {
		this.changeIn = changeIn;
		this.changeOut = changeOut;
	}

	@Override
	public final void update() {
		if ( isEnd() ) return;
		if ( !isEndOfChangeIn() ) {
			changeIn.update();
		} else if ( !isEndOfChangeOut() ) {
			changeOut.update();
		}
	}

	public boolean isEndOfChangeIn() {
		return changeIn.isEnd();
	}

	public boolean isEndOfChangeOut() {
		return changeOut.isEnd();
	}

	public final boolean isEnd() {
		return ChangeViewAnimation.super.isEnd();
	}
}
