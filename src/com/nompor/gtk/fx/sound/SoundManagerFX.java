package com.nompor.gtk.fx.sound;

import com.nompor.fx.sound.SoundMedia;
import com.nompor.gtk.sound.SoundFactory;
import com.nompor.gtk.sound.SoundManager;

public class SoundManagerFX extends SoundManager{

	private SoundManagerFX(SoundFactory factory) {
		super(factory);
	}
	public static SoundManagerFX createMediaInstance() {
		return new SoundManagerFX(e -> new SoundMedia(e));
	}
}
