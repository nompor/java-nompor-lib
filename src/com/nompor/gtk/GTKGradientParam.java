package com.nompor.gtk;

public class GTKGradientParam {
	public float fraction;
	public GTKColor color;
	public GTKGradientParam(float fraction, GTKColor color) {
		this.fraction = fraction;
		this.color = color;
	}
	public GTKGradientParam(double fraction, GTKColor color) {
		this.fraction = (float) fraction;
		this.color = color;
	}
}
