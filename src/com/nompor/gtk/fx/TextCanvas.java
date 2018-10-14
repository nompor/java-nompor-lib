package com.nompor.gtk.fx;

import com.nompor.gtk.GTKException;

import javafx.beans.value.WritableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextCanvas extends Canvas {

	private String text;
	private double ox,oy;
	private Color backColor;
	private Color fontColor;
	private WritableValue<Double> globalAlpha = new WritableValue<Double>() {

		private double alpha=1;

		@Override
		public Double getValue() {
			return alpha;
		}

		@Override
		public void setValue(Double arg0) {
			alpha = arg0;
			draw();
		}
	};

	protected TextCanvas(boolean isInitializedDraw,String text, double x, double y, double width, double height, double ox, double oy) {
		setLayoutX(x);
		setLayoutY(y);
		setWidth(width);
		setHeight(height);
		setText(text);
		this.setOffsetX(ox);
		this.setOffsetY(oy);
		if ( isInitializedDraw ) draw();
	}

	public TextCanvas(String text, double x, double y, double width, double height, double ox, double oy) {
		this(true, text, x, y, width, height, ox, oy);
	}

	public TextCanvas(String text, double width, double height, double ox, double oy) {
		this(text, 0, 0, width, height, ox, oy);
	}

	@Override
	public final GraphicsContext getGraphicsContext2D() {
		throw new GTKException("Get graphics invalid method.");
	}

	public void setColor(Color clr) {
		this.fontColor = clr;
		draw();
	}

	public void setBackgroundColor(Color clr) {
		this.backColor = clr;
		draw();
	}

	public void setFont(Font f) {
		super.getGraphicsContext2D().setFont(f);
		draw();
	}

	public void setText(String text) {
		this.text = text;
		draw();
	}

	public String getText() {
		return text;
	}

	protected void draw() {
		GraphicsContext g = super.getGraphicsContext2D();
		g.setGlobalAlpha(globalAlpha.getValue());
		g.clearRect(0, 0, getWidth(), getHeight());
		if ( backColor != null ) {
			g.setFill(backColor);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if ( fontColor == null ) fontColor = Color.BLACK;
		g.setFill(fontColor);
		g.fillText(text, ox, oy);
	}

	public double getOffsetX() {
		return ox;
	}

	public void setOffsetX(double ox) {
		this.ox = ox;
	}

	public double getOffsetY() {
		return oy;
	}

	public void setOffsetY(double oy) {
		this.oy = oy;
	}

	public void setGlobalAlpha(double alpha) {
		globalAlpha.setValue(alpha);
	}

	public double getGlobalAlpha() {
		return globalAlpha.getValue();
	}

	public WritableValue<Double> globalAlpha(){
		return globalAlpha;
	}
}
