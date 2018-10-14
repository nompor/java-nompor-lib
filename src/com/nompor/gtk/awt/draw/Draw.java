package com.nompor.gtk.awt.draw;

import java.awt.Graphics;

import com.nompor.gtk.draw.Drawable;

public interface Draw extends Drawable<Graphics> {
	void draw(Graphics g);
}
