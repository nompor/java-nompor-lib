package com.nompor.gtk.geom;

import com.nompor.gtk.GTKMath;

public class Point{
	public double x;
	public double y;
	public Point(){
	}
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}

	public int getIX() {
		return (int)x;
	}
	public int getIY() {
		return (int)y;
	}

	public Point toVector(Point p) {
		return new Point(p.x - x, p.y - y);
	}

	public double distance(Point p) {
		return distance(this,p);
	}

	public static double distance(Point p1, Point p2) {
		return GTKMath.distance(p1.x,p1.y,p2.x,p2.y);
	}

	public static double vector2(Point p1, Point p2) {
		return GTKMath.vector2(p1.x,p1.y,p2.x,p2.y);
	}

	public double vector2(Point p) {
		return vector2(this,p);
	}

	public static double dot(Point v1, Point v2) {
		return GTKMath.dot(v1.x, v1.y, v2.x, v2.y);
	}

	public static double cross(Point v1, Point v2) {
		return GTKMath.cross(v1.x, v1.y, v2.x, v2.y);
	}

	public double dot(Point v) {
		return dot(this, v);
	}

	public double cross(Point v) {
		return cross(this, v);
	}
}