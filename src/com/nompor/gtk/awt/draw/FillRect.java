package com.nompor.gtk.awt.draw;

import java.awt.Color;
import java.awt.Graphics;

import com.nompor.gtk.animation.AnimationObject;
import com.nompor.gtk.geom.Rect;

public class FillRect extends AnimationObject implements Draw{

	private Rect rect;
	private Color fillColor;
	public FillRect(double x, double y, double width, double height, Color fillColor) {
		this.fillColor=fillColor;
		rect = new Rect(x, y, width, height);
	}
	@Override
	public void draw(Graphics g) {
		GraphicsUtil.fillRect(g, rect, fillColor, this);
	}
}
