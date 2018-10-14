package com.nompor.gtk.fx.input;

import static com.nompor.gtk.input.GTKMouseCode.*;

import java.util.HashMap;
import java.util.Map;

import com.nompor.gtk.input.GTKMouseCode;

import javafx.scene.input.MouseButton;

public class MouseCodeConverterFX {
	private static final Map<MouseButton,GTKMouseCode> map = new HashMap<>();
	private static final Map<GTKMouseCode,MouseButton> map2 = new HashMap<>();
	static {
		map.put(MouseButton.PRIMARY, LEFT);
		map.put(MouseButton.SECONDARY, RIGHT);
		map.put(MouseButton.MIDDLE, MIDDLE);
		map.put(MouseButton.NONE, MIDDLE);
		map.forEach((k,v)->map2.put(v, k));
	}

	public static GTKMouseCode toGTKCode(MouseButton key) {
		return map.get(key);
	}
	public static MouseButton toCode(GTKMouseCode key) {
		return map2.get(key);
	}
}
