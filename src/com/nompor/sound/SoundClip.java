package com.nompor.sound;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundClip implements Sound{
	private Clip c;
	private FloatControl fc;
	private AudioFormat af;
	private byte[] data;

	/**
	 * 指定した音声フォーマットと音声データを利用してSoundClipオブジェクトを構築します。
	 * @param filePath
	 * @param subBufferNum
	 * @throws LineUnavailableException
	 */
	public SoundClip(AudioFormat af, byte[] data) throws LineUnavailableException {
		init(af, data);
	}

	/**
	 * 指定した音声フォーマットと音声データを利用してSoundClipオブジェクトを構築します。
	 * @param filePath
	 * @param subBufferNum
	 * @throws LineUnavailableException
	 */
	public SoundClip(SoundData data) throws LineUnavailableException {
		init(data.format, data.data);
	}

	/**
	 * 指定した音声ファイルを読み込み、再生するためのSoundClipオブジェクトを構築します。
	 * @param filePath
	 * @param subBufferNum
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public SoundClip(String filePath) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		this(SoundUtil.createSoundData(filePath));
	}

	private void init(AudioFormat af, byte[] data) throws LineUnavailableException {
		this.data = data;
		this.af = af;
		c = SoundUtil.createClip(af, data);
		fc = (FloatControl)c.getControl(FloatControl.Type.MASTER_GAIN);
	}


	/**
	 * 再生します。
	 */
	public void play() {
		c.start();
	}

	/**
	 * ループ再生します。
	 */
	public void loop() {
		c.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * 一時停止します。
	 */
	public void pause() {
		c.stop();
	}


	/**
	 * 停止します。
	 */
	public void stop() {
        c.stop();
        c.flush();
        c.setFramePosition(0);
	}


	/**
	 * 音量を変更します。
	 * @param vol
	 */
	public void setVolume(double vol) {
		float f = (float)Math.log10(vol) * 20;
		if ( f <= fc.getMaximum() && f >= fc.getMinimum() ) fc.setValue(f);
	}

	/**
	 * ループ再生をする位置を設定します。
	 * @param start
	 * @param end
	 */
	public void setLoopPoints(int start, int end) {
		c.setLoopPoints(start, end);
	}


	/**
	 * リソースを解放します。
	 */
	public void close() {
		stop();
		c.close();
	}

	/**
	 * オーディオ形式を取得します。
	 * @return
	 */
	public AudioFormat getFormat() {
		return af;
	}

	/**
	 * 音声データを返します。
	 * @return
	 */
	public byte[] getData() {
		return data;
	}

	@Override
	public boolean isActive() {
        return c.isActive();
	}

	public void addLineListener(LineListener ll) {
		c.addLineListener(ll);
	}
}
