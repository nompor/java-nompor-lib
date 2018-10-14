package com.nompor.sound;

import java.io.Closeable;

public interface Sound extends Closeable {
	void play();
	void loop();
	void pause();
	void stop();
	void close();
	boolean isActive();
}
