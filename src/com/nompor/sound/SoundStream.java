package com.nompor.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundStream implements Runnable,Sound{
	private FloatControl fc;
	private AudioFormat af;
	private File file;
	private boolean loop;
	private boolean stop;
	private boolean end = true;
	private SourceDataLine sdl;
	private byte[] b = new byte[20000];
	private Thread th;
	private AudioInputStream ais;

	public SoundStream(String filePath) throws LineUnavailableException {
		file = new File(filePath);
		createAudioInputStream();
		AudioFormat af = ais.getFormat();
		sdl = SoundUtil.createSourceDataLine(af, b.length);
		fc = (FloatControl)sdl.getControl(FloatControl.Type.MASTER_GAIN);
	}

	/**
	 * 再生します。
	 * */
	public void play(){
		if(end){
			loop = false;
			start();
		}
	}

	/**
	 * 無限ループで再生します。
	 * */
	public void loop(){
		if(end){
			loop = true;
			start();
		}
	}

	/**
	 * 停止します。
	 * */
	public void stop(){
		stop = true;
		th = null;
		sdl.stop();
		sdl.flush();
	}

	/**
	 * 一時停止させます。
	 * */
	public void pause(){
		sdl.stop();
		th = null;
	}

	private void start(){
		th = new Thread(this);
		end = false;
		th.start();
	}

	/**
	 * 音量を変更します。
	 * @param vol
	 */
	public void setVolume(double vol) {
		float f = (float)Math.log10(vol) * 20;
		if ( f <= fc.getMaximum() && f >= fc.getMinimum() ) fc.setValue(f);
	}

	@Override
	public void close() {
		Thread th = this.th;
		sdl.stop();
		this.th = null;
		try{
			th.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		sdl.flush();
		sdl.close();
		try{
			ais.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void run(){

		int size = 0;
		stop = false;
		sdl.start();

		while(th != null){
			try {
				size = ais.read(b);
				if ( size == -1 ) {
					ais.close();
					if ( loop ) {
						createAudioInputStream();
						continue;
					} else {
						break;
					}
				}
				sdl.write(b, 0, size);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(size == -1 || stop){
			createAudioInputStream();
		}

		stop();
		end = true;
	}

	private void createAudioInputStream(){

		try{
			if(ais != null)ais.close();

			//指定されたURLのオーディオ入力ストリームを取得
			ais = AudioSystem.getAudioInputStream(file);

		}catch(IOException e){
			e.printStackTrace();
		}catch(UnsupportedAudioFileException e){
			e.printStackTrace();
		}
	}

	/**
	 * オーディオ形式を取得します。
	 * @return
	 */
	public AudioFormat getFormat() {
		return af;
	}

	@Override
	public boolean isActive() {
		return sdl.isActive();
	}
}
