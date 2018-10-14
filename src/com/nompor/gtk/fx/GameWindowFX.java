package com.nompor.gtk.fx;

import java.util.LinkedList;

import com.nompor.gtk.GTKException;
import com.nompor.gtk.GTKWindow;
import com.nompor.gtk.fx.animation.ChangeViewAnimationFX;
import com.nompor.gtk.fx.input.KeyCodeManagerFX;
import com.nompor.gtk.fx.input.MouseManagerFX;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameWindowFX implements GTKWindow{

	private Stage wnd;
	private Scene scene;
	private GameCameraFX camera;
	private Group root = new Group();
	private GameViewableFX current;
	private boolean isChangeAnimation;
	private boolean isAutoUpdateCamera = true;
	private KeyCodeManagerFX keyManager = new KeyCodeManagerFX();
	private MouseManagerFX mouseManager = new MouseManagerFX();
	private EventHandler<MouseEvent> onMousePressed;
	private EventHandler<MouseEvent> onMouseReleased;
	private EventHandler<MouseEvent> onMouseClicked;
	private EventHandler<MouseEvent> onMouseMoved;
	private EventHandler<MouseEvent> onMouseDragged;
	private EventHandler<KeyEvent> onKeyPressed;
	private EventHandler<KeyEvent> onKeyReleased;
	private EventHandler<KeyEvent> onKeyTyped;
	private double width;
	private double height;
	private AnimationTimer at = new AnimationTimer() {
		@Override
		public void handle(long now) {
			keyManager.update();
			mouseManager.update();
			current.process();
			if ( isAutoUpdateCamera ) {
				if ( camera != null ) camera.update();
				current.updateCamera();
			}
			current.draw();
		}
	};

	/**
	 * キーコードフラグ管理クラスを取得します。
	 * @return
	 */
	public KeyCodeManagerFX getKeyCodeManager() {
		return keyManager;
	}
	/**
	 * ゲーム用ウィンドウとして初期化します。
	 * @param width ウィンドウの表示領域の横幅
	 * @param height ウィンドウの表示領域の縦幅
	 * @param initView 初期表示対象の画面
	 * @return
	 */
	public GameWindowFX(double width, double height) {
		this(null, width, height);
	}

	/**
	 * ゲーム用ウィンドウとして初期化します。
	 * @param stage ウィンドウオブジェクト
	 * @param width ウィンドウの表示領域の横幅
	 * @param height ウィンドウの表示領域の縦幅
	 * @param initView 初期表示対象の画面
	 * @return
	 */
	public GameWindowFX(Stage stage, double width, double height) {
		this.width = width;
		this.height = height;
		if ( stage == null ) {
			wnd = new Stage();
		} else {
			wnd = stage;
		}
		init();
	}
	private void init() {
		scene = new Scene(root, width, height);
		wnd.setScene(scene);
		wnd.setResizable(false);
		scene.setOnMouseClicked(e -> {
			if ( onMouseClicked != null ) onMouseClicked.handle(e);
			current.mouseClicked(e);
		});
		scene.setOnMousePressed(e -> {
			if ( onMousePressed != null ) onMousePressed.handle(e);
			mouseManager.position(e.getX(), e.getY());
			mouseManager.pressed(e.getButton());
			current.mousePressed(e);
		});
		scene.setOnMouseReleased(e -> {
			if ( onMouseReleased != null ) onMouseReleased.handle(e);
			mouseManager.position(e.getX(), e.getY());
			mouseManager.released(e.getButton());
			current.mouseReleased(e);
		});
		scene.setOnMouseDragged(e -> {
			if ( onMouseDragged != null ) onMouseDragged.handle(e);
			mouseManager.position(e.getX(), e.getY());
			current.mouseDragged(e);
		});
		scene.setOnMouseMoved(e -> {
			if ( onMouseMoved != null ) onMouseMoved.handle(e);
			mouseManager.position(e.getX(), e.getY());
			current.mouseMoved(e);
		});
		scene.setOnKeyPressed(e -> {
			if ( onKeyPressed != null ) onKeyPressed.handle(e);
			keyManager.pressed(e.getCode());
			current.keyPressed(e);
		});
		scene.setOnKeyReleased(e -> {
			if ( onKeyReleased != null ) onKeyReleased.handle(e);
			keyManager.released(e.getCode());
			current.keyReleased(e);
		});
		scene.setOnKeyTyped(e -> {
			if ( onKeyTyped != null ) onKeyTyped.handle(e);
			current.keyTyped(e);
		});
	}

	public double getDWidth() {
		return width;
	}

	public double getDHeight() {
		return height;
	}

	public Scene getScene() {
		return scene;
	}

	public Stage getPrimaryStage() {
		return wnd;
	}

	public Group getRoot() {
		return root;
	}

	/**
	 * ウィンドウを表示します。
	 */
	public synchronized void show() {
		if ( !wnd.isShowing() ) {
			wnd.show();
		}
	}

	/**
	 * ウィンドウを閉じます。
	 */
	public synchronized void close() {
		if ( wnd.isShowing() ) {
			wnd.close();
		}
	}

	/**
	 * ゲームループを開始します。
	 * ゲームループの処理はGameViewFXクラスのprocessメソッドを定期的に呼び出すように処理します。
	 */
	public synchronized void startGameLoop() {
		at.start();
	}

	/**
	 * ゲームループを終了します。
	 */
	public synchronized void stopGameLoop() {
		at.stop();
	}

	/**
	 * ウィンドウタイトルを変更します。
	 * @param title
	 */
	public void setTitle(String title) {
		wnd.setTitle(title);
	}

	/**
	 * ウィンドウアイコンを設定します。
	 * @param img
	 */
	public void setIcon(Image img) {
		wnd.getIcons().add(img);
	}

	/**
	 * ウィンドウのリサイズを可能にするかどうか設定します。
	 * @param isResizable
	 */
	public void setResizable(boolean isResizable) {
		wnd.setResizable(isResizable);
	}

	/**
	 * フルスクリーンにするかどうか設定します。
	 * @param value
	 */
	public void setFullScreen(boolean value) {
		wnd.setFullScreen(value);
	}

	/**
	 * フルスクリーンかどうか返します。
	 */
	public boolean isFullScreen() {
		return wnd.isFullScreen();
	}

	/**
	 * 指定した引数の画面へアニメーション処理とともに遷移します。
	 * 画面遷移命令を実行できた場合はtrueを返します。
	 * @param view
	 */
	public synchronized <GV extends Node & GameViewableFX> boolean changeViewAnimation(GV view, ChangeViewAnimationFX animation) {
		return changeViewAnimation(view, animation, false);
	}
	/**
	 * 指定した引数の画面へアニメーション処理とともに遷移します。
	 * 画面遷移命令を実行できた場合はtrueを返します。
	 * @param view
	 * @param isAutoResize 表示ノードをできるだけウィンドウ領域に合わせるかどうか
	 */
	public synchronized <GV extends Node & GameViewableFX> boolean changeViewAnimation(GV view, ChangeViewAnimationFX animation, boolean isAutoResize) {
		if ( isChangeAnimation ) return false;
		if ( isAutoResize ) _autoResize(view);
		root.getChildren().add(animation);

		animation.addChangeInOnFinished(e -> {
			_change(view);
		});
		animation.addChangeOutOnFinished(e -> {
			root.getChildren().remove(animation);
			isChangeAnimation = false;
		});
		isChangeAnimation = true;
		animation.start();
		return true;
	}

	/**
	 * 指定した引数の画面へ遷移します。
	 * 画面遷移に成功した場合はtrueを返します。
	 * @param view
	 */
	public synchronized  <GV extends Node & GameViewableFX> boolean changeView(GV view) {
		return changeView(view, false);
	}

	/**
	 * 指定した引数の画面へ遷移します。
	 * 画面遷移に成功した場合はtrueを返します。
	 * @param view
	 * @param isAutoResize 表示ノードをできるだけウィンドウ領域に合わせるかどうか
	 */
	public synchronized  <GV extends Node & GameViewableFX> boolean changeView(GV view, boolean isAutoResize) {
		if ( isChangeAnimation ) return false;
		if ( isAutoResize ) _autoResize(view);
		_change(view);
		return true;
	}

	/**
	 * 指定した引数の画面へアニメーション処理とともに遷移します。
	 * 画面遷移命令を実行できた場合はtrueを返します。
	 * @param view
	 */
	public synchronized boolean changeViewAnimation(GameViewableFX view, ChangeViewAnimationFX animation) {
		return changeViewAnimation(view, animation, false);
	}
	/**
	 * 指定した引数の画面へアニメーション処理とともに遷移します。
	 * 画面遷移命令を実行できた場合はtrueを返します。
	 * @param view
	 * @param isAutoResize 表示ノードをできるだけウィンドウ領域に合わせるかどうか
	 */
	public synchronized boolean changeViewAnimation(GameViewableFX view, ChangeViewAnimationFX animation, boolean isAutoResize) {
		if ( !(view instanceof Node) ) {
			throw new GTKException(new IllegalArgumentException("View have to FX Node"));
		}
		return changeViewAnimation((Node & GameViewableFX)view, animation, isAutoResize);
	}

	/**
	 * 指定した引数の画面へ遷移します。
	 * 画面遷移に成功した場合はtrueを返します。
	 * @param view
	 */
	public synchronized boolean changeView(GameViewableFX view) {
		return changeView(view, false);
	}

	/**
	 * 指定した引数の画面へ遷移します。
	 * 画面遷移に成功した場合はtrueを返します。
	 * @param view
	 * @param isAutoResize 表示ノードをできるだけウィンドウ領域に合わせるかどうか
	 */
	public synchronized boolean changeView(GameViewableFX view, boolean isAutoResize) {
		if ( !(view instanceof Node) ) {
			throw new GTKException(new IllegalArgumentException("View have to FX Node"));
		}
		return changeView((Node & GameViewableFX)view, isAutoResize);
	}

	@SuppressWarnings("unlikely-arg-type")
	private <GV extends Node & GameViewableFX> void _change(GV view) {
		if ( current != null ) current.stop();
		root.getChildren().remove(current);
		if ( current != null ) current.end();
		view.ready();
		root.getChildren().add(0, view);
		current = view;
		view.start();
	}

	private void _autoResize(Node node) {
		LinkedList<Node> checkTarget = new LinkedList<>();
		checkTarget.addLast(node);
		while(!checkTarget.isEmpty()) {
			Node n = checkTarget.removeFirst();
			if ( n instanceof Group ) {
				Group g = (Group)n;
				for(Node n2 : g.getChildren()) {
					checkTarget.addLast(n2);
				}
			} else if ( n instanceof GameSceneFX ) {
				GameSceneFX gs = (GameSceneFX)n;
				for(Node n2 : gs.getChildren()) {
					checkTarget.addLast(n2);
				}
				gs.setWidth(scene.getWidth());
				gs.setHeight(scene.getHeight());
			} else if ( n instanceof GameCanvasFX ) {
				GameCanvasFX c = (GameCanvasFX)n;
				c.setWidth(scene.getWidth());
				c.setHeight(scene.getHeight());
			} else if ( n instanceof DefaultViewFX ) {
				DefaultViewFX c = (DefaultViewFX)n;
				c.setWidth(scene.getWidth());
				c.setHeight(scene.getHeight());
			}
		}
	}

	public MouseManagerFX getMouseManager() {
		return mouseManager;
	}

	public boolean isAutoUpdateCamera() {
		return isAutoUpdateCamera;
	}

	public void setAutoUpdateCamera(boolean isAutoUpdateCamera) {
		this.isAutoUpdateCamera = isAutoUpdateCamera;
	}

	public GameCameraFX getGameCamera() {
		return camera;
	}

	public void setGameCamera(GameCameraFX camera) {
		this.camera = camera;
		scene.setCamera(camera == null ? null : camera.getCamera());
	}

	public final EventHandler<WindowEvent> getOnCloseRequest() {
		return wnd.getOnCloseRequest();
	}

	public final EventHandler<WindowEvent> getOnHidden() {
		return wnd.getOnHidden();
	}

	public final EventHandler<WindowEvent> getOnHiding() {
		return wnd.getOnHiding();
	}

	public final EventHandler<WindowEvent> getOnShowing() {
		return wnd.getOnShowing();
	}

	public final EventHandler<WindowEvent> getOnShown() {
		return wnd.getOnShown();
	}

	public final void setOnCloseRequest(EventHandler<WindowEvent> arg0) {
		wnd.setOnCloseRequest(arg0);
	}

	public final void setOnHidden(EventHandler<WindowEvent> arg0) {
		wnd.setOnHidden(arg0);
	}

	public final void setOnHiding(EventHandler<WindowEvent> arg0) {
		wnd.setOnHiding(arg0);
	}

	public final void setOnShowing(EventHandler<WindowEvent> arg0) {
		wnd.setOnShowing(arg0);
	}

	public final void setOnShown(EventHandler<WindowEvent> arg0) {
		wnd.setOnShown(arg0);
	}

	public EventHandler<MouseEvent> getOnMousePressed() {
		return onMousePressed;
	}

	public void setOnMousePressed(EventHandler<MouseEvent> onMousePressed) {
		this.onMousePressed = onMousePressed;
	}

	public EventHandler<MouseEvent> getOnMouseReleased() {
		return onMouseReleased;
	}

	public void setOnMouseReleased(EventHandler<MouseEvent> onMouseReleased) {
		this.onMouseReleased = onMouseReleased;
	}

	public EventHandler<MouseEvent> getOnMouseClicked() {
		return onMouseClicked;
	}

	public void setOnMouseClicked(EventHandler<MouseEvent> onMouseClicked) {
		this.onMouseClicked = onMouseClicked;
	}

	public EventHandler<MouseEvent> getOnMouseMoved() {
		return onMouseMoved;
	}

	public void setOnMouseMoved(EventHandler<MouseEvent> onMouseMoved) {
		this.onMouseMoved = onMouseMoved;
	}

	public EventHandler<MouseEvent> getOnMouseDragged() {
		return onMouseDragged;
	}

	public void setOnMouseDragged(EventHandler<MouseEvent> onMouseDragged) {
		this.onMouseDragged = onMouseDragged;
	}

	public EventHandler<KeyEvent> getOnKeyPressed() {
		return onKeyPressed;
	}

	public void setOnKeyPressed(EventHandler<KeyEvent> onKeyPressed) {
		this.onKeyPressed = onKeyPressed;
	}

	public EventHandler<KeyEvent> getOnKeyReleased() {
		return onKeyReleased;
	}

	public void setOnKeyReleased(EventHandler<KeyEvent> onKeyReleased) {
		this.onKeyReleased = onKeyReleased;
	}

	public EventHandler<KeyEvent> getOnKeyTyped() {
		return onKeyTyped;
	}

	public void setOnKeyTyped(EventHandler<KeyEvent> onKeyTyped) {
		this.onKeyTyped = onKeyTyped;
	}

	public final String getFullScreenExitHint() {
		return wnd.getFullScreenExitHint();
	}

	public final KeyCombination getFullScreenExitKeyCombination() {
		return wnd.getFullScreenExitKeyCombination();
	}

	public final void setFullScreenExitHint(String arg0) {
		wnd.setFullScreenExitHint(arg0);
	}

	public final void setFullScreenExitKeyCombination(KeyCombination arg0) {
		wnd.setFullScreenExitKeyCombination(arg0);
	}

	@Override
	public int getWidth() {
		return (int) wnd.getWidth();
	}
	@Override
	public int getHeight() {
		return (int) wnd.getHeight();
	}
	@Override
	public int getViewWidth() {
		return (int) width;
	}
	@Override
	public int getViewHeight() {
		return (int) height;
	}
}