package com.nompor.gtk.geom;

public class Ellipse{
	public double cx;
	public double cy;
	public double rx;
	public double ry;
	public Ellipse(double cx, double cy, double rx, double ry){
		this(rx, ry);
		this.cx = cx;
		this.cy = cy;
	}
	public Ellipse(double rx, double ry){
		this.rx = rx;
		this.ry = ry;
	}
	public Ellipse(){
	}
}