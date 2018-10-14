package com.nompor.gtk.fx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.nompor.gtk.fx.event.GameEvent;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * 各種画面クラスに伝番イベントを子のGameViewableオブジェクトに伝える機能を有する
 * @author nompor
 *
 */
public class GameViewGroupFX extends GameViewFX {

	private ArrayList<GameViewableFX> views = new ArrayList<>();
	private GameEvent processsGameEvent = new GameEvent(this, this);

	public GameViewGroupFX(Node... viewList) {
		ObservableList<Node> obsList = getChildren();
		obsList.addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(Change<? extends Node> e) {
				while(e.next()) {
					if(e.wasAdded()) {
						List<? extends Node> list = e.getAddedSubList();
						list.stream().filter(e2 -> e2 instanceof GameViewableFX).forEach(e2 -> views.add((GameViewableFX)e2));
					}
					if (e.wasRemoved() || e.wasReplaced()) {
						List<? extends Node> list = e.getAddedSubList();
						views.removeAll(list.stream().filter(e2 -> e2 instanceof GameViewableFX).map(e2 -> (GameViewableFX)e2).collect(Collectors.toList()));
					}
				}
			}
		});
		for ( Node view : viewList ) obsList.add(view);
	}
	public GameViewGroupFX() {this(new Node[0]);}

	public Stream<GameViewableFX> gameViewStream(){
		return views.stream();
	}

	public void updateCamera() {gameViewStream().forEach(v->v.updateCamera());}
	public void process() {
		if ( onProcessProperty != null ) onProcessProperty.get().handle(processsGameEvent);
		gameViewStream().forEach(v->v.process());
	}
	public void draw() {gameViewStream().forEach(v->v.draw());}
	public void mouseDragged(MouseEvent e) {gameViewStream().forEach(v->v.mouseDragged(e));}
	public void mouseMoved(MouseEvent e) {gameViewStream().forEach(v->v.mouseMoved(e));}
	public void mouseClicked(MouseEvent e) {gameViewStream().forEach(v->v.mouseClicked(e));}
	public void mousePressed(MouseEvent e) {gameViewStream().forEach(v->v.mousePressed(e));}
	public void mouseReleased(MouseEvent e) {gameViewStream().forEach(v->v.mouseReleased(e));}
	public void keyTyped(KeyEvent e) {gameViewStream().forEach(v->v.keyTyped(e));}
	public void keyPressed(KeyEvent e) {gameViewStream().forEach(v->v.keyPressed(e));}
	public void keyReleased(KeyEvent e) {gameViewStream().forEach(v->v.keyReleased(e));}
	public void start() {gameViewStream().forEach(v->v.start());}
	public void end() {gameViewStream().forEach(v->v.end());}
	public void ready() {gameViewStream().forEach(v->v.ready());}
	public void stop() {gameViewStream().forEach(v->v.stop());}
	public void changeInStart() {gameViewStream().forEach(v->v.changeInStart());}
	public void changeInEnd() {gameViewStream().forEach(v->v.changeInEnd());}
	public void changeOutStart() {gameViewStream().forEach(v->v.changeOutStart());}
	public void changeOutEnd() {gameViewStream().forEach(v->v.changeOutEnd());}

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
