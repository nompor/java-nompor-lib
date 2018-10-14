package com.nompor.gtk.fx.event;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventHandler;

public final class MultiEventHandler<E extends Event> extends ArrayList<EventHandler<E>> implements EventHandler<E> {
	@Override
	public void handle(E arg0) {
		stream().forEach(e -> e.handle(arg0));
	}
}
