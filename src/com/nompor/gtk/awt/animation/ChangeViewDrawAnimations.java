package com.nompor.gtk.awt.animation;

import java.awt.Color;

public class ChangeViewDrawAnimations {
	public static ChangeViewDrawAnimation createFadeChangeFillRect(int x, int y, int width, int height,Color color, float changeSpeed) {
		return new ChangeViewDrawAnimation(new FadeFillRectDrawAnimation(x, y, width, height, color, changeSpeed), new FadeFillRectDrawAnimation(x, y, width, height, color, -changeSpeed));
	}
	public static ChangeViewDrawAnimation createFadeChangeFillRect(int width, int height,Color color, float changeSpeed) {
		return createFadeChangeFillRect(0, 0, width, height, color, changeSpeed);
	}
	public static ChangeViewDrawAnimation createFadeChangeFillRect(int width, int height,Color color) {
		return createFadeChangeFillRect(width, height, color, 0.05f);
	}
	public static ChangeViewDrawAnimation createFadeChangeFillRect(int width, int height, float changeSpeed) {
		return createFadeChangeFillRect(width, height, Color.BLACK, changeSpeed);
	}
	public static ChangeViewDrawAnimation createFadeChangeFillRect(int width, int height) {
		return createFadeChangeFillRect(width, height, Color.BLACK);
	}
}
