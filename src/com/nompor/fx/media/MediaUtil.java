package com.nompor.fx.media;

import java.io.File;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MediaUtil {
	public static Media createMedia(String filePath) {
		return new Media(new File(filePath).toURI().toString());
	}
	public static MediaPlayer createMediaPlayer(String filePath) {
		return new MediaPlayer(createMedia(filePath));
	}
	public static AudioClip createAudioClip(String filePath) {
		return new AudioClip(new File(filePath).toURI().toString());
	}
	public static MediaView createMediaView(String filePath) {
		return new MediaView(createMediaPlayer(filePath));
	}
}
