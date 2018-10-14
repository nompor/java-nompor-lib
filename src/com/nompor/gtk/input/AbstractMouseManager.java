package com.nompor.gtk.input;

import java.util.Map;

import com.nompor.gtk.geom.Point;

/**
 * 毎フレームキー処理を監視することで、キーが押されているかどうかを判断するための機能を実装するための抽象クラス

 * @param <K>
 */
public abstract class AbstractMouseManager<K> extends AbstractInputManager<K>{

	private transient Point p=new Point();

	protected AbstractMouseManager(Map<K, InputInfo> keyMap) {
		super(keyMap);
	}

	public void position(double x, double y) {
		p.x = x;
		p.y = y;
	}

	public double getX() {
		return p.x;
	}
	public double getY() {
		return p.y;
	}

	public int getIX() {
		return (int)p.x;
	}
	public int getIY() {
		return (int)p.y;
	}

	public Point getPoint() {
		return p;
	}
}
