package com.nompor.gtk.fx;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public final class DefaultViewFX extends GameViewFX {
	private Rectangle rect = new Rectangle();
	public DefaultViewFX(Paint backgroundPaint) {
		rect.setFill(backgroundPaint);
		getChildren().add(rect);
	}
	public DefaultViewFX() {
		this(Color.WHITE);
	}
	public void setSize(double w, double h) {
		setWidth(w);
		setHeight(h);
	}
	public void setWidth(double w) {
		rect.setWidth(w);
	}
	public void setHeight(double h) {
		rect.setHeight(h);
	}
}
