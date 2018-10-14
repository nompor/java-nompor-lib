package com.nompor.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundUtil {
	public static SoundData createSoundData(String filePath) throws IOException, UnsupportedAudioFileException {
		SoundData data = null;
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filePath))){
			AudioFormat af = ais.getFormat();
			byte[] b = ais.readAllBytes();
			data = new SoundData();
			data.data = b;
			data.format = af;
		}
		return data;
	}
	public static Clip createClip(SoundData data) throws LineUnavailableException {
		return createClip(data.format, data.data);
	}
	public static Clip createClip(AudioFormat af, byte[] data) throws LineUnavailableException {

		DataLine.Info dataLine = new DataLine.Info(Clip.class,af);
		Clip c = (Clip)AudioSystem.getLine(dataLine);
		c.open(af, data, 0, data.length);
		return c;
	}
	public static Clip createClip(String filePath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		return createClip(createSoundData(filePath));
	}
	public static SourceDataLine createSourceDataLine(AudioFormat af, Integer buffer) throws LineUnavailableException {
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,af);
		SourceDataLine sdl = (SourceDataLine)AudioSystem.getLine(info);
		if ( buffer != null ) {
			sdl.open(af, buffer);
		} else {
			sdl.open(af);
		}
		return sdl;
	}
	public static SourceDataLine createSourceDataLine(AudioFormat af) throws LineUnavailableException {
		return createSourceDataLine(af, null);
	}
}
