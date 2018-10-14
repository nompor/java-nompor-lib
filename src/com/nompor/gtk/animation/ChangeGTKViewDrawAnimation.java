package com.nompor.gtk.animation;

import com.nompor.gtk.draw.GTKGraphics;

public class ChangeGTKViewDrawAnimation extends ChangeViewDrawableAnimation<GTKGraphics> implements GTKDrawAnimation {
	public ChangeGTKViewDrawAnimation(GTKDrawAnimation changeIn, GTKDrawAnimation changeOut) {
		super(changeIn, changeOut);
	}
}
