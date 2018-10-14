package com.nompor.gtk.swing;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;

/**
 * ゲーム画面を表すクラス
 * @author nompor
 *
 */
public abstract class GameView extends JPanel implements GameInputListener{
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	/**
	 * オーバーライドしてゲームの処理を記述する
	 * @param g
	 */
	public void process() {}
	/**
	 * オーバーライドしてゲームの描画を記述する
	 * @param g
	 */
	public void draw(Graphics g) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	public void mouseDragged(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	public void mouseMoved(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	public void mouseClicked(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	public void mouseReleased(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	public void mouseEntered(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてマウスイベントを記述する
	 * @param e
	 */
	public void mouseExited(MouseEvent e) {}
	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	public void keyTyped(KeyEvent e) {}
	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {}
	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {}

	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	public void windowActivated(WindowEvent e) {}

	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	public void windowClosed(WindowEvent e) {}

	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	public void windowClosing(WindowEvent e) {}

	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	public void windowDeactivated(WindowEvent e) {}

	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	public void windowDeiconified(WindowEvent e) {}

	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	public void windowIconified(WindowEvent e) {}

	/**
	 * 必要ならオーバーライドしてキーイベントを記述する
	 * @param e
	 */
	public void windowOpened(WindowEvent e) {}

	/**
	 * この画面の表示が開始された時に呼び出されます。
	 */
	public void start() {}

	/**
	 * この画面の表示が終了された時に呼び出されます。
	 */
	public void end() {}

	/**
	 * この画面の表示が開始される前に呼び出されます。
	 */
	public void ready() {}

	/**
	 * この画面の表示が終了される前に呼び出されます。
	 */
	public void stop() {}

	/**
	 * 表示アニメーション開始処理が開始された時に呼び出されます。
	 */
	public void changeInStart() {}
	/**
	 * 表示アニメーション開始処理が終了した時に呼び出されます。
	 */
	public void changeInEnd() {}

	/**
	 * 表示アニメーション終了処理が開始された時に呼び出されます。
	 */
	public void changeOutStart() {}
	/**
	 * 表示アニメーション終了処理が終了した時に呼び出されます。
	 */
	public void changeOutEnd() {}

	public void changeView(GameView view) {
		Component comp=getParent();
		while( comp != null ){
			if ( comp instanceof GameWindow ) {
				((GameWindow)comp).changeView(view);;
				break;
			}
		}
	}
	public void changeViewEvent(GameView view) {
		Component comp=getParent();
		while( comp != null ){
			if ( comp instanceof GameWindow ) {
				((GameWindow)comp).changeViewEvent(view);;
				break;
			}
		}
	}
	public static class DefaultView extends GameView{}
}