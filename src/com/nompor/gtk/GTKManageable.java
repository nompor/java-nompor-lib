package com.nompor.gtk;

import com.nompor.gtk.image.GTKImageManager;
import com.nompor.gtk.input.GTKKeyCodeManager;
import com.nompor.gtk.input.GTKMouseManager;
import com.nompor.gtk.sound.SoundEffectManager;
import com.nompor.gtk.sound.SoundManager;

public interface GTKManageable {
	void startGameLoop();
	void stopGameLoop();
	SoundManager getSoundManager();
	SoundEffectManager getSoundEffectManager();
	void setFullScreen(boolean isFullScreen);
	boolean isFullScreen();
	void changeView(GTKView view);
	void start(GTK.InitParams params);
	APIType getAPIType();
	GTKImageManager getImageManager();
	GTKKeyCodeManager getKeyCodeManager();
	GTKMouseManager getMouseManager();
	GTKPaintFactory getPaintFactory();
	GTKFontFactory getFontFactory();
	GTKWindow getWindow();
	GTKCanvas getCanvas();
	void show();
}
