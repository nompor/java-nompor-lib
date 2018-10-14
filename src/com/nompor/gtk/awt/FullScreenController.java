package com.nompor.gtk.awt;

import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

/**
 * 解像度を変更するためのメソッド群です。
 * @author nompor
 *
 */
public class FullScreenController {

	private static  DisplayMode dm;
	private static Window wnd;
	static {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		dm = gd.getDisplayMode();
	}
	public static void setWindow(Window wnd) {
		FullScreenController.wnd = wnd;
	}
	public static Window getWindow() {
		return wnd;
	}
	public static DisplayMode[] getDisplayModes() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.getDisplayModes();
	}
	public static synchronized void setDisplayMode(int width,int height,int bitDepth,int refrashRate){
		setDisplayMode(new DisplayMode(width,height,bitDepth,refrashRate));
	}
	public static synchronized void setDisplayMode(DisplayMode dm){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		if ( !gd.getDisplayMode().equals(dm) ) {
			gd.setDisplayMode(dm);
		}
	}
	/**
	 * 初期解像度を設定します。
	 */
	public static synchronized void setDefaultDisplayMode(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		if ( !gd.getDisplayMode().equals(dm) ) gd.setDisplayMode(dm);
	}
	public static void setFullScreenWindow(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		if ( wnd instanceof Frame && wnd.isShowing() ) {
			Frame f = (Frame)wnd;
			f.dispose();
			f.setUndecorated(true);
			f.setVisible(true);
		}
		gd.setFullScreenWindow(wnd);
	}
	/**
	 * 規定値を使用し、フルスクリーンモードへ移行します。
	 */
	public static synchronized void setFullScreenWithResolution(){
		setFullScreenWindow();
		setDisplayMode(wnd.getWidth(), wnd.getHeight());
	}
	public static synchronized void setFullScreenWithResolution(int width,int height){
		setFullScreenWindow();
		setDisplayMode(width,height);
	}
	public static synchronized void setFullScreenWithResolution(int width,int height,int bitDepth,int refrashRate){
		setFullScreenWindow();
		setDisplayMode(width,height,bitDepth,refrashRate);
	}
	/**
	 * 初期解像度に変更し、ウィンドウモードに移行します。
	 */
	public static synchronized void setUnFullScreenWithResolution(){
		setDefaultDisplayMode();
		setUnFullScreenWindow();
	}
	/**
	 * 初期解像度に変更し、ウィンドウモードに移行します。
	 */
	public static synchronized void setUnFullScreenWindow(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(null);
		if ( wnd instanceof Frame && wnd.isShowing() ) {
			Frame f = (Frame)wnd;
			f.dispose();
			f.setUndecorated(false);
			f.setVisible(true);
		}
	}
	/**
	 * 指定されたサイズと一致する解像度を設定します。
	 * ビット深度、リフレッシュレートは高いモードを優先で選択します。
	 * @param width
	 * @param height
	 */
	public static synchronized void setDisplayMode(int width, int height) {

		DisplayMode[] display = getDisplayModes();
		DisplayMode newMode = null;
		for(DisplayMode dm : display){
			if(dm.getWidth() == width && dm.getHeight() == height &&
					((newMode == null)
					|| newMode.getBitDepth() < dm.getBitDepth()
					|| newMode.getBitDepth() == dm.getBitDepth() && Math.abs(60 - newMode.getRefreshRate()) >= Math.abs(60 - dm.getRefreshRate()))
					){
				newMode = dm;
			}
		}
		if(newMode != null){
			setDisplayMode(newMode);
		}
	}
	/**
	 * 指定されたサイズと一致する解像度を設定します。
	 * ビット深度、リフレッシュレートは最大のものが選択されます。
	 * @param width
	 * @param height
	 */
	public static synchronized void setDisplayMode(double width, double height) {
		setDisplayMode((int)Math.round(width), (int)Math.round(height));
	}

	public static boolean isDisplayChangeSupported(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.isDisplayChangeSupported();
	}

	public static boolean isFullScreenSupported() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.isFullScreenSupported();
	}

	public static boolean isFullScreen() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		return wnd != null && gd.getFullScreenWindow() == wnd;
	}
}
