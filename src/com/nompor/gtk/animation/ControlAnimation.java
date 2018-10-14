package com.nompor.gtk.animation;

public interface ControlAnimation extends Animation{


	/**
	 * アニメ―ションを終点状態へ移行させます。
	 */
	void doFinal();

	/**
	 * アニメーションを初期状態に設定します。
	 */
	void doInit();
}
