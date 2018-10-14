package com.nompor.gtk.input;

import com.nompor.gtk.awt.input.MouseCodeConverter;
import com.nompor.gtk.awt.input.MouseManager;
import com.nompor.gtk.fx.input.MouseCodeConverterFX;
import com.nompor.gtk.fx.input.MouseManagerFX;
import com.nompor.gtk.geom.Point;

import javafx.scene.input.MouseButton;

public abstract class GTKMouseManager extends GTKInputManager<GTKMouseCode> {

	private AbstractMouseManager<?> mmng;
	protected GTKMouseManager(AbstractMouseManager<?> mng) {
		super(mng);
		this.mmng = mng;
	}

	public double getX() {
		return mmng.getX();
	}
	public double getY() {
		return mmng.getY();
	}

	public int getIX() {
		return (int)mmng.getX();
	}
	public int getIY() {
		return (int)mmng.getY();
	}

	public Point getPoint() {
		return mmng.getPoint();
	}

	public static GTKMouseManager createInstance(MouseManager mm) {
		return new GTKMouseManager(mm) {

			@Override
			public void regist(Object key, GTKMouseCode code) {
				mm.regist(key, MouseCodeConverter.toCode(code));
			}

			@Override
			public void orRegist(Object key, GTKMouseCode... codes) {
				Integer[] intCode = new Integer[codes.length];
				for ( int i = 0;i < codes.length;i++ ) intCode[i] = MouseCodeConverter.toCode(codes[i]);
				mm.orRegist(key, intCode);
			}

			@Override
			public void andRegist(Object key, GTKMouseCode... codes) {
				Integer[] intCode = new Integer[codes.length];
				for ( int i = 0;i < codes.length;i++ ) intCode[i] = MouseCodeConverter.toCode(codes[i]);
				mm.andRegist(key, intCode);
			}
		};
	}
	public static GTKMouseManager createFXInstance(MouseManagerFX mm) {
		return new GTKMouseManager(mm) {

			@Override
			public void regist(Object key, GTKMouseCode code) {
				mm.regist(key, MouseCodeConverterFX.toCode(code));
			}

			@Override
			public void orRegist(Object key, GTKMouseCode... codes) {
				MouseButton[] fxCode = new MouseButton[codes.length];
				for ( int i = 0;i < codes.length;i++ ) fxCode[i] = MouseCodeConverterFX.toCode(codes[i]);
				mm.orRegist(key, fxCode);
			}

			@Override
			public void andRegist(Object key, GTKMouseCode... codes) {
				MouseButton[] fxCode = new MouseButton[codes.length];
				for ( int i = 0;i < codes.length;i++ ) fxCode[i] = MouseCodeConverterFX.toCode(codes[i]);
				mm.andRegist(key, fxCode);
			}
		};
	}
}
