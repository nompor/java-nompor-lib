package com.nompor.gtk.awt.input;

import java.util.HashMap;

import com.nompor.gtk.input.AbstractInputManager;
import com.nompor.gtk.input.InputInfo;
/**
 * キーコード用キーフラグ管理クラス
 *
 */
public class KeyCodeManager extends AbstractInputManager<Integer> {

	public KeyCodeManager() {
		super(new HashMap<Integer, InputInfo>());
	}
}
