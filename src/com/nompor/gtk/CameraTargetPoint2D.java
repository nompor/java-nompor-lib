package com.nompor.gtk;

import com.nompor.gtk.geom.Point;

public class CameraTargetPoint2D extends Point implements CameraTarget2D {
	public CameraTargetPoint2D() {
		super();
	}
	public CameraTargetPoint2D(double x, double y) {
		super(x, y);
	}
	@Override
	public double getCTX() {return x;}
	@Override
	public double getCTY() {return y;}
}
