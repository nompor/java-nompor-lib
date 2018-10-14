package com.nompor.gtk;

public interface GTKFontFactory {
	default GTKFont createFont(int size) {
		return createFont(null, size);
	}
	default GTKFont createFont(String name, int size) {
		return createFont(name, GTKFontWeight.NORMAL, GTKFontPosture.REGULAR, size);
	}
	GTKFont createFont(String name, GTKFontWeight weight, GTKFontPosture posture, int size);
}
