package com.nompor.gtk.awt.draw;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;

import com.nompor.gtk.draw.GTKGraphics;
import com.nompor.gtk.image.GTKImage;

public class GTKGraphicsAWT implements GTKGraphics {

	private Graphics2D g;
	public GTKGraphicsAWT(Graphics g) {
		this.g = (Graphics2D) g;
	}

	public void setGraphics(Graphics g) {
		this.g = (Graphics2D) g;
	}

	@Override
	public void strokeRect(double x, double y, double w, double h) {
		g.drawRect((int)x, (int)y, (int)w, (int)h);
	}

	@Override
	public void fillRect(double x, double y, double w, double h) {
		g.fillRect((int)x, (int)y, (int)w, (int)h);
	}

	@Override
	public void strokeOval(double x, double y, double w, double h) {
		g.drawOval((int)x, (int)y, (int)w, (int)h);
	}

	@Override
	public void fillOval(double x, double y, double w, double h) {
		g.fillOval((int)x, (int)y, (int)w, (int)h);
	}

	@Override
	public void drawImage(GTKImage img, double x, double y, double w, double h) {
		g.drawImage((Image)img.getImage(), (int)x, (int)y, (int)w, (int)h, null);
	}

	@Override
	public void drawImage(GTKImage img, double x, double y) {
		g.drawImage((Image)img.getImage(), (int)x, (int)y, null);
	}

	@Override
	public void drawImage(GTKImage img, double sx, double sy, double sw, double sh, double dx, double dy, double dw, double dh) {
		g.drawImage((Image)img.getImage(), (int)dx, (int)dy, (int)dx+(int)dw, (int)dy+(int)dh, (int)sx, (int)sy, (int)sx+(int)sw, (int)sy+(int)sh, null);
	}

	@Override
	public void setPaint(Object clr) {
		g.setPaint((Paint)clr);
	}

	@Override
	public void setFont(Object font) {
		g.setFont((Font)font);
	}

	@Override
	public void drawString(String text, double x, double y) {
		g.drawString(text, (int)x, (int)y);
	}

	@Override
	public double stringWidth(String text) {
		return GraphicsUtil.getStringBounds(g, text).getWidth();
	}

	@Override
	public double stringHeight(String text) {
		return GraphicsUtil.getStringBounds(g, text).getHeight();
	}

	@Override
	public void setOpacity(double opacity) {
		GraphicsUtil.setAlpha(g, (float)opacity);
	}

	@Override
	public void strokeLine(double x1, double y1, double x2, double y2) {
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}

	@Override
	public void setTextAntialias(boolean isAntialias) {
		GraphicsUtil.setTextAntialiasing(g, true);
	}

	@Override
	public boolean isTextAntialias() {
		return RenderingHints.VALUE_ANTIALIAS_ON == GraphicsUtil.getTextAntialiasing(g);
	}
}
