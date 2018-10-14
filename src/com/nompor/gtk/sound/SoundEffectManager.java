package com.nompor.gtk.sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.nompor.gtk.GTKException;
import com.nompor.sound.Sound;
import com.nompor.sound.SoundClip;
import com.nompor.sound.SoundData;
import com.nompor.sound.SoundUtil;
import com.nompor.util.QueueCacheMap;

public class SoundEffectManager {

	private Map<Object, Sound[]> map;
	private SoundEffectFactory factory;
	private int defaultSubBuffer;
	protected SoundEffectManager(Map<Object, Sound[]> map, SoundEffectFactory factory, int defaultSubBuffer) {
		this.map = map;
		this.factory = factory;
		this.defaultSubBuffer = defaultSubBuffer;
	}

	public void load(String filePath) {
		load(filePath, defaultSubBuffer);
	}

	public static SoundEffectManager createInstance() {
		return createInstance(3);
	}
	public static SoundEffectManager createInstance(int defaultSubBuffer) {
		return new SoundEffectManager(new HashMap<>(), new SoundClipEffectFactory(), defaultSubBuffer);
	}
	public static SoundEffectManager createAutoRemoveCacheInstance() {
		return createAutoRemoveCacheInstance(20);
	}
	public static SoundEffectManager createAutoRemoveCacheInstance(int cacheNum) {
		return createAutoRemoveCacheInstance(cacheNum, 3);
	}
	public static SoundEffectManager createAutoRemoveCacheInstance(int cacheNum, int defaultSubBuffer) {
		return new SoundEffectManager(new QueueCacheMap<>(cacheNum), new SoundClipEffectFactory(), defaultSubBuffer);
	}


	public void load(String filePath, int subBuffer) {
		Sound[] sound = factory.newSound(filePath, subBuffer);
		Sound[] removeList = map.put(filePath, sound);
		if ( removeList != null ) {
			for ( Sound s : removeList ) s.close();
		}
	}

	public void unload(String filePath) {
		Sound[] removeList = map.remove(filePath);
		if ( removeList != null ) {
			for ( Sound s : removeList ) s.close();
		}
	}

	/**
	 * 指定したパスに関連づく効果音の再生されていないオブジェクトを再生します。
	 * @param filePath
	 */
	public void play(String filePath) {
		for( Sound c : getOrLoad(filePath)) {
			if ( !c.isActive() ) {
				c.play();
				break;
			}
		}
	}

	/**
	 * 指定したパスに関連づく効果音の再生されていないオブジェクトをループ再生します。
	 * @param filePath
	 */
	public void loop(String filePath) {
		for( Sound c : getOrLoad(filePath)) {
			if ( !c.isActive() ) {
				c.loop();
				break;
			}
		}
	}

	/**
	 * 指定したパスに関連づく効果音全てを停止します。
	 * @param filePath
	 */
	public void stop(String filePath) {
		for( Sound c : getOrLoad(filePath)) {
			if ( c.isActive() ) {
				c.stop();
			}
		}
	}

	protected Sound[] getOrLoad(String filePath){
		Sound[] arr = map.get(filePath);
		if ( arr == null ) {
			load(filePath);
		}
		return map.get(filePath);
	}

	public void clear() {
		map.forEach((k,v)->{for ( Sound s : v ) s.close();});
		map.clear();
	}

	private static class SoundClipEffectFactory implements SoundEffectFactory{
		LineListener adapter = new LineListener() {
			@Override
			public void update(LineEvent e) {
				if ( e.getType() == LineEvent.Type.STOP ) {
					Clip c = (Clip)e.getLine();
					c.stop();
					c.setFramePosition(0);
				}
			}
		};

		@Override
		public Sound[] newSound(String filePath, int subBuffer) {
			try {
				SoundData data = SoundUtil.createSoundData(filePath);
				SoundClip[] list = new SoundClip[subBuffer];
				for ( int i = 0;i < list.length;i++ ) {
					list[i] = new SoundClip(data);
					list[i].addLineListener(adapter);
				}
				return list;
			} catch (IOException|UnsupportedAudioFileException|LineUnavailableException e) {
				throw new GTKException(e);
			}
		}
	}
}
