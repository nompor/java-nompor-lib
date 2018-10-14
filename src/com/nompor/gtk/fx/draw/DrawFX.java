package com.nompor.gtk.fx.draw;

import com.nompor.gtk.draw.Drawable;

import javafx.scene.canvas.GraphicsContext;

public interface DrawFX extends Drawable<GraphicsContext>{
	void draw(GraphicsContext g);
}
