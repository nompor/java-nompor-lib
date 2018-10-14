package com.nompor.gtk.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.nompor.gtk.GTKWindow;
import com.nompor.gtk.awt.input.KeyCodeManager;
import com.nompor.gtk.awt.input.MouseManager;
import com.nompor.util.LibraryLogger;

/**
 * ゲーム用ウィンドウクラス
 *
 * @author nompor
 *
 */
public class GameWindow extends JFrame implements Runnable,GameInputListener,GTKWindow{
	private Thread th = null;
	private double sleepAddTime;
	private int fps=60;
	private BufferStrategy strategy;
	private boolean isBufferStrategyMode;
	private KeyCodeManager kcm = new KeyCodeManager();
	private MouseManager mm = new MouseManager();
	private GameView currentView;
	private GameView nextView;
	private boolean isForceGameLoop;
	private int width,height;
	private boolean isGameLoop;
	/**
	 * 指定されたサイズでウィンドウオブジェクトを構築します。
	 * @param width
	 * @param height
	 */
	public GameWindow(int width, int height) {
		this(width, height, true);
	}

	/**
	 * 指定されたサイズでウィンドウオブジェクトを構築します。
	 * isBufferStrategyModeをtrueにした場合はBufferStrategyを利用したアクティブレンダリングを利用し、falseの場合はデフォルトの描画処理が使用されます。
	 * @param width
	 * @param height
	 * @param isBufferStrategyMode
	 */
	public GameWindow(int width, int height, boolean isBufferStrategyMode) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		setFps(fps);
		getContentPane().setPreferredSize(new Dimension(width, height));
		pack();
		setLocationRelativeTo(null);
		getContentPane().addMouseListener(this);
		getContentPane().addMouseMotionListener(this);
		addKeyListener(this);
		addWindowListener(this);
		setIgnoreRepaint(true);
		this.width = width;
		this.height = height;
		this.isBufferStrategyMode = isBufferStrategyMode;
	}

	public int getViewWidth() {
		return width;
	}

	public int getViewHeight() {
		return height;
	}

	/**
	 * ゲームループを開始するメソッド
	 */
	public synchronized void startGameLoop(){
		if ( th == null ) {
			th = new Thread(this);
			isGameLoop = true;
			th.start();
		}
	}

	/**
	 * ゲームループを終了するメソッド
	 */
	public synchronized void stopGameLoop(){
		if ( isGameLoop ) {
			isGameLoop = false;
		}
	}

	/**
	 * ゲームループを終了するメソッド
	 */
	public synchronized void stopGameLoopJoin(long millis){
		if ( isGameLoop ) {
			Thread t = th;
			stopGameLoop();
			try {
				t.join(millis);
			} catch (InterruptedException e) {
				LibraryLogger.error(e);
			}
		}
	}

	/**
	 * ゲームループを終了するメソッド
	 */
	public synchronized void stopGameLoopJoin(){
		stopGameLoopJoin(0);
	}

	public synchronized void changeView(GameView view) {
		if ( view == null ) view = new GameView.DefaultView();
		view.setSize(width, height);
		GameView endView = this.currentView;
		this.currentView = view;
		if ( endView != null ) endView.stop();
		getContentPane().removeAll();
		if ( endView != null ) endView.end();
		view.ready();
		add(view);
		currentView.start();
	}

	/**
	 * GameViewの変更をゲームループスレッドに実行させるためのメソッド
	 * @param view
	 */
	public void changeViewEvent(GameView view) {
		nextView = view;
	}

	/**
	 * ゲームループのフレームレートを設定するメソッド
	 * @param fps
	 */
	public void setFps(int fps){
		if ( fps < 10 || fps > 60 ) {
			throw new IllegalArgumentException("fpsの設定は10～60の間で指定してください。");
		}
		this.fps = fps;
		sleepAddTime = 1000.0 / fps;
	}

	/**
	 * trueを設定するとウィンドウを表示します。
	 * BufferStrategy処理のためにオーバーライドします。
	 */
	public void setVisible(boolean isVisible) {
		if ( currentView == null ) {
			currentView = new GameView.DefaultView();
			add(currentView);
		}
		super.setVisible(isVisible);
		if ( isBufferStrategyMode && strategy == null ) {
			createBufferStrategy(2);
			strategy = getBufferStrategy();
		}
	}

	@Override
	public void run(){
		double nextTime = System.currentTimeMillis() + sleepAddTime;
		while(isGameLoop){
			try {
				if ( nextView != null ) {
					changeView(nextView);
					nextView = null;
				}
				kcm.update();
				mm.update();
				currentView.process();
				repaint();
			} catch (Exception e) {
				LibraryLogger.error(e);
				if ( !isForceGameLoop ) {
					break;
				}
			}
			long res = (long)nextTime - System.currentTimeMillis();
			if ( res < 0 ) {
				nextTime -= res;
				res = 0;
			}
			try{
				Thread.sleep(res);
			}catch(InterruptedException e){
				LibraryLogger.error(e);
			}
			nextTime += sleepAddTime;
		}
		isGameLoop = false;
		th = null;
	}


	/**
	 * このメソッドはisBufferStrategyModeがtrueの場合は描画処理が即実行され、終了するまで制御を戻しません。
	 * falseの場合は描画処理の要求を行い、制御をすぐに戻します。
	 */
	@Override
	public void repaint(long n, int x, int y, int w, int h) {
		if ( isBufferStrategyMode ) {
			// Render single frame
			do {
				// The following loop ensures that the contents of the drawing buffer
				// are consistent in case the underlying surface was recreated
				do {
					// Get a new graphics context every time through the loop
					// to make sure the strategy is validated
					Graphics g = strategy.getDrawGraphics();

					// Render to graphics
					// ...
					this.paint(g);



					// Dispose the graphics
					g.dispose();

					// Repeat the rendering if the drawing buffer contents
					// were restored
				} while (strategy.contentsRestored());

				// Display the buffer
				strategy.show();

				// Repeat the rendering if the drawing buffer was lost
			} while (strategy.contentsLost());
		} else {
			super.repaint(n, x, y, w, h);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mm.position(e.getX(), e.getY());
		currentView.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mm.position(e.getX(), e.getY());
		currentView.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		currentView.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mm.pressed(e.getButton());
		currentView.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mm.released(e.getButton());
		currentView.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		currentView.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		currentView.mouseExited(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		currentView.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		kcm.pressed(e.getKeyCode());
		currentView.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		kcm.released(e.getKeyCode());
		currentView.keyReleased(e);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		currentView.windowActivated(e);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		currentView.windowClosed(e);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		currentView.windowClosing(e);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		currentView.windowDeactivated(e);
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		currentView.windowDeiconified(e);
	}

	@Override
	public void windowIconified(WindowEvent e) {
		currentView.windowIconified(e);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		currentView.windowOpened(e);
	}

	public KeyCodeManager getKeyCodeManager() {
		return kcm;
	}

	public MouseManager getMouseManager() {
		return mm;
	}

	public boolean isForceGameLoop() {
		return isForceGameLoop;
	}

	public void setForceGameLoop(boolean isForceGameLoop) {
		this.isForceGameLoop = isForceGameLoop;
	}
}