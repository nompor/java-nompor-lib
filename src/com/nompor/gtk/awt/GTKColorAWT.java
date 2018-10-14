package com.nompor.gtk.awt;

import java.awt.Color;

import com.nompor.gtk.GTKColor;

public class GTKColorAWT extends GTKColor {

	public GTKColorAWT(Color clr) {
		super(clr);
	}

	@Override
	public int getIntRed() {
		return ((Color)p).getRed();
	}

	@Override
	public int getIntGreen() {
		return ((Color)p).getGreen();
	}

	@Override
	public int getIntBlue() {
		return ((Color)p).getBlue();
	}

}
