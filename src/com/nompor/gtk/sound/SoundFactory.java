package com.nompor.gtk.sound;

import com.nompor.sound.Sound;

public interface SoundFactory {
	Sound newSound(String filePath);
}
