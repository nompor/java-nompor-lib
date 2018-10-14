package com.nompor.gtk.fx.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class GameEvent extends Event {

	public GameEvent(Object source, EventTarget tgt) {
		super(source, tgt, EventType.ROOT);
	}
	public GameEvent() {
		super(EventType.ROOT);
	}
}
