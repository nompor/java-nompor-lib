package com.nompor.gtk.animation;


public class ParallelAnimation implements Animation {
	private Animation[] animations;
	private boolean isEnd;
	public ParallelAnimation(Animation... animations){
		this.animations = animations;
	}
	public void update(){
		if ( this.isEnd ) return;
		boolean isEnd = true;
		for ( Animation a : animations ) {
			boolean isEndAnimation = a.isEnd();
			if ( !isEndAnimation ) a.update();
			isEnd &= isEndAnimation;
		}
		this.isEnd = isEnd;
	}
	public boolean isEnd(){
		return isEnd;
	}
}
