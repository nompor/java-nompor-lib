package com.nompor.gtk.fx;


import com.nompor.gtk.fx.event.GameEvent;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * カメラ付きのゲーム画面を表すクラス
 * 各種イベントはベースとなるGameViewFXオブジェクトに伝播する
 * @author nompor
 *
 */
public class GameSceneFX extends SubScene implements GameViewableFX{
	private GameViewFX grp;
	private GameEvent processsGameEvent = new GameEvent(this, this);
	private GameCameraFX camera;
	public GameSceneFX(double width, double height) {
		super(new GameViewFX(), width, height);
		grp = (GameViewFX) getRoot();
	}
	public GameSceneFX(GameViewFX grp, double width, double height) {
		super(grp, width, height);
		this.grp = (GameViewFX) getRoot();
	}
	public void setSize(double width, double height) {
		setWidth(width);
		setHeight(height);
	}
	public void setGameCamera(GameCameraFX camera) {
		this.camera = camera;
		setCamera(camera == null ? null : camera.getCamera());
	}
	public GameCameraFX getGameCamera() {
		return camera;
	}
	public void updateCamera() {
		if (camera != null) {
			camera.update();
		}
		grp.updateCamera();
	}

	public ObservableList<Node> getChildren(){
		return grp.getChildren();
	}

	public void process() {
		if ( onProcessProperty != null ) onProcessProperty.get().handle(processsGameEvent);
		grp.process();
	}
	public void draw() {grp.draw();}
	public void mouseDragged(MouseEvent e) {grp.mouseDragged(e);}
	public void mouseMoved(MouseEvent e) {grp.mouseMoved(e);}
	public void mouseClicked(MouseEvent e) {grp.mouseClicked(e);}
	public void mousePressed(MouseEvent e) {grp.mousePressed(e);}
	public void mouseReleased(MouseEvent e) {grp.mouseReleased(e);}
	public void keyTyped(KeyEvent e) {grp.keyTyped(e);}
	public void keyPressed(KeyEvent e) {grp.keyPressed(e);}
	public void keyReleased(KeyEvent e) {grp.keyReleased(e);}
	public void start() {grp.start();}
	public void end() {grp.end();}
	public void ready() {grp.ready();}
	public void stop() {grp.stop();}
	public void changeInStart() {grp.changeInStart();}
	public void changeInEnd() {grp.changeInEnd();}
	public void changeOutStart() {grp.changeOutStart();}
	public void changeOutEnd() {grp.changeOutEnd();}


	/**
	 * ゲームのメイン処理を実装するためのイベント
	 */
	private ObjectProperty<EventHandler<GameEvent>> onProcessProperty;
	public ObjectProperty<EventHandler<GameEvent>> onProcessProperty() {
		return onProcessProperty;
	}
	public void setOnProcess(EventHandler<GameEvent> handle) {
		if ( this.onProcessProperty == null ) {
			onProcessProperty = new SimpleObjectProperty<EventHandler<GameEvent>>();
		}
		onProcessProperty.set(handle);;
	}
	public EventHandler<GameEvent> getOnProcess() {
		return onProcessProperty == null ? null : onProcessProperty.get();
	}
}