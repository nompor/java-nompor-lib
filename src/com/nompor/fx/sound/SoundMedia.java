package com.nompor.fx.sound;

import com.nompor.fx.media.MediaUtil;
import com.nompor.sound.Sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class SoundMedia implements Sound{
	private MediaPlayer player;

	public SoundMedia(String filePath) {
		this(MediaUtil.createMedia(filePath));
	}

	public SoundMedia(Media media) {
		player = new MediaPlayer(media);
	}

	@Override
	public void play() {
		if ( player.getStatus() != Status.PLAYING ) {
			player.setCycleCount(1);
			player.play();
		}
	}

	@Override
	public void loop() {
		if ( player.getStatus() != Status.PLAYING ) {
			player.setCycleCount(MediaPlayer.INDEFINITE);
			player.play();
		}
	}

	@Override
	public void pause() {
		if ( player.getStatus() == Status.PLAYING ) {
			player.pause();
		}
	}

	@Override
	public void stop() {
		if ( player.getStatus() == Status.PLAYING ) {
			player.stop();
		}
	}

	@Override
	public void close() {
		player = null;
	}

	public void setVolume(double vol) {
		player.setVolume(vol);
	}

	public double getVolume() {
		return player.getVolume();
	}

	public void setRate(double rate) {
		player.setRate(rate);
	}

	public double getRate() {
		return player.getRate();
	}

	public void seek(double seek) {
		player.seek(Duration.millis(seek));
	}

	@Override
	public boolean isActive() {
		return player.getStatus() == Status.PLAYING;
	}

	public void setOnEndOfMedia(Runnable run) {
		player.setOnEndOfMedia(run);
	}
}
