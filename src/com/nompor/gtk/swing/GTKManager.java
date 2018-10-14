package com.nompor.gtk.swing;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.nompor.gtk.APIType;
import com.nompor.gtk.GTKCanvas;
import com.nompor.gtk.GTKException;
import com.nompor.gtk.GTKFontFactory;
import com.nompor.gtk.GTKManageable;
import com.nompor.gtk.GTKPaintFactory;
import com.nompor.gtk.GTKView;
import com.nompor.gtk.GTKWindow;
import com.nompor.gtk.awt.GTKFontFactoryAWT;
import com.nompor.gtk.awt.GTKPaintFactoryAWT;
import com.nompor.gtk.awt.image.ImageManager;
import com.nompor.gtk.awt.input.KeyCodeManager;
import com.nompor.gtk.awt.input.MouseManager;
import com.nompor.gtk.geom.Point;
import com.nompor.gtk.image.GTKImageManager;
import com.nompor.gtk.input.GTKKeyCodeManager;
import com.nompor.gtk.input.GTKMouseManager;
import com.nompor.gtk.sound.SoundEffectManager;
import com.nompor.gtk.sound.SoundManager;
import com.nompor.gtk.sound.SoundType;
import com.nompor.util.LibraryLogger;

public class GTKManager {

	public static class InitParams{
		public int width;
		public int height;
		public String title;
		public Image icon;
		public String iconPath;
		public boolean isGameLoopStart=true;
		public boolean isWindowShow=true;
		public GameView initView;
		public SoundType soundType = SoundType.WAVE_STREAMING;
		public boolean isUseImageManager = true;
		public boolean isUseSoundManager = true;
		public boolean isUseSoundEffectManager = true;
		public boolean isUseTransitionManager = true;
		public static InitParams create(int width, int height) {
			InitParams p = new InitParams();
			p.width = width;
			p.height = height;
			return p;
		}
	}

	private static GTKManager instance = null;
	private GameWindow wnd;
	private SoundManager sm;
	private SoundEffectManager sem;
	private ImageManager im;

	private GTKManager(int width, int height) {
		sm = SoundManager.createStreamInstance();
		sem = SoundEffectManager.createInstance();
		wnd = new GameWindow(width, height);
		im = ImageManager.createInstance(wnd);
	}

	public static GTKManager getInstance() {
		return instance;
	}
	public static void start(int width, int height, GameView initView) {
		start(null, width, height, initView);
	}
	public static void start(String title, int width, int height, GameView initView) {
		start(title, (Image)null, width, height, initView);
	}
	public static synchronized void start(String title, String iconPath, int width, int height, GameView initView) {
		try {
			start(title, ImageIO.read(new File(iconPath)), width, height, initView);
		} catch (IOException e) {
			LibraryLogger.error(e);
		}
	}
	public static synchronized void start(String title, Image icon, int width, int height, GameView initView) {
		InitParams params = new InitParams();
		params.title = title;
		params.icon = icon;
		params.width = width;
		params.height = height;
		params.initView = initView;
		start(params);
	}
	public static synchronized void start(InitParams params) {
		if ( instance != null ) throw new GTKException(new IllegalStateException("It has already initialized"));
		instance = new GTKManager(params.width, params.height);
		instance.wnd.setTitle(params.title);
		instance.wnd.changeView(params.initView);
		instance.wnd.setIconImage(params.icon);
		if (params.isWindowShow)  instance.wnd.setVisible(true);
		if (params.isGameLoopStart)  startGameLoop();
	}
	public static void end() {
		System.exit(0);
	}
	public static int getWidth() {
		return getWindow().getViewWidth();
	}
	public static int getHeight() {
		return getWindow().getViewHeight();
	}
	public static GameWindow getWindow() {
		return instance.wnd;
	}
	/**
	 * 現在のスレッドでGameViewの変更処理を実行します。
	 * @param view
	 */
	public static void changeView(GameView view) {
		getWindow().changeView(view);
	}
	/**
	 * ゲームループスレッドにGameViewの変更処理を要求します。
	 * @param view
	 */
	public static void changeViewEvent(GameView view) {
		getWindow().changeViewEvent(view);
	}
	/**
	 * ゲームループを開始させます。
	 */
	public static void startGameLoop() {
		instance.wnd.startGameLoop();
	}
	/**
	 * ゲームループを停止させます。
	 */
	public static void stopGameLoop() {
		instance.wnd.stopGameLoop();
	}
	/**
	 * ゲームループを停止させ、ゲームスレッドが終了するまで待機します。
	 * シングルスレッドのゲームループの場合、デッドロックとなるので注意してください。
	 */
	public static void stopGameLoopJoin() {
		instance.wnd.stopGameLoopJoin();
	}
	/**
	 * ウィンドウに再描画を促します。
	 */
	public static void repaint() {
		instance.wnd.repaint();
	}

	public static SoundManager getSoundManager() {
		return instance.sm;
	}

	public static SoundEffectManager getSoundEffectManager() {
		return instance.sem;
	}

	public static ImageManager getImageManager() {
		return instance.im;
	}

	public static KeyCodeManager getKeyCodeManager() {
		return instance.wnd.getKeyCodeManager();
	}

	public static MouseManager getMouseManager() {
		return instance.wnd.getMouseManager();
	}

	public static boolean isKeyPress(String keyName) {
		return instance.wnd.getKeyCodeManager().isPress(keyName);
	}

	public static boolean isKeyRelease(String keyName) {
		return instance.wnd.getKeyCodeManager().isRelease(keyName);
	}

	public static boolean isKeyDown(String keyName) {
		return instance.wnd.getKeyCodeManager().isDown(keyName);
	}

	public static boolean isKeyUp(String keyName) {
		return instance.wnd.getKeyCodeManager().isUp(keyName);
	}

	public static boolean isMousePress(String keyName) {
		return instance.wnd.getMouseManager().isPress(keyName);
	}

	public static boolean isMouseRelease(String keyName) {
		return instance.wnd.getMouseManager().isRelease(keyName);
	}

	public static boolean isMouseDown(String keyName) {
		return instance.wnd.getMouseManager().isDown(keyName);
	}

	public static boolean isMouseUp(String keyName) {
		return instance.wnd.getMouseManager().isUp(keyName);
	}

	public static Point getMousePoint() {
		return instance.wnd.getMouseManager().getPoint();
	}

	public static void registKey(String keyName, int keyCode) {
		instance.wnd.getKeyCodeManager().regist(keyName, keyCode);
	}

	public static void removeKey(String keyName) {
		instance.wnd.getKeyCodeManager().remove(keyName);
	}

	public static void registMouse(String keyName, int mouseCode) {
		instance.wnd.getMouseManager().regist(keyName, mouseCode);
	}

	public static void removeMouse(String keyName) {
		instance.wnd.getMouseManager().remove(keyName);
	}

	public static void playBGM(String filePath) {
		instance.sm.play(filePath);
	}

	public static void loopBGM(String filePath) {
		instance.sm.loop(filePath);
	}

	public static void pauseBGM() {
		instance.sm.pause();
	}

	public static void stopBGM() {
		instance.sm.stop();
	}

	public static void playSE(String filePath) {
		instance.sem.play(filePath);
	}

	public static void loopSE(String filePath) {
		instance.sem.loop(filePath);
	}

	public static void stopSE(String filePath) {
		instance.sem.stop(filePath);
	}

	public static void loadSE(String filePath) {
		instance.sem.load(filePath);
	}

	public static Image getImage(String filePath) {
		return instance.im.getImage(filePath);
	}

	public static void loadImage(String filePath) {
		instance.im.load(filePath);
	}

	/**
	 * エラーが発生してもゲームループを続行させようとするかを設定します。
	 * @param isForceGameLoop
	 */
	public static void setForceGameLoop(boolean isForceGameLoop) {
		instance.wnd.setForceGameLoop(isForceGameLoop);
	}

	public static boolean isForceGameLoop() {
		return instance.wnd.isForceGameLoop();
	}

	/**
	 * OSからの再描画要求を無視するかどうか設定します。
	 * @param isOSRepaint
	 */
	public void setIgnoreRepaint(boolean isOSRepaint) {
		instance.wnd.setIgnoreRepaint(isOSRepaint);
	}

	/**
	 * OSからの再描画要求を無視するかどうかを返します。
	 * デフォルトはtrue
	 * @return
	 */
	public boolean getIgnoreRepaint() {
		return instance.wnd.getIgnoreRepaint();
	}

	public static GTKManageable createManageable() {
		return new GTKManageable() {
			private GTKImageManager im;
			private GTKKeyCodeManager kcm;
			private GTKMouseManager mm;
			private GTKPaintFactory pf;
			private GTKFontFactory ff;
			private GTKCanvasSwing canvas;

			@Override
			public void startGameLoop() {
				GTKManager.startGameLoop();
			}

			@Override
			public void stopGameLoop() {
				GTKManager.stopGameLoop();
			}

			@Override
			public SoundManager getSoundManager() {
				return GTKManager.getSoundManager();
			}

			@Override
			public SoundEffectManager getSoundEffectManager() {
				return GTKManager.getSoundEffectManager();
			}

			@Override
			public void setFullScreen(boolean isFullScreen) {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isFullScreen() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void changeView(GTKView view) {
				canvas.changeView(view);
			}

			@Override
			public GTKImageManager getImageManager() {
				return im;
			}

			@Override
			public GTKKeyCodeManager getKeyCodeManager() {
				return kcm;
			}

			@Override
			public APIType getAPIType() {
				return APIType.SWING;
			}

			@Override
			public GTKMouseManager getMouseManager() {
				return mm;
			}


			@Override
			public void start(com.nompor.gtk.GTK.InitParams params) {
				InitParams swingParams = InitParams.create(params.width,params.height);
				canvas = new GTKCanvasSwing();
				swingParams.soundType = params.soundType;
				swingParams.title = params.title;
				swingParams.initView = canvas;
				swingParams.iconPath = params.iconPath;
				swingParams.isWindowShow = false;
				swingParams.isGameLoopStart = false;
				swingParams.isUseImageManager = false;
				swingParams.isUseTransitionManager = false;
				GTKManager.start(swingParams);
				im = GTKImageManager.createInstance(GTKManager.getWindow());
				kcm = GTKKeyCodeManager.createInstance(GTKManager.getKeyCodeManager());
				mm = GTKMouseManager.createInstance(GTKManager.getMouseManager());
				pf = new GTKPaintFactoryAWT();
				ff = new GTKFontFactoryAWT();
				if ( params.onInit != null ) params.onInit.run();
			}

			@Override
			public GTKPaintFactory getPaintFactory() {
				return pf;
			}

			@Override
			public GTKFontFactory getFontFactory() {
				return ff;
			}

			@Override
			public GTKWindow getWindow() {
				return GTKManager.getWindow();
			}

			@Override
			public GTKCanvas getCanvas() {
				return canvas;
			}

			@Override
			public void show() {
				instance.wnd.setVisible(true);
			}
		};
	}
}
