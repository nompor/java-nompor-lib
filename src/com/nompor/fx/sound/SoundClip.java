package com.nompor.fx.sound;

import com.nompor.fx.media.MediaUtil;
import com.nompor.sound.Sound;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

public class SoundClip implements Sound{
	private AudioClip player;

	public SoundClip(String filePath) {
		this(MediaUtil.createAudioClip(filePath));
	}

	public SoundClip(AudioClip media) {
		player = media;
	}

	@Override
	public void play() {
		if ( !player.isPlaying() ) {
			player.setCycleCount(1);
			player.play();
		}
	}

	@Override
	public void loop() {
		if ( !player.isPlaying() ) {
			player.setCycleCount(MediaPlayer.INDEFINITE);
			player.play();
		}
	}

	@Override
	public void pause() {
		throw new UnsupportedOperationException("pause is unsupported");
	}

	@Override
	public void stop() {
		if ( player.isPlaying() ) {
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

	@Override
	public boolean isActive() {
		return player.isPlaying();
	}
}
