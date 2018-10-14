package com.nompor.gtk;

import com.nompor.gtk.draw.GTKGraphics;
import com.nompor.gtk.event.GTKKeyEvent;
import com.nompor.gtk.event.GTKMouseEvent;

public interface GTKView {
	/**
	 * オーバーライドしてゲームの処理を記述する
	 */
	default void process() {}
	/**
	 * オーバーライドしてゲームの描画を記述する
	 */
	default void draw() {}
	/**
	 * オーバーライドしてゲームの描画を記述する
	 */
	default void draw(GTKGraphics g) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	default void mouseDragged(GTKMouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	default void mouseMoved(GTKMouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	default void mouseClicked(GTKMouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	default void mousePressed(GTKMouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	default void mouseReleased(GTKMouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	default void keyTyped(GTKKeyEvent e) {}
	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	default void keyPressed(GTKKeyEvent e) {}
	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	default void keyReleased(GTKKeyEvent e) {}

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
