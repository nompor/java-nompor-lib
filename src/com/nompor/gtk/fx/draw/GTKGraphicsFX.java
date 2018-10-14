package com.nompor.gtk.fx.draw;

import com.nompor.gtk.draw.GTKGraphics;
import com.nompor.gtk.image.GTKImage;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

public class GTKGraphicsFX implements GTKGraphics{
	private GraphicsContext g;
	private Text text = new Text();
	public GTKGraphicsFX(Canvas cvs) {
		g = cvs.getGraphicsContext2D();
	}
	public GTKGraphicsFX(GraphicsContext g) {
		this.g = g;
	}

	public void setGraphics(GraphicsContext g) {
		this.g = g;
	}

	@Override
	public void strokeRect(double x, double y, double w, double h) {
		g.strokeRect(x, y, w, h);
	}

	@Override
	public void fillRect(double x, double y, double w, double h) {
		g.fillRect(x, y, w, h);
	}

	@Override
	public void strokeOval(double x, double y, double w, double h) {
		g.strokeOval(x, y, w, h);
	}

	@Override
	public void fillOval(double x, double y, double w, double h) {
		g.fillOval(x, y, w, h);
	}

	@Override
	public void drawLine(double x1, double y1, double x2, double y2) {
		g.strokeLine(x1, y1, x2, y2);
	}

	@Override
	public void drawImage(GTKImage img, double x, double y, double w, double h) {
		g.drawImage((Image)img.getImage(), x, y, w, h);
	}

	@Override
	public void drawImage(GTKImage img, double x, double y) {
		g.drawImage((Image)img.getImage(), x, y);
	}

	@Override
	public void drawImage(GTKImage img, double sx, double sy, double sw, double sh, double dx, double dy,
			double dw, double dh) {
		g.drawImage((Image)img.getImage(), sx, sy, sw, sh, dx, dy, dw, dh);
	}

	@Override
	public void setPaint(Object clr) {
		Paint p = (Paint)clr;
		g.setStroke(p);
		g.setFill(p);
	}

	@Override
	public void setFont(Object font) {
		Font f = (Font)font;
		g.setFont(f);
		this.text.setFont(f);
	}

	@Override
	public void drawString(String text, double x, double y) {
		g.fillText(text, x, y);
	}

	@Override
	public double stringWidth(String text) {
		this.text.setText(text);
		return this.text.getBoundsInLocal().getWidth();
	}

	@Override
	public double stringHeight(String text) {
		this.text.setText(text);
		return this.text.getBoundsInLocal().getHeight();
	}

	@Override
	public void setOpacity(double opacity) {
		g.setGlobalAlpha(opacity);
	}

	@Override
	public void strokeLine(double x1, double y1, double x2, double y2) {
		drawLine(x1,y1,x2,y2);
	}
	@Override
	public void setTextAntialias(boolean isAntialias) {
		g.setFontSmoothingType(isAntialias ? FontSmoothingType.GRAY : FontSmoothingType.LCD);
	}
	@Override
	public boolean isTextAntialias() {
		return g.getFontSmoothingType() == FontSmoothingType.GRAY;
	}

}
