package com.nompor.gtk.input;

public class InputInfo {
	private boolean isOnRelease;
	private boolean isOnPress;
	private boolean isPress;
	private boolean isRelease;
	private boolean isDown;
	int registedCount=0;
	void press(){
		isOnPress=true;
	}
	void release() {
		isOnRelease=true;
	}
	void update() {
		isPress = false;
		isRelease = false;
		if ( isOnPress ) {
			if ( !isDown ) {
				isPress = true;
				isDown = true;
			}
			isOnPress = false;

		} else if ( isOnRelease ) {
			if ( isDown ) {
				isRelease = true;
				isDown = false;
			}
			isOnRelease = false;
		}
	}
	void clear() {
		isOnRelease = false;
		isOnPress = false;
		isPress = false;
		isRelease = false;
		isDown = false;
	}
	boolean isPress() {
		return isPress;
	}
	boolean isRelease() {
		return isRelease;
	}
	boolean isDown() {
		return isDown;
	}
	boolean isUp() {
		return !isDown;
	}
}
