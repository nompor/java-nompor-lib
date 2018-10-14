package com.nompor.gtk.fx;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public interface GameViewableFX {
	/**
	 * オーバーライドしてゲームの処理を記述する
	 */
	default void process() {}
	/**
	 * オーバーライドしてゲームの描画を記述する
	 */
	default void draw() {}

	/**
	 * カメラ更新処理を記述する
	 */
	default void updateCamera() {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	default void mouseDragged(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	default void mouseMoved(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	default void mouseClicked(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	default void mousePressed(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	default void mouseReleased(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	default void keyTyped(KeyEvent e) {}
	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	default void keyPressed(KeyEvent e) {}
	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	default void keyReleased(KeyEvent e) {}

	/**
	 * この画面の表示が開始される前に呼び出されます。
	 */
	default void ready() {}

	/**
	 * この画面の表示が開始された時に呼び出されます。
	 */
	default void start() {}

	/**
	 * この画面の表示が終了される前に呼び出されます。
	 */
	default void stop() {}

	/**
	 * この画面の表示が終了された時に呼び出されます。
	 */
	default void end() {}

	/**
	 * 表示アニメーション開始処理が開始された時に呼び出されます。
	 */
	default void changeInStart() {}
	/**
	 * 表示アニメーション開始処理が終了した時に呼び出されます。
	 */
	default void changeInEnd() {}

	/**
	 * 表示アニメーション終了処理が開始された時に呼び出されます。
	 */
	default void changeOutStart() {}
	/**
	 * 表示アニメーション終了処理が終了した時に呼び出されます。
	 */
	default void changeOutEnd() {}
}
