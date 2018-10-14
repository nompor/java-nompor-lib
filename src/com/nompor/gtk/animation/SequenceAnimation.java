package com.nompor.gtk.animation;

public class SequenceAnimation implements Animation{

	private Animation[] list;
	private Animation current = null;
	private int currentIndex;
	private boolean isEnd;

	public SequenceAnimation(Animation... animation) {
		this.list = animation;
		current = list[0];
		if ( current == null ) isEnd = true;
	}

	@Override
	public void update() {
		if ( isEnd ) return;
		if ( current.isEnd() ) {
			current = ++currentIndex<list.length ? list[currentIndex] : null;
			if ( current == null ) {
				isEnd = true;
				return;
			}
		}
		current.update();
	}

	@Override
	public boolean isEnd() {
		return isEnd;
	}
}
