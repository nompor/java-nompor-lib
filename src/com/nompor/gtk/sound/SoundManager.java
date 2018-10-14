package com.nompor.gtk.sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.nompor.gtk.GTKException;
import com.nompor.sound.Sound;
import com.nompor.sound.SoundClip;
import com.nompor.sound.SoundStream;

public class SoundManager{
	private SoundFactory factory;
	private Sound sound;
	private String filePath;
	protected SoundManager(SoundFactory factory) {
		this.factory = factory;
	}

	public static SoundManager createStreamInstance() {
		return new SoundManager(e -> {
			try {
				return new SoundStream(e);
			} catch (LineUnavailableException ex) {
				throw new GTKException(ex);
			}
		});
	}
	public static SoundManager createClipInstance() {
		return new SoundManager(e -> {
			try {
				return new SoundClip(e);
			} catch (IOException|UnsupportedAudioFileException|LineUnavailableException e1) {
				throw new GTKException(e1);
			}
		});
	}
	public static SoundManager createMediaClipInstance() {
		return new SoundManager(e -> {
			try {
				return new com.nompor.fx.sound.SoundClip(e);
			} catch (Exception e1) {
				throw new GTKException(e1);
			}
		});
	}
	/**
	 * 指定したパスに関連づく音声を再生します。
	 * @param filePath
	 */
	public void play(String filePath) {
		if ( !filePath.equals(this.filePath) ) {
			load(filePath);
		}
		play();
	}

	/**
	 * 指定したパスに関連づく音声をループ再生します。
	 * @param filePath
	 */
	public void loop(String filePath) {
		if ( !filePath.equals(this.filePath) ) {
			load(filePath);
		}
		loop();
	}

	/**
	 * 現在の音声を再生します。
	 * @param filePath
	 */
	public void play() {
		if ( !sound.isActive() ) {
			this.sound.play();
		}
	}

	/**
	 * 現在の音声をループ再生します。
	 * @param filePath
	 */
	public void loop() {
		if ( !sound.isActive() ) {
			this.sound.loop();
		}
	}

	/**
	 * 指定したパスに関連づく音声を停止します。
	 * @param filePath
	 */
	public void stop() {
		if ( sound.isActive() ) this.sound.stop();
	}

	/**
	 * 指定したパスに関連づく音声を一時停止します。
	 * @param filePath
	 */
	public void pause() {
		if ( sound.isActive() ) this.sound.pause();
	}

	/**
	 * 指定したファイルをロードします。
	 * @param filePath
	 */
	public void load(String filePath) {
		if ( this.filePath == null ) {
			this.sound = factory.newSound(filePath);
			this.filePath = filePath;
		} else if ( !this.filePath.equals(filePath) ) {
			stop();
			sound.close();
			this.sound = factory.newSound(filePath);
			this.filePath = filePath;
		}
	}
}
