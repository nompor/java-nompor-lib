package com.nompor.gtk.geom;

public class Rect{
	public double x;
	public double y;
	public double w;
	public double h;
	public Rect(double w, double h){
		this.w = w;
		this.h = h;
	}
	public Rect(double x, double y, double w, double h){
		this(w,h);
		this.x = x;
		this.y = y;
	}
	public Rect(){}

	public boolean intersects(Rect rect){
		return intersects(rect.x,rect.y,rect.w,rect.h);
	}

	public boolean contains(double px, double py){
        return x < px && y < py && x + w > px && y + h > py;
	}

	public boolean contains(Point p){
		return contains(p.x, p.y);
	}

	public boolean intersects(double x2, double y2, double w2, double h2){
		return x      < x2 + w2 &&
				x + w  > x2      &&
				y      < y2 + h2 &&
				y + h  > y2;
	}
	public int getIX() {
		return (int)x;
	}
	public int getIY() {
		return (int)y;
	}
	public int getIWidth() {
		return (int)w;
	}
	public int getIHeight() {
		return (int)h;
	}
	public double getCX() {
		return x + w / 2;
	}
	public double getCY() {
		return x + h / 2;
	}
}