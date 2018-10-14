package com.nompor.gtk.fx.movie;

import com.nompor.fx.media.MediaUtil;
import com.nompor.gtk.fx.GameViewFX;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;

public class MovieViewFX extends GameViewFX {
	private MediaPlayer player;
	public MovieViewFX(String filePath) {
		MediaView view = MediaUtil.createMediaView(filePath);
		player = view.getMediaPlayer();
		getChildren().add(view);
	}
	public MediaPlayer getMediaPlyer() {
		return player;
	}
	public void play() {
		player.play();
	}
	public void stop() {
		player.stop();
	}
	public void pause() {
		player.pause();
	}
	public void setVolume(double value) {
		player.setVolume(value);
	}
	public boolean isPlay() {
		return player.getStatus() == Status.PLAYING;
	}
	public void setAutoPlay(boolean isAutoPlay) {
		player.setAutoPlay(isAutoPlay);
	}
}
