package com.nompor.gtk.fx.input;

import java.util.EnumMap;

import com.nompor.gtk.input.AbstractInputManager;
import com.nompor.gtk.input.InputInfo;

import javafx.scene.input.KeyCode;

/**
 * JavaFXキーフラグ管理クラス
 *
 */
public class KeyCodeManagerFX extends AbstractInputManager<KeyCode> {

	public KeyCodeManagerFX() {
		super(new EnumMap<KeyCode, InputInfo>(KeyCode.class));
	}
}
