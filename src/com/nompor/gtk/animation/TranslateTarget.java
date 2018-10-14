package com.nompor.gtk.animation;

import com.nompor.gtk.CameraTarget2D;

/**
 * 移動対象のオブジェクトに実装するためのインターフェース
 *
 */
public interface TranslateTarget extends CameraTarget2D {
	/**
	 * 移動対象のx座標を取得します。
	 * @return
	 */
	double getTranslateX();
	/**
	 * 移動対象のy座標を取得します。
	 * @return
	 */
	double getTranslateY();
	/**
	 * 移動対象のx座標を設定します。
	 */
	void setTranslateX(double x);
	/**
	 * 移動対象のx座標を設定します。
	 */
	void setTranslateY(double y);

	/**
	 * 移動対象のx座標を取得します。
	 * @return
	 */
	default double getCTX() {
		return getTranslateX();
	}

	/**
	 * 移動対象のy座標を取得します。
	 * @return
	 */
	default double getCTY() {
		return getTranslateY();
	}
}
