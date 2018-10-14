package com.nompor.gtk.fx;


import com.nompor.gtk.fx.event.GameEvent;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;

/**
 * 基本ゲーム画面クラス
 * 各種伝番イベントを利用してゲームの処理を記述する
 * @author nompor
 *
 */
public class GameViewFX extends Group implements GameViewableFX{

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
}