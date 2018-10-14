package com.nompor.gtk.animation;

import com.nompor.gtk.GTK;
import com.nompor.gtk.GTKColor;
import com.nompor.gtk.draw.Drawable;
import com.nompor.gtk.draw.GTKGraphics;

public class ChangeGTKViewDrawAnimations {
	public static ChangeGTKViewDrawAnimation createFadeChangeFillRect(int x, int y, int width, int height,GTKColor color, float changeSpeed) {
		return new ChangeGTKViewDrawAnimation(new FillRectAnimation(x, y, width, height, color, 0, changeSpeed), new FillRectAnimation(x, y, width, height, color, 1, -changeSpeed));
	}
	public static ChangeGTKViewDrawAnimation createFadeChangeFillRect(int width, int height,GTKColor color, float changeSpeed) {
		return createFadeChangeFillRect(0, 0, width, height, color, changeSpeed);
	}
	public static ChangeGTKViewDrawAnimation createFadeChangeFillRect(int width, int height,GTKColor color) {
		return createFadeChangeFillRect(width, height, color, 0.05f);
	}
	public static ChangeGTKViewDrawAnimation createFadeChangeFillRect(int width, int height, float changeSpeed) {
		return createFadeChangeFillRect(width, height, GTK.createColor(0,0,0), changeSpeed);
	}
	public static ChangeGTKViewDrawAnimation createFadeChangeFillRect(int width, int height) {
		return createFadeChangeFillRect(width, height, GTK.createColor(0,0,0));
	}

	private static class FillRectAnimation extends AnimationObject implements Drawable<GTKGraphics>, GTKDrawAnimation {

		private FadeAnimation anime;

		@Override
		public void update() {
			anime.update();
		}

		@Override
		public boolean isEnd() {
			return anime.isEnd();
		}

		private GTKColor fillColor;
		private double x,y,w,h;
		public FillRectAnimation(double x, double y, double width, double height, GTKColor fillColor, float alpha, float addOp) {
			this.fillColor=fillColor;
			this.x = x;
			this.y = y;
			w = width;
			h = height;
			setOpacity(alpha);
			anime = new FadeAnimation(this, addOp);
		}

		@Override
		public void draw(GTKGraphics g) {
			g.setColor(fillColor);
			g.setOpacity(getOpacity());
			g.fillRect(x, y, w, h);
			g.setOpacity(1);
		}
	}

}
