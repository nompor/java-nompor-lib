package com.nompor.gtk.input;

import java.io.Serializable;
import java.util.function.Consumer;

public interface InputAdapter<K> extends Serializable {
	class Entry<K> implements Serializable{
		K key;
		InputInfo info;
		public Entry(K key) {
			this.key = key;
		}
	}
	boolean isPress();
	boolean isRelease();
	boolean isDown();
	boolean isUp();

	/**
	 * 汎用実装用判定結果を取得するメソッド
	 * @return
	 */
	default boolean isAction() {return isPress() || isRelease();}

	/**
	 * 汎用実装用アップデートメソッド
	 */
	default void update() {}


	void forEach(Consumer<Entry<K>> con);
}
