package com.nompor.gtk;

import javax.swing.SwingUtilities;

import com.nompor.gtk.animation.ChangeGTKViewDrawAnimations;
import com.nompor.gtk.draw.Drawable;
import com.nompor.gtk.draw.GTKGraphics;
import com.nompor.gtk.fx.GTKManagerFX;
import com.nompor.gtk.geom.Point;
import com.nompor.gtk.image.GTKImage;
import com.nompor.gtk.image.GTKImageManager;
import com.nompor.gtk.input.GTKKeyCode;
import com.nompor.gtk.input.GTKKeyCodeManager;
import com.nompor.gtk.input.GTKMouseCode;
import com.nompor.gtk.input.GTKMouseManager;
import com.nompor.gtk.sound.SoundEffectManager;
import com.nompor.gtk.sound.SoundManager;
import com.nompor.gtk.sound.SoundType;
import com.nompor.gtk.swing.GTKManager;

import javafx.application.Platform;
import javafx.stage.Stage;

public class GTK {
	public static class InitParams{
		public Stage stage;
		public int width;
		public int height;
		public String title;
		public String iconPath;
		public boolean isGameLoopStart=true;
		public boolean isWindowShow=true;
		public GTKView initView;
		public SoundType soundType = SoundType.AUTO;
		public SoundType seType = SoundType.AUTO;
		public APIType apiType = APIType.AUTO;
		public Runnable onInit;
		public static InitParams create(int width, int height) {
			InitParams p = new InitParams();
			p.width = width;
			p.height = height;
			return p;
		}
	}
	private static GTKManageable instance;
	public static GTKImageManager img;
	public static GTKKeyCodeManager key;
	public static GTKMouseManager mouse;
	public static SoundManager bgm;
	public static SoundEffectManager se;
	public static GTKPaintFactory paint;
	public static GTKFontFactory font;
	public static GTKWindow window;
	public static GTKCanvas canvas;

	public static GTKManageable getManageable() {
		return instance;
	}

	public static void start(String title, String icon, int width, int height, GTKView initView, APIType type) {
		InitParams p = new InitParams();
		p.title = title;
		p.iconPath = icon;
		p.width = width;
		p.height = height;
		p.initView = initView;
		p.apiType = type;
		start(p);
	}

	public static void start(String title, int width, int height, GTKView initView) {
		start(title, null, width, height, initView, APIType.AUTO);
	}

	public static void start(String title, int width, int height, GTKView initView, APIType type) {
		start(title, null, width, height, initView, type);
	}

	public static void start(int width, int height, GTKView initView) {
		start(null, null, width, height, initView, APIType.AUTO);
	}

	public static void start(int width, int height, GTKView initView, APIType type) {
		start(null, null, width, height, initView, type);
	}

	public static void changeView(GTKView view) {
		canvas.changeView(view);
	}

	public static void changeViewDefaultAnimation(GTKView view) {
		GTKViewChanger changer = new GTKViewChanger(view, ChangeGTKViewDrawAnimations.createFadeChangeFillRect((int)getWidth(), (int)getHeight()));
		changer.prepare(canvas);
	}

	public static int getWidth() {
		return window.getViewWidth();
	}

	public static int getHeight() {
		return window.getViewHeight();
	}

	public static void playBGM(String filePath) {
		bgm.play(filePath);
	}

	public static void loopBGM(String filePath) {
		bgm.loop(filePath);
	}

	public static void pauseBGM() {
		bgm.pause();
	}

	public static void stopBGM() {
		bgm.stop();
	}

	public static void playSE(String filePath) {
		se.play(filePath);
	}

	public static void loopSE(String filePath) {
		se.loop(filePath);
	}

	public static void stopSE(String filePath) {
		se.stop(filePath);
	}

	public static void loadSE(String filePath) {
		se.load(filePath);
	}

	public static boolean isKeyPress(Object key) {
		return GTK.key.isPress(key);
	}

	public static boolean isKeyRelease(Object key) {
		return GTK.key.isRelease(key);
	}

	public static boolean isKeyDown(Object key) {
		return GTK.key.isDown(key);
	}

	public static boolean isKeyUp(Object key) {
		return GTK.key.isUp(key);
	}

	public static boolean isMousePress(Object key) {
		return mouse.isPress(key);
	}

	public static boolean isMouseRelease(Object key) {
		return mouse.isRelease(key);
	}

	public static boolean isMouseDown(Object key) {
		return mouse.isDown(key);
	}

	public static boolean isMouseUp(Object key) {
		return mouse.isUp(key);
	}

	public static Point getMousePoint() {
		return mouse.getPoint();
	}

	public static void registKey(Object key, GTKKeyCode keyCode) {
		GTK.key.regist(key, keyCode);
	}

	public static void removeKey(Object key) {
		GTK.key.remove(key);
	}

	public static void registMouse(Object key, GTKMouseCode mouseCode) {
		mouse.regist(key, mouseCode);
	}

	public static void removeMouse(Object key) {
		mouse.remove(key);
	}

	public static GTKImage getImage(String filePath) {
		return img.getImage(filePath);
	}

	public static void loadImage(String filePath) {
		img.load(filePath);
	}

	public static GTKPaint createLinearGradient(int sx, int sy, int ex, int ey, GTKCycleMethod method, float[] fractions, GTKColor[] colors) {
		return paint.createLinearGradient(sx,sy,ex,ey,method,fractions,colors);
	}

	public static GTKPaint createLinearGradient(int sx, int sy, int ex, int ey, GTKCycleMethod method, GTKGradientParam... params) {
		return paint.createLinearGradient(sx,sy,ex,ey,method,params);
	}

	public static GTKPaint createRadialGradient() {
		return paint.createRadialGradient();
	}

	public static GTKColor createColor(double r, double g, double b, double a) {
		return paint.createColor(r, g, b, a);
	}

	public static GTKColor createColor(double r, double g, double b) {
		return paint.createColor(r, g, b);
	}

	public static GTKColor createIntColor(int r, int g, int b) {
		return paint.createIntColor(r, g, b);
	}

	public static GTKColor color(Object clr) {
		return paint.color(clr);
	}

	public static GTKFont createFont(int size) {
		return font.createFont(size);
	}

	public static GTKFont createFont(String name, int size) {
		return font.createFont(name, size);
	}

	public static GTKFont createFont(String name, GTKFontWeight weight, GTKFontPosture posture, int size) {
		return font.createFont(name, weight, posture, size);
	}

	public static GTKImage createImage(int width, int height) {
		return img.createImage(width, height);
	}

	public static GTKImage createImage(int width, int height, Drawable<GTKGraphics> d) {
		return img.createImage(width, height, d);
	}

	/**
	 * UIスレッドで実行します。
	 * @param runnable
	 */
	public static void executeUIThread(Runnable runnable) {
		switch(getAPIType()) {
		case AUTO:throw new GTKException("invoke later fail.");
		case FX:
			Platform.runLater(runnable);
			break;
		case SWING:
			SwingUtilities.invokeLater(runnable);
			break;
		default:
			break;
		}
	}

	/**
	 * UIスレッドで実行します。
	 * @param runnable
	 */
	public static void invokeLater(Runnable runnable) {
		executeUIThread(runnable);
	}

	/**
	 * UIスレッドで実行します。
	 * @param runnable
	 */
	public static void runLater(Runnable runnable) {
		executeUIThread(runnable);
	}

	public static APIType getAPIType() {
		return instance.getAPIType();
	}

	public static synchronized void start(InitParams p) {
		if ( instance != null ) throw new GTKException(new IllegalStateException("It has already initialized"));

		Runnable callback = new Runnable() {
			public Runnable innerRun;

			@Override
			public void run() {
				GTK.bgm = instance.getSoundManager();
				GTK.se = instance.getSoundEffectManager();
				GTK.paint = instance.getPaintFactory();
				GTK.font = instance.getFontFactory();
				GTK.img = instance.getImageManager();
				GTK.key = instance.getKeyCodeManager();
				GTK.mouse = instance.getMouseManager();
				GTK.window = instance.getWindow();
				GTK.canvas = instance.getCanvas();
				if ( p.isWindowShow ) instance.show();
				GTK.canvas.changeView(p.initView);
				if ( p.isGameLoopStart ) instance.startGameLoop();
				if (innerRun != null) {
					innerRun.run();
				}
			}

			public Runnable init(Runnable run) {
				innerRun = run;
				return this;
			}
		}.init(p.onInit);
		p.onInit = callback;

		switch(p.apiType) {
		case FX:
			instance = GTKManagerFX.createManageable();
			instance.start(p);
			break;
		case SWING:
			instance = GTKManager.createManageable();
			instance.start(p);
			break;
		case AUTO:
			try {
				instance = GTKManagerFX.createManageable();
				instance.start(p);
				break;
			} catch ( Throwable t ) {
				try {
					instance = GTKManager.createManageable();
					instance.start(p);
					break;
				} catch ( Throwable t2 ) {
					throw new GTKException("API can't use it.");
				}
			}
		default:
		}
	}
}
