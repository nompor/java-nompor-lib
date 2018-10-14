package com.nompor.gtk.fx.animation;

import com.nompor.gtk.animation.ChangeViewDrawableAnimation;

import javafx.scene.canvas.GraphicsContext;

public class ChangeViewDrawAnimationFX extends ChangeViewDrawableAnimation<GraphicsContext> implements DrawAnimationFX{
	public ChangeViewDrawAnimationFX(DrawAnimationFX changeIn, DrawAnimationFX changeOut) {
		super(changeIn, changeOut);
	}
}
