package com.nompor.gtk.fx;

import com.nompor.gtk.fx.event.GameDrawEvent;
import com.nompor.gtk.fx.event.GameEvent;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * キャンバス描画用ゲーム画面クラス
 * @author nompor
 *
 */
public class GameCanvasFX extends Canvas implements GameViewableFX {
	public GameCanvasFX(double width, double height) {
		super(width, height);
	}
	@Deprecated
	public GameCanvasFX() {
		this(0,0);
	}
	public GraphicsContext getGraphics() {
		return getGraphicsContext2D();
	}
	@Override
	public final void draw() {draw(getGraphics());}

	public void setSize(double width, double height) {
		setWidth(width);
		setHeight(height);
	}


	private GameEvent processsGameEvent = new GameEvent(this, this);

	@Override
	public void process() {
		if ( onProcessProperty != null ) onProcessProperty.get().handle(processsGameEvent);
	}

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



	private GameDrawEvent drawGameEvent = new GameDrawEvent(null, this);

	public void draw(GraphicsContext g) {
		drawGameEvent.setGraphics(g);
		if ( onDrawProperty != null ) onDrawProperty.get().handle(drawGameEvent);
	}

	/**
	 * ゲームのメイン描画を実装するためのイベント
	 */
	private ObjectProperty<EventHandler<GameDrawEvent>> onDrawProperty;
	public ObjectProperty<EventHandler<GameDrawEvent>> onDrawProperty() {
		return onDrawProperty;
	}
	public void setOnDraw(EventHandler<GameDrawEvent> handle) {
		if ( this.onDrawProperty == null ) {
			onDrawProperty = new SimpleObjectProperty<EventHandler<GameDrawEvent>>();
		}
		onDrawProperty.set(handle);
	}
	public EventHandler<GameDrawEvent> getOnDraw() {
		return onDrawProperty == null ? null : onDrawProperty.get();
	}
}
