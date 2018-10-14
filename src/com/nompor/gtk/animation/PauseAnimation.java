package com.nompor.gtk.animation;

import java.util.concurrent.TimeUnit;

import com.nompor.gtk.GTKException;

public class PauseAnimation implements Animation {

	private long ms;
	public PauseAnimation(long ms) {
		this.ms = System.currentTimeMillis() + ms;
	}
	public static PauseAnimation createAnimationStop(TimeUnit unit, long time) {
		switch(unit) {
		case DAYS:
			return new PauseAnimation(time*1000*60*60*24);
		case HOURS:
			return new PauseAnimation(time*1000*60*60);
		case MILLISECONDS:
			return new PauseAnimation(time);
		case MINUTES:
			return new PauseAnimation(time*1000*60);
		case SECONDS:
			return new PauseAnimation(time*1000);
		default:
			throw new GTKException(new IllegalArgumentException("TimeUnit is illegal argument."));
		}
	}

	@Override
	public void update() {}

	@Override
	public boolean isEnd() {
		return System.currentTimeMillis() >= ms;
	}
}
