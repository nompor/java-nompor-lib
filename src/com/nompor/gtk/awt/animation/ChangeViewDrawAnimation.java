package com.nompor.gtk.awt.animation;

import java.awt.Graphics;

import com.nompor.gtk.animation.ChangeViewDrawableAnimation;

public class ChangeViewDrawAnimation extends ChangeViewDrawableAnimation<Graphics> implements DrawAnimation {
	public ChangeViewDrawAnimation(DrawAnimation changeIn, DrawAnimation changeOut) {
		super(changeIn, changeOut);
	}
}
