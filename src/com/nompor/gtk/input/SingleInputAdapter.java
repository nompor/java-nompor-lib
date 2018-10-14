package com.nompor.gtk.input;

import java.util.function.Consumer;

public class SingleInputAdapter<K> implements InputAdapter<K> {

	private Entry<K> entry;

	public SingleInputAdapter(K key) {
		entry = new Entry<>(key);
	}

	@Override
	public void forEach(Consumer<Entry<K>> con) {
		con.accept(entry);
	}

	@Override
	public boolean isPress() {
		return entry.info.isPress();
	}

	@Override
	public boolean isRelease() {
		return entry.info.isRelease();
	}

	@Override
	public boolean isDown() {
		return entry.info.isDown();
	}

	@Override
	public boolean isUp() {
		return entry.info.isUp();
	}
}
