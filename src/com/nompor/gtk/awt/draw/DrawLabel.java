package com.nompor.gtk.awt.draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import com.nompor.gtk.animation.AnimationObject;
import com.nompor.gtk.geom.Rect;

/**
 * 文字列をできるだけ指定座標中央になるように描画します。
 * @author nompor
 *
 */
public class DrawLabel extends AnimationObject implements Draw{

	private Font f;
	private String text;
	private Rectangle2D rect;
	private Color color;
	private final double x, y;
	public DrawLabel(Graphics g, String text, Font f, double x, double y) {
		this.x = x;
		this.y = y;
		this.text = text;
		setFont(g, f);
	}
	public DrawLabel(Graphics g, String text, Color color, Font f, double x, double y) {
		this(g, text, f, x, y);
		this.color = color;
	}

	public void setFont(Graphics g, Font f) {
		this.f = f;
		this.rect = g.getFontMetrics(f).getStringBounds(text, g);
		rect.setRect(x - rect.getWidth() / 2, y - rect.getHeight() / 2, rect.getWidth(), rect.getHeight());
	}

	@Override
	public void draw(Graphics g) {
		GraphicsUtil.drawCenteringString(g, text, (int)x, (int)y, f, color, this);
	}

	public void drawRect(Graphics g) {
		((Graphics2D)g).draw(rect);
	}

	public Rectangle2D getStringBounds() {
		return rect;
	}

	public boolean contains(Point p) {
		return contains(p.x, p.y);
	}

	public boolean contains(com.nompor.gtk.geom.Point p) {
		return contains((int)p.x, (int)p.y);
	}

	/**
	 * 指定された座標がこの文字列矩形内に収まるかどうか判定します。
	 * 判定はアフィン変換前の情報で行われます。
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(int x, int y) {
		return rect.contains(x, y);
	}

	public boolean intersects(Rectangle2D rect) {
		return intersects(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
	}

	public boolean intersects(Rect rect) {
		return intersects(rect.x, rect.y, rect.w, rect.h);
	}

	public boolean intersects(double x, double y, double w, double h) {
		return this.rect.intersects(x, y, w, h);
	}
}
