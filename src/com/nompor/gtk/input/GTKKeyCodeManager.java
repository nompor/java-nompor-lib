package com.nompor.gtk.input;

import com.nompor.gtk.awt.input.KeyCodeConverter;
import com.nompor.gtk.awt.input.KeyCodeManager;
import com.nompor.gtk.fx.input.KeyCodeConverterFX;
import com.nompor.gtk.fx.input.KeyCodeManagerFX;

import javafx.scene.input.KeyCode;

public abstract class GTKKeyCodeManager extends GTKInputManager<GTKKeyCode> {

	protected GTKKeyCodeManager(AbstractInputManager<?> mng) {
		super(mng);
	}
	public static GTKKeyCodeManager createInstance(KeyCodeManager km) {
		return new GTKKeyCodeManager(km) {

			@Override
			public void regist(Object key, GTKKeyCode code) {
				km.regist(key, KeyCodeConverter.toCode(code));
			}

			@Override
			public void orRegist(Object key, GTKKeyCode... codes) {
				Integer[] intCode = new Integer[codes.length];
				for ( int i = 0;i < codes.length;i++ ) intCode[i] = KeyCodeConverter.toCode(codes[i]);
				km.orRegist(key, intCode);
			}

			@Override
			public void andRegist(Object key, GTKKeyCode... codes) {
				Integer[] intCode = new Integer[codes.length];
				for ( int i = 0;i < codes.length;i++ ) intCode[i] = KeyCodeConverter.toCode(codes[i]);
				km.andRegist(key, intCode);
			}
		};
	}
	public static GTKKeyCodeManager createFXInstance(KeyCodeManagerFX km) {
		return new GTKKeyCodeManager(km) {

			@Override
			public void regist(Object key, GTKKeyCode code) {
				km.regist(key, KeyCodeConverterFX.toCode(code));
			}

			@Override
			public void orRegist(Object key, GTKKeyCode... codes) {
				KeyCode[] fxCode = new KeyCode[codes.length];
				for ( int i = 0;i < codes.length;i++ ) fxCode[i] = KeyCodeConverterFX.toCode(codes[i]);
				km.orRegist(key, fxCode);
			}

			@Override
			public void andRegist(Object key, GTKKeyCode... codes) {
				KeyCode[] fxCode = new KeyCode[codes.length];
				for ( int i = 0;i < codes.length;i++ ) fxCode[i] = KeyCodeConverterFX.toCode(codes[i]);
				km.andRegist(key, fxCode);
			}
		};
	}
}
