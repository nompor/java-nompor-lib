package com.nompor.gtk.sound;

import com.nompor.sound.Sound;

public interface SoundEffectFactory {
	Sound[] newSound(String filePath, int subBuffer);
}
