package com.nompor.gtk.fx.event;

import javafx.event.EventTarget;
import javafx.scene.canvas.GraphicsContext;

public class GameDrawEvent extends GameEvent {

	public GameDrawEvent(Object source, EventTarget tgt) {
		super(source, tgt);
	}
	public GameDrawEvent() {
		super();
	}
	public void setGraphics(GraphicsContext g) {
		super.source = g;
	}
	public GraphicsContext getGraphics() {
		return (GraphicsContext) super.source;
	}
}
