package com.nompor.gtk.animation;

public interface ChangeViewAnimation extends Animation {
	boolean isEndOfChangeIn();
	boolean isEndOfChangeOut();

	@Override
	default boolean isEnd() {
		return isEndOfChangeOut();
	}
}
