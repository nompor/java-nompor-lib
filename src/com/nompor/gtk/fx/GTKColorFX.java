package com.nompor.gtk.fx;

import com.nompor.gtk.GTKColor;

import javafx.scene.paint.Color;

public class GTKColorFX extends GTKColor {

	public GTKColorFX(Color clr) {
		super(clr);
	}

	@Override
	public int getIntRed() {
		return (int)(((Color)p).getRed()*255);
	}

	@Override
	public int getIntGreen() {
		return (int)(((Color)p).getRed()*255);
	}

	@Override
	public int getIntBlue() {
		return (int)(((Color)p).getRed()*255);
	}

}
