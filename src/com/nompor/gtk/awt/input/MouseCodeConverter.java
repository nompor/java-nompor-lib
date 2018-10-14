package com.nompor.gtk.awt.input;

import static com.nompor.gtk.input.GTKMouseCode.*;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import com.nompor.gtk.input.GTKMouseCode;

public class MouseCodeConverter {
	private static final Map<Integer,GTKMouseCode> map = new HashMap<>();
	private static final Map<GTKMouseCode,Integer> map2 = new HashMap<>();
	static {
		map.put(MouseEvent.BUTTON1, LEFT);
		map.put(MouseEvent.BUTTON2, RIGHT);
		map.put(MouseEvent.BUTTON3, MIDDLE);
		map.forEach((k,v)->map2.put(v, k));
	}

	public static GTKMouseCode toGTKCode(Integer key) {
		return map.get(key);
	}
	public static Integer toCode(GTKMouseCode key) {
		return map2.get(key);
	}
}
