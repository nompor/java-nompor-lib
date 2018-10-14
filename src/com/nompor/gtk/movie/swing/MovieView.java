package com.nompor.gtk.movie.swing;

import com.nompor.fx.media.MediaUtil;
import com.nompor.gtk.swing.GameView;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;

public class MovieView extends GameView{

	private MediaPlayer player;
	public MovieView(String filePath) {
		MediaView view = MediaUtil.createMediaView(filePath);
		player = view.getMediaPlayer();
		JFXPanel panel = new JFXPanel();
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		panel.setScene(scene);
		add(panel);
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
