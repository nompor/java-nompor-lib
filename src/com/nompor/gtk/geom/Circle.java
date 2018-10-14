package com.nompor.gtk.geom;

import com.nompor.gtk.GTKMath;

/**
 * @author nompor
 *
 */
public class Circle{
	public double radius;
	public double cx;
	public double cy;

	/**
	 * 指定された引数の円を作成する
	 * @param cx 円の中心点x座標
	 * @param cy 円の中心点y座標
	 * @param radius 円の半径
	 */
	public Circle(double cx, double cy, double radius) {
		this.cx = cx;
		this.cy = cy;
		this.radius = radius;
	}

	/**
	 * この円の左x座標を返します。
	 * @return
	 */
	public double getLeft() {
		return cx - radius;
	}


	/**
	 * この円の上y座標を返します。
	 * @return
	 */
	public double getTop() {
		return cy - radius;
	}

	/**
	 * この円の左x座標を返します。
	 * @return
	 */
	public double getX() {
		return getLeft();
	}


	/**
	 * この円の上y座標を返します。
	 * @return
	 */
	public double getY() {
		return getTop();
	}


	/**
	 * この円の右x座標を返します。
	 * @return
	 */
	public double getRight() {
		return cx + radius;
	}

	/**
	 * この円の下y座標を返します。
	 * @return
	 */
	public double getBottom() {
		return cy + radius;
	}

	/**
	 * この円の中点xを返します。
	 * @return
	 */
	public double getCX() {
		return cx;
	}

	/**
	 * この円の中点yを返します。
	 * @return
	 */
	public double getCY() {
		return cy;
	}

	/**
	 * この円の中心点xを設定します。
	 * @param x
	 */
	public void setCX(double x) {
		cx = x;
	}

	/**
	 * この円の中心点yを設定します。
	 * @param y
	 */
	public void setCY(double y) {
		cy = y;
	}

	/**
	 * この円半径を返します。
	 * @return
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * この円を内包する矩形を返します。
	 * @return
	 */
	public Rect getBounds() {
		double r = getRadius()*2;
		return new Rect(getLeft(), getTop(), r, r);
	}

	/**
	 * この円の左x座標をintで返します。
	 * @return
	 */
	public int getILeft() {
		return (int)(cx - radius);
	}

	/**
	 * この円の上y座標をintで返します。
	 * @return
	 */
	public int getITop() {
		return (int)(cy - radius);
	}

	/**
	 * この円の右x座標をintで返します。
	 * @return
	 */
	public int getIRight() {
		return (int)getRight();
	}

	/**
	 * この円の下y座標をintで返します。
	 * @return
	 */
	public int getIBottom() {
		return (int)getBottom();
	}

	/**
	 * この円の左x座標をintで返します。
	 * @return
	 */
	public int getIX() {
		return (int)getX();
	}

	/**
	 * この円の上y座標をintで返します。
	 * @return
	 */
	public int getIY() {
		return (int)getY();
	}

	/**
	 * この円の中心点xをintで返します。
	 * @return
	 */
	public int getICX() {
		return (int)getCX();
	}

	/**
	 * この円の中心点yをintで返します。
	 * @return
	 */
	public int getICY() {
		return (int)getCY();
	}

	/**
	 * この円の半径をintで返します。
	 * @return
	 */
	public int getIRadius() {
		return (int)getRadius();
	}

	/**
	 * 指定された円がこの円と交差しているどうかを返します。
	 * @param circle
	 * @return
	 */
	public boolean intersects(Circle circle) {
		return intersects(circle.cx, circle.cy, circle.radius);
	}

	/**
	 * 指定された円がこの円と交差しているどうかを返します。
	 * @param cx
	 * @param cy
	 * @param radius
	 * @return
	 */
	public boolean intersects(double cx, double cy, double radius) {
		double vr = radius + this.radius;
		return GTKMath.vector2(cx, cy, this.cx, this.cy) <= vr*vr;
	}

	/**
	 * 指定された座標がこの円に内包されているかどうかを返します。
	 * @param p
	 * @return
	 */
	public boolean contains(Point p) {
		return contains(p.x, p.y);
	}

	/**
	 * 指定された座標がこの円に内包されているかどうかを返します。
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(double x, double y) {
		return GTKMath.vector2(x, y, cx, cy) <= radius*radius;
	}
}