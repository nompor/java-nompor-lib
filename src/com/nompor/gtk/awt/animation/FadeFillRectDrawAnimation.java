package com.nompor.gtk.awt.animation;

import java.awt.Color;

import com.nompor.gtk.animation.FadeAnimation;
import com.nompor.gtk.awt.draw.FillRect;

public class FadeFillRectDrawAnimation extends FillRect implements DrawAnimation {

	private FadeAnimation anime;

	public FadeFillRectDrawAnimation(double x, double y, double width, double height, Color color, float alpha) {
		super(x, y, width, height, color);
		anime = new FadeAnimation(this, alpha);
	}

	@Override
	public void update() {
		anime.update();
	}

	@Override
	public boolean isEnd() {
		return anime.isEnd();
	}
}
