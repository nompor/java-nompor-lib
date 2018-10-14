package com.nompor.gtk.fx;

import java.io.File;

import com.nompor.gtk.APIType;
import com.nompor.gtk.GTKCanvas;
import com.nompor.gtk.GTKException;
import com.nompor.gtk.GTKFontFactory;
import com.nompor.gtk.GTKManageable;
import com.nompor.gtk.GTKPaintFactory;
import com.nompor.gtk.GTKView;
import com.nompor.gtk.GTKWindow;
import com.nompor.gtk.fx.animation.ChangeViewAnimationFX;
import com.nompor.gtk.fx.animation.ChangeViewAnimationsFX;
import com.nompor.gtk.fx.animation.ImageAnimationView;
import com.nompor.gtk.fx.animation.TextAnimationCanvas;
import com.nompor.gtk.fx.animation.TransitionManager;
import com.nompor.gtk.fx.image.ImageManagerFX;
import com.nompor.gtk.fx.input.KeyCodeManagerFX;
import com.nompor.gtk.fx.input.MouseManagerFX;
import com.nompor.gtk.fx.sound.SoundEffectManagerFX;
import com.nompor.gtk.fx.sound.SoundManagerFX;
import com.nompor.gtk.geom.Point;
import com.nompor.gtk.image.GTKImageManager;
import com.nompor.gtk.input.GTKKeyCodeManager;
import com.nompor.gtk.input.GTKMouseManager;
import com.nompor.gtk.sound.SoundEffectManager;
import com.nompor.gtk.sound.SoundManager;
import com.nompor.gtk.sound.SoundType;
import com.nompor.util.LibraryLogger;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class GTKManagerFX {


	public static class InitParams{
		public Stage stage;
		public int width;
		public int height;
		public String title;
		public Image icon;
		public String iconPath;
		public boolean isGameLoopStart=true;
		public boolean isWindowShow=true;
		public GameViewableFX initView;
		public String fullScreenExitHint;
		public KeyCombination fullScreenExitKeyCombination;
		public SoundType soundType = SoundType.MEDIA_PLAYER;
		public SoundType soundEffectType = SoundType.MEDIA_CLIP;
		public boolean isUseImageManager = true;
		public boolean isUseSoundManager = true;
		public boolean isUseSoundEffectManager = true;
		public boolean isUseTransitionManager = true;
		public static InitParams create(Stage stage) {
			InitParams p = new InitParams();
			p.stage = stage;
			p.width = (int) stage.getWidth();
			p.height = (int) stage.getHeight();
			return p;
		}
		public static InitParams create(Stage stage, int width, int height) {
			InitParams p = new InitParams();
			p.stage = stage;
			p.width = width;
			p.height = height;
			return p;
		}
	}

	private static GTKManagerFX instance = null;
	private GameWindowFX wnd;
	private SoundManager sm;
	private SoundEffectManager sem;
	private ImageManagerFX im;
	private TransitionManager trm;
	private boolean isStart;
	private boolean isAWTResolutionUsed;

	private GTKManagerFX() {}

	public static GTKManagerFX getInstance() {
		return instance;
	}
	public static void start(Stage stage) {
		start(stage, (int)stage.getWidth(), (int)stage.getHeight());
	}
	public static void start(Stage stage, int width, int height) {
		start(stage, width, height, null);
	}
	public static void start(Stage stage, int width, int height, GameViewableFX initView) {
		start(stage, null, width, height, initView);
	}
	public static void start(Stage stage, String title, int width, int height, GameViewableFX initView) {
		start(stage, title, (Image)null, width, height, initView);
	}
	public static void start(Stage stage, String title, String iconPath, int width, int height, GameViewableFX initView) {
		start(stage, title, iconPath == null ? null : new Image(new File(iconPath).toURI().toString()), width, height, initView);
	}

	public static void start(Stage stage, String title, Image icon, int width, int height, GameViewableFX initView) {
		InitParams p = InitParams.create(stage, width, height);
		p.title = title;
		p.icon = icon;
		p.initView = initView;
		start(p);
	}

	public static synchronized void start(InitParams param) {
		if ( instance != null ) throw new GTKException(new IllegalStateException("It has already initialized"));
		if ( Platform.isFxApplicationThread() ) {
			init(param);
		} else {
			Platform.startup(()->{
				init(param);
			});
		}
	}
	private static void init(InitParams param) {
		instance = new GTKManagerFX();
		if ( param.isUseSoundManager ) {
			switch(param.soundType) {
			case MEDIA_PLAYER:instance.sm = SoundManagerFX.createMediaInstance();break;
			case MEDIA_CLIP:throw new GTKException("media clip is cannot.");
			case WAVE_STREAMING:instance.sm = SoundManager.createStreamInstance();break;
			case WAVE_CLIP:instance.sm = SoundManagerFX.createClipInstance();break;
			case AUTO:
				instance.sm = SoundManager.createStreamInstance();break;
			default:
				throw new GTKException("soundtype is cannot.");
			}
		}
		if ( param.isUseSoundEffectManager ) {
			switch(param.soundEffectType) {
			case MEDIA_CLIP:
				instance.sem = SoundEffectManagerFX.createMediaInstance();
				break;
			case WAVE_CLIP:
				instance.sem = SoundEffectManager.createInstance();
				break;
			case AUTO:
				instance.sem = SoundEffectManager.createInstance();
				break;
			default:
				throw new GTKException("soundeffecttype is cannot.");
			}
		}
		if ( param.isUseImageManager ) instance.im = ImageManagerFX.createFXInstance();
		instance.wnd = new GameWindowFX(param.stage, param.width, param.height);
		if ( param.isUseTransitionManager ) instance.trm = TransitionManager.create();
		instance.wnd.setTitle(param.title);
		instance.wnd.changeView(param.initView != null ? param.initView : new GameViewFX());
		if ( param.icon != null ) instance.wnd.setIcon(param.icon);
		instance.wnd.setFullScreenExitHint(param.fullScreenExitHint);
		instance.wnd.setFullScreenExitKeyCombination(param.fullScreenExitKeyCombination);
		if ( param.isWindowShow ) instance.wnd.show();
		if ( param.isGameLoopStart ) startGameLoop();
		instance.isStart = true;
	}

	/**
	 * アプリケーション終了処理を実行します。
	 * このメソッドは必要なリソースの解放処理を実行し、Platform.exitを呼び出します。
	 * 特に非推奨メソッドを使用した場合は、このメソッドで終了を実行するようにするか、System.exitで終了処理を実行したほうが安全です。
	 */
	public static void end() {
		try {
			instance.resolutionObjectsRelease(()->{
				Platform.runLater(()->{
					instance.wnd.close();
					Platform.exit();
				});
			});
		}catch(Exception e) {
			LibraryLogger.error(new GTKException());
			System.exit(-1);
		}
	}

	public static double getWidth() {
		return instance.wnd.getViewWidth();
	}

	public static double getHeight() {
		return instance.wnd.getViewHeight();
	}

	public static void setFullScreen(boolean isFullScreen) {
		instance.wnd.setFullScreen(isFullScreen);
	}

	/**
	 * フルスクリーンと最適な解像度の設定を行います。
	 * このメソッドは環境依存する可能性が非常に高いため、通常は利用することは推奨されません。
	 * このメソッドを利用した場合、アプリケーション終了時にresolutionObjectsReleaseを呼び出すか、endを呼び出すか、またはSystem.exitで終了しなければなりません。
	 * @param value
	 */
	@Deprecated
	public static void setFullScreenWithResolution(boolean value) {
		if ( FullScreenControllerFX.getStage() != instance.wnd.getPrimaryStage() ) {
			FullScreenControllerFX.init(instance.wnd);
			FullScreenControllerFX.setAutoChangeEvent(true);
			instance.isAWTResolutionUsed = true;
		}
		if ( value ) {
			FullScreenControllerFX.setFullScreenWithResolution();
		} else {
			FullScreenControllerFX.setUnFullScreenWithResolution();
		}
	}

	/**
	 * フルスクリーンと最適な解像度の初期化を行います。
	 * このメソッドは環境依存する可能性が非常に高いため、通常は利用することは推奨されません。
	 * @param value
	 */
	@Deprecated
	public static void initFullScreenWithResolution(FullScreenControllerFX.ResolutionType type, boolean isAutoChangeEvent) {
		if ( FullScreenControllerFX.getStage() != instance.wnd.getPrimaryStage() ) {
			FullScreenControllerFX.init(instance.wnd, type);
			FullScreenControllerFX.setAutoChangeEvent(isAutoChangeEvent);
			instance.isAWTResolutionUsed = true;
		} else {
			throw new GTKException("Already full screen with resolution init.");
		}
	}

	/**
	 * フルスクリーンと最適な解像度の初期化を行います。
	 * このメソッドは環境依存する可能性が非常に高いため、通常は利用することは推奨されません。
	 * @param value
	 */
	@Deprecated
	public static void initFullScreenWithResolution(boolean isAutoChangeEvent) {
		initFullScreenWithResolution(FullScreenControllerFX.ResolutionType.AWT, isAutoChangeEvent);
	}

	public static boolean isFullScreenWithResolution() {
		return isFullScreen();
	}

	@Deprecated
	public void resolutionObjectsRelease(Runnable releaseCallback) {
		if ( instance.isAWTResolutionUsed ) {
			FullScreenControllerFX.close(releaseCallback);
		} else if (releaseCallback != null) {
			releaseCallback.run();
		}
	}

	public static boolean isFullScreen() {
		return instance.wnd.isFullScreen();
	}

	public static void setAutoUpdateCamera(boolean isAutoUpdateCamera) {
		instance.wnd.setAutoUpdateCamera(isAutoUpdateCamera);
	}

	public static boolean isAutoUpdateCamera(boolean isAutoUpdateCamera) {
		return instance.wnd.isAutoUpdateCamera();
	}

	public static boolean changeView(GameViewableFX view) {
		return instance.wnd.changeView(view, false);
	}

	public static boolean changeViewAnimation(GameViewableFX view, ChangeViewAnimationFX animation) {
		return instance.wnd.changeViewAnimation(view, animation, false);
	}

	public static boolean isStart() {
		return instance != null && instance.isStart;
	}

	/**
	 * 指定した引数の画面へ暗転処理とともに遷移します。
	 * 画面遷移命令を実行できた場合はtrueを返します。
	 * @param view
	 */
	public static boolean changeViewDefaultAnimation(GameViewableFX view) {
		return instance.wnd.changeViewAnimation(view, ChangeViewAnimationsFX.createFadeChangeFillRect(getWidth(), getHeight()), false);
	}

	public static boolean changeView(GameViewableFX view, boolean isAutoResize) {
		return instance.wnd.changeView(view, isAutoResize);
	}

	public static boolean changeViewAnimation(GameViewableFX view, ChangeViewAnimationFX animation, boolean isAutoResize) {
		return instance.wnd.changeViewAnimation(view, animation, isAutoResize);
	}

	/**
	 * 指定した引数の画面へ暗転処理とともに遷移します。
	 * 画面遷移命令を実行できた場合はtrueを返します。
	 * @param view
	 */
	public static boolean changeViewDefaultAnimation(GameViewableFX view, boolean isAutoResize) {
		return instance.wnd.changeViewAnimation(view, ChangeViewAnimationsFX.createFadeChangeFillRect(getWidth(), getHeight()), isAutoResize);
	}

	public static <GV extends Node & GameViewableFX>boolean changeView(GV view) {
		return instance.wnd.changeView(view, false);
	}

	public static <GV extends Node & GameViewableFX>boolean changeViewAnimation(GV view, ChangeViewAnimationFX animation) {
		return instance.wnd.changeViewAnimation(view, animation, false);
	}

	/**
	 * 指定した引数の画面へ暗転処理とともに遷移します。
	 * 画面遷移命令を実行できた場合はtrueを返します。
	 * @param view
	 */
	public static <GV extends Node & GameViewableFX>boolean changeViewDefaultAnimation(GV view) {
		return instance.wnd.changeViewAnimation(view, ChangeViewAnimationsFX.createFadeChangeFillRect(getWidth(), getHeight()), false);
	}

	public static <GV extends Node & GameViewableFX>boolean changeView(GV view, boolean isAutoResize) {
		return instance.wnd.changeView(view, isAutoResize);
	}

	public static <GV extends Node & GameViewableFX>boolean changeViewAnimation(GV view, ChangeViewAnimationFX animation, boolean isAutoResize) {
		return instance.wnd.changeViewAnimation(view, animation, isAutoResize);
	}

	/**
	 * 指定した引数の画面へ暗転処理とともに遷移します。
	 * 画面遷移命令を実行できた場合はtrueを返します。
	 * @param view
	 */
	public static <GV extends Node & GameViewableFX>boolean changeViewDefaultAnimation(GV view, boolean isAutoResize) {
		return instance.wnd.changeViewAnimation(view, ChangeViewAnimationsFX.createFadeChangeFillRect(getWidth(), getHeight()), isAutoResize);
	}

	public static GameWindowFX getWindow() {
		return instance.wnd;
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

	public static SoundManager getSoundManager() {
		return instance.sm;
	}

	public static SoundEffectManager getSoundEffectManager() {
		return instance.sem;
	}

	public static ImageManagerFX getImageManager() {
		return instance.im;
	}

	public static KeyCodeManagerFX getKeyCodeManager() {
		return instance.wnd.getKeyCodeManager();
	}

	public static boolean isKeyPress(Object key) {
		return instance.wnd.getKeyCodeManager().isPress(key);
	}

	public static boolean isKeyRelease(Object key) {
		return instance.wnd.getKeyCodeManager().isRelease(key);
	}

	public static boolean isKeyDown(Object key) {
		return instance.wnd.getKeyCodeManager().isDown(key);
	}

	public static boolean isKeyUp(Object key) {
		return instance.wnd.getKeyCodeManager().isUp(key);
	}

	public static MouseManagerFX getMouseManager() {
		return instance.wnd.getMouseManager();
	}

	public static boolean isMousePress(Object key) {
		return instance.wnd.getMouseManager().isPress(key);
	}

	public static boolean isMouseRelease(Object key) {
		return instance.wnd.getMouseManager().isRelease(key);
	}

	public static boolean isMouseDown(Object key) {
		return instance.wnd.getMouseManager().isDown(key);
	}

	public static boolean isMouseUp(Object key) {
		return instance.wnd.getMouseManager().isUp(key);
	}

	public static Point getMousePoint() {
		return instance.wnd.getMouseManager().getPoint();
	}

	public static void registKey(Object key, KeyCode keyCode) {
		instance.wnd.getKeyCodeManager().regist(key, keyCode);
	}

	public static void registKey(KeyCode keyCode) {
		instance.wnd.getKeyCodeManager().regist(keyCode);
	}

	public static void removeKey(Object key) {
		instance.wnd.getKeyCodeManager().remove(key);
	}

	public static void registMouse(Object key, MouseButton mouseCode) {
		instance.wnd.getMouseManager().regist(key, mouseCode);
	}

	public static void registMouse(MouseButton mouseCode) {
		instance.wnd.getMouseManager().regist(mouseCode);
	}

	public static void removeMouse(Object key) {
		instance.wnd.getMouseManager().remove(key);
	}

	public static void clearKey() {
		instance.wnd.getKeyCodeManager().clear();
	}

	public static void clearMouse() {
		instance.wnd.getMouseManager().clear();
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

	public static GameCameraFX getGameCamera() {
		return instance.wnd.getGameCamera();
	}

	public static void setGameCamera(GameCameraFX camera) {
		instance.wnd.setGameCamera(camera);
	}

	public static final EventHandler<WindowEvent> getOnCloseRequest() {
		return instance.wnd.getOnCloseRequest();
	}

	public static final EventHandler<WindowEvent> getOnHidden() {
		return instance.wnd.getOnHidden();
	}

	public static final EventHandler<WindowEvent> getOnHiding() {
		return instance.wnd.getOnHiding();
	}

	public static final EventHandler<WindowEvent> getOnShowing() {
		return instance.wnd.getOnShowing();
	}

	public static final EventHandler<WindowEvent> getOnShown() {
		return instance.wnd.getOnShown();
	}

	public static final void setOnCloseRequest(EventHandler<WindowEvent> arg0) {
		instance.wnd.setOnCloseRequest(arg0);
	}

	public static final void setOnHidden(EventHandler<WindowEvent> arg0) {
		instance.wnd.setOnHidden(arg0);
	}

	public static final void setOnHiding(EventHandler<WindowEvent> arg0) {
		instance.wnd.setOnHiding(arg0);
	}

	public static final void setOnShowing(EventHandler<WindowEvent> arg0) {
		instance.wnd.setOnShowing(arg0);
	}

	public static final void setOnShown(EventHandler<WindowEvent> arg0) {
		instance.wnd.setOnShown(arg0);
	}

	public static TransitionManager getTransitionManager() {
		return instance.trm;
	}

	public static SequentialTransition createSequentialTransition() {
		return instance.trm.createSequentialTransition();
	}

	public static SequentialTransition createSequentialTransition(Animation... args) {
		return instance.trm.createSequentialTransition(args);
	}

	public static ParallelTransition createParallelTransition() {
		return instance.trm.createParallelTransition();
	}

	public static ParallelTransition createParallelTransition(Animation... args) {
		return instance.trm.createParallelTransition(args);
	}

	public static TranslateTransition createTranslateTransition() {
		return instance.trm.createTranslateTransition();
	}

	public static TranslateTransition createTranslateTransition(Duration dur, Node node) {
		return instance.trm.createTranslateTransition(dur, node);
	}

	public static FadeTransition createFadeTransition() {
		return instance.trm.createFadeTransition();
	}

	public static FadeTransition createFadeTransition(Duration dur, Node node) {
		return instance.trm.createFadeTransition(dur, node);
	}

	public static RotateTransition createRotateTransition() {
		return instance.trm.createRotateTransition();
	}

	public static RotateTransition createRotateTransition(Duration dur, Node node) {
		return instance.trm.createRotateTransition(dur, node);
	}

	public static StrokeTransition createStrokeTransition() {
		return instance.trm.createStrokeTransition();
	}

	public static StrokeTransition createStrokeTransition(Duration dur, Shape node) {
		return instance.trm.createStrokeTransition(dur, node);
	}

	public static FillTransition createFillTransition() {
		return instance.trm.createFillTransition();
	}

	public static FillTransition createFillTransition(Duration dur, Shape node) {
		return instance.trm.createFillTransition(dur, node);
	}

	public static PathTransition createPathTransition() {
		return instance.trm.createPathTransition();
	}

	public static PathTransition createPathTransition(Duration dur, Shape node) {
		return instance.trm.createPathTransition(dur, node);
	}

	public static ScaleTransition createScaleTransition() {
		return instance.trm.createScaleTransition();
	}

	public static ScaleTransition createScaleTransition(Duration dur, Node node) {
		return instance.trm.createScaleTransition(dur, node);
	}

	public static PauseTransition createPauseTransition() {
		return instance.trm.createPauseTransition();
	}

	public static PauseTransition createPauseTransition(Duration dur) {
		return instance.trm.createPauseTransition(dur);
	}

	public static Timeline createTimeline() {
		return instance.trm.createTimeline();
	}

	public static Timeline createTimeline(KeyFrame... keyFrames) {
		return instance.trm.createTimeline(keyFrames);
	}

	public static ImageAnimationView createImageAnimationView(Duration dur, Image img, int width, int height) {
		return instance.trm.createImageAnimationView(dur, img, width, height);
	}

	public static TextAnimationCanvas createTextAnimationCanvas(Duration dur, String text, double x, double y, double width,
			double height, double ox, double oy) {
		return instance.trm.createTextAnimationCanvas(dur, text, x, y, width, height, ox, oy);
	}

	public static void animationClear() {
		instance.trm.clear();
	}


	public static EventHandler<MouseEvent> getOnMousePressed() {
		return instance.wnd.getOnMousePressed();
	}

	public static void setOnMousePressed(EventHandler<MouseEvent> onMousePressed) {
		instance.wnd.setOnMousePressed(onMousePressed);
	}

	public static EventHandler<MouseEvent> getOnMouseReleased() {
		return instance.wnd.getOnMouseReleased();
	}

	public static void setOnMouseReleased(EventHandler<MouseEvent> onMouseReleased) {
		instance.wnd.setOnMouseReleased(onMouseReleased);
	}

	public static EventHandler<MouseEvent> getOnMouseClicked() {
		return instance.wnd.getOnMouseClicked();
	}

	public static void setOnMouseClicked(EventHandler<MouseEvent> onMouseClicked) {
		instance.wnd.setOnMouseClicked(onMouseClicked);
	}

	public static EventHandler<MouseEvent> getOnMouseMoved() {
		return instance.wnd.getOnMouseMoved();
	}

	public static void setOnMouseMoved(EventHandler<MouseEvent> onMouseMoved) {
		instance.wnd.setOnMouseMoved(onMouseMoved);
	}

	public static EventHandler<MouseEvent> getOnMouseDragged() {
		return instance.wnd.getOnMouseDragged();
	}

	public static void setOnMouseDragged(EventHandler<MouseEvent> onMouseDragged) {
		instance.wnd.setOnMouseDragged(onMouseDragged);
	}

	public static EventHandler<KeyEvent> getOnKeyPressed() {
		return instance.wnd.getOnKeyPressed();
	}

	public static void setOnKeyPressed(EventHandler<KeyEvent> onKeyPressed) {
		instance.wnd.setOnKeyPressed(onKeyPressed);
	}

	public static EventHandler<KeyEvent> getOnKeyReleased() {
		return instance.wnd.getOnKeyReleased();
	}

	public static void setOnKeyReleased(EventHandler<KeyEvent> onKeyReleased) {
		instance.wnd.setOnKeyReleased(onKeyReleased);
	}

	public static EventHandler<KeyEvent> getOnKeyTyped() {
		return instance.wnd.getOnKeyTyped();
	}

	public static void setOnKeyTyped(EventHandler<KeyEvent> onKeyTyped) {
		instance.wnd.setOnKeyTyped(onKeyTyped);
	}

	public static final String getFullScreenExitHint() {
		return instance.wnd.getFullScreenExitHint();
	}

	public static final KeyCombination getFullScreenExitKeyCombination() {
		return instance.wnd.getFullScreenExitKeyCombination();
	}

	public static final void setFullScreenExitHint(String arg0) {
		instance.wnd.setFullScreenExitHint(arg0);
	}

	public static final void setFullScreenExitKeyCombination(KeyCombination arg0) {
		instance.wnd.setFullScreenExitKeyCombination(arg0);
	}

	public static final GTKManageable createManageable() {
		return new GTKManageable() {
			private GTKImageManager im;
			private GTKKeyCodeManager kcm;
			private GTKMouseManager mm;
			private GTKPaintFactory pf;
			private GTKFontFactory ff;
			private GTKCanvasFX canvas;

			@Override
			public void startGameLoop() {
				GTKManagerFX.startGameLoop();
			}

			@Override
			public void stopGameLoop() {
				GTKManagerFX.stopGameLoop();
			}

			@Override
			public SoundManager getSoundManager() {
				return GTKManagerFX.getSoundManager();
			}

			@Override
			public SoundEffectManager getSoundEffectManager() {
				return GTKManagerFX.getSoundEffectManager();
			}

			@Override
			public void setFullScreen(boolean isFullScreen) {
				GTKManagerFX.setFullScreenWithResolution(isFullScreen);
			}

			@Override
			public boolean isFullScreen() {
				return GTKManagerFX.isFullScreenWithResolution();
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
				return APIType.FX;
			}

			@Override
			public GTKMouseManager getMouseManager() {
				return mm;
			}

			@Override
			public void start(com.nompor.gtk.GTK.InitParams params) {
				if ( Platform.isFxApplicationThread() ) {
					init(params);
				} else {
					Platform.startup(()->{
						init(params);
					});
				}
			}

			private void init(com.nompor.gtk.GTK.InitParams params) {
				InitParams fxParams = InitParams.create(params.stage,params.width,params.height);
				canvas = new GTKCanvasFX(params.width,params.height);
				fxParams.soundType = params.soundType;
				fxParams.title = params.title;
				fxParams.initView = canvas;
				fxParams.iconPath = params.iconPath;
				fxParams.isWindowShow = false;
				fxParams.isGameLoopStart = false;
				fxParams.isUseImageManager = false;
				fxParams.isUseTransitionManager = false;
				GTKManagerFX.start(fxParams);
				im = GTKImageManager.createFXInstance();
				kcm = GTKKeyCodeManager.createFXInstance(GTKManagerFX.getKeyCodeManager());
				mm = GTKMouseManager.createFXInstance(GTKManagerFX.getMouseManager());
				pf = new GTKPaintFactoryFX();
				ff = new GTKFontFactoryFX();
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
				return GTKManagerFX.getWindow();
			}

			@Override
			public GTKCanvas getCanvas() {
				return canvas;
			}

			@Override
			public void show() {
				instance.wnd.show();
			}
		};
	}
}
