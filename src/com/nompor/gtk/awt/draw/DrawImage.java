package com.nompor.gtk.awt.draw;

import java.awt.Graphics;
import java.awt.Image;

import com.nompor.gtk.animation.AnimationObject;
import com.nompor.gtk.geom.Rect;

public class DrawImage extends AnimationObject implements Draw{

	private Image img;
	private Rect viewport;
	private double x, y;

	public DrawImage(Image img) {
		this(img, 0, 0);
	}

	public DrawImage(Image img, double x, double y) {
		this.img = img;
		this.x = x;
		this.y = y;
		viewport=new Rect(x, y, img.getWidth(null), img.getHeight(null));
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void draw(Graphics g) {
		draw(g, (int)x, (int)y);
	}

	public void draw(Graphics g, int x, int y) {
		GraphicsUtil.drawImage(g, img, x, y, x + viewport.getIWidth(), y + viewport.getIHeight(), viewport.getIX(), viewport.getIY(), viewport.getIX() + viewport.getIWidth(), viewport.getIY() + viewport.getIHeight(), this);
	}

	public Rect getViewport() {
		return viewport;
	}

	public void setViewport(Rect viewport) {
		this.viewport = viewport;
	}
}
