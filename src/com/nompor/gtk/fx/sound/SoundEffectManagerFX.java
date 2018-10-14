package com.nompor.gtk.fx.sound;

import java.util.HashMap;
import java.util.Map;

import com.nompor.fx.sound.SoundClip;
import com.nompor.gtk.sound.SoundEffectFactory;
import com.nompor.gtk.sound.SoundEffectManager;
import com.nompor.sound.Sound;
import com.nompor.util.QueueCacheMap;

public class SoundEffectManagerFX extends SoundEffectManager {

	private SoundEffectManagerFX(Map<Object, Sound[]> map, SoundEffectFactory factory, int defaultSubBuffer) {
		super(map, factory, defaultSubBuffer);
	}

	public static SoundEffectManagerFX createMediaInstance() {
		return createMediaInstance(3);
	}
	public static SoundEffectManagerFX createMediaInstance(int defaultSubBuffer) {
		return new SoundEffectManagerFX(new HashMap<>(), new SoundMediaFactory(), defaultSubBuffer);
	}
	public static SoundEffectManagerFX createAutoRemoveCacheMediaInstance() {
		return createAutoRemoveCacheMediaInstance(20);
	}
	public static SoundEffectManagerFX createAutoRemoveCacheMediaInstance(int cacheNum) {
		return createAutoRemoveCacheMediaInstance(cacheNum, 3);
	}
	public static SoundEffectManagerFX createAutoRemoveCacheMediaInstance(int cacheNum, int defaultSubBuffer) {
		return new SoundEffectManagerFX(new QueueCacheMap<>(cacheNum), new SoundMediaFactory(), defaultSubBuffer);
	}

	private static class SoundMediaFactory implements SoundEffectFactory{

		@Override
		public Sound[] newSound(String filePath, int subBuffer) {
			Sound[] list = new Sound[subBuffer];
			for ( int i = 0;i < list.length;i++ ) list[i] = new SoundClip(filePath);
			return list;
		}
	}
}
