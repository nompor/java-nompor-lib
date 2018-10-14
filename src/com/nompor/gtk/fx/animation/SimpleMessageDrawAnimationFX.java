package com.nompor.gtk.fx.animation;

import com.nompor.gtk.animation.AbstractFrameCounterAnimation;
import com.nompor.gtk.animation.MessageDrawableAnimation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class SimpleMessageDrawAnimationFX extends AbstractFrameCounterAnimation implements MessageDrawableAnimation<GraphicsContext>{

	public double x;
	public double y;

	private String text;
	private boolean isEnd;
	private String currentText="";
	private int index;
	private Color color;
	private Font font;
	private double alpha=1;
	private int updateDelay;

	public SimpleMessageDrawAnimationFX(String text, double x, double y, int updateDelay) {
		this(text,x,y,updateDelay, null, null);
	}

	public SimpleMessageDrawAnimationFX(String text, double x, double y, int updateDelay, Color color, Font font) {
		this.updateDelay= Math.max(updateDelay, 1);
		this.x = x;
		this.y = y;
		this.color = color;
		this.font = font;
		this.text = text;
	}

	@Override
	public void draw(GraphicsContext g) {
		Font f = g.getFont();
		Paint stroke = g.getStroke();
		Paint fill = g.getFill();
		double a = g.getGlobalAlpha();
		g.setStroke(null);
		if ( color != null ) g.setFill(color);
		if ( font != null ) g.setFont(font);
		g.setGlobalAlpha(alpha);
		g.fillText(currentText, x, y);
		g.setFont(f);
		g.setStroke(stroke);
		g.setFill(fill);
		g.setGlobalAlpha(a);
	}


	@Override
	public boolean isEnd() {
		return isEnd;
	}
	/**
	 * メッセージをすべて表示し、アニメーションを終了させます。
	 */
	public void doFinal() {
		currentText = text;
		isEnd = true;
	}

	/**
	 * このメッセージアニメーションを初期状態に設定します。
	 */
	public void doInit() {
		currentText = "";
		index = 0;
		isEnd = false;
	}

	@Override
	public void updateFrame() {
		if ( isUpdateDelayFrame(updateDelay) ) {
			currentText += text.charAt(index);
			index++;
			if ( index >= text.length() ) isEnd = true;
		}
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
}
