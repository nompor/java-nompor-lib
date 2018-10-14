package com.nompor.gtk;

public abstract class GTKColor extends GTKPaint {
	public GTKColor(Object clr) {
		super(clr);
	}
	public abstract int getIntRed();
	public abstract int getIntGreen();
	public abstract int getIntBlue();
	public Object getColor() {
		return p;
	}
}
