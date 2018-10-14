
package com.nompor.gtk.fx;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import com.nompor.gtk.GTKException;
import com.nompor.gtk.NativeResolutionController;
import com.nompor.gtk.awt.FullScreenController;
import com.nompor.util.PlatformUtil;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

/**
 * AWT、SwingのAPI、または独自のJNI実装を使用しJavaFXアプリケーションでのフルスクリーンサポートと解像度の変更をサポートをします。
 * このクラスはJavaFXアプリケーションで解像度変更をする際に邪道な処理が実行されるため、通常使用することは推奨されません。
 * @author nompor
 *
 */
@Deprecated
public class FullScreenControllerFX {
	private static Stage stage;
	private static double defaultWidth;
	private static double defaultHeight;
	private static FullScreenControllerFXBase ctrl;
	private static ChangeListener<Boolean> l;

	public static enum ResolutionType{
		AWT,NATIVE;
	}

	public static void init(Stage stage, ResolutionType type) {
		init(stage, stage.getScene().getWidth(), stage.getScene().getHeight(), type);
	}

	public static void init(Stage stage, double width, double height, ResolutionType type) {
		if ( FullScreenControllerFX.stage != null ) throw new GTKException("Already setting.");
		defaultWidth = width;
		defaultHeight = height;
		FullScreenControllerFX.stage = stage;
		if ( type == ResolutionType.AWT ) ctrl = new AWTFullScreenControllerFX();
		else if ( type == ResolutionType.NATIVE ) ctrl = new NativeFullScreenControllerFX();
	}

	public static void init(GameWindowFX wnd, ResolutionType type) {
		init(wnd.getPrimaryStage(),wnd.getWidth(),wnd.getHeight(),type);
	}

	public static void init(Stage stage) {
		init(stage, stage.getScene().getWidth(), stage.getScene().getHeight(), ResolutionType.AWT);
	}

	public static void init(GameWindowFX wnd) {
		init(wnd, ResolutionType.AWT);
	}

	public static Stage getStage() {
		return stage;
	}

	public static void close() {
		close(null);
	}

	public static void close(Runnable callback) {
		ctrl.close(callback);
		removeAutoActiveEvent();
	}

	public static boolean isFullScreen() {
		return ctrl.isFullScreen();
	}

	public static boolean isDisplayChangeSupported() {
		return ctrl.isDisplayChangeSupported();
	}
	public boolean isFullScreenSupported() {
		return ctrl.isFullScreenSupported();
	}

	public static void setAutoChangeEvent(boolean isAutoActive) {
		if ( isAutoActive ) {
			if ( l == null ) {
				l = (ObservableValue<? extends Boolean> ob, Boolean o, Boolean n)->{
					if ( o && !n ) {
						setUnFullScreenWithResolution();
					} else if ( !o && n ) {
						setFullScreenWithResolution();
					}
				};
			}
			removeAutoActiveEvent();
			stage.fullScreenProperty().addListener(l);
		} else {
			removeAutoActiveEvent();
		}
	}
	private static void removeAutoActiveEvent() {
		if ( l != null ) stage.fullScreenProperty().removeListener(l);
	}


	@Deprecated
	public static void setFullScreenWithResolution(double width, double height) {
		ctrl.setFullScreenWithResolution(width, height);
	}

	@Deprecated
	public static void setFullScreenWithResolution() {
		setFullScreenWithResolution(defaultWidth, defaultHeight);
	}

	@Deprecated
	public static void setUnFullScreenWithResolution() {
		ctrl.setUnFullScreenWithResolution();
	}

	private interface FullScreenControllerFXBase{
		void setFullScreenWithResolution(double width, double height) ;
		void setUnFullScreenWithResolution();
		void close(Runnable callback);
		boolean isFullScreen();
		boolean isDisplayChangeSupported();
		boolean isFullScreenSupported();
	}

	private static class AWTFullScreenControllerFX implements FullScreenControllerFXBase{
		private final JWindow pseudoWnd;
		private boolean isOldAlwaysOnTop;
		private final JButton applicationExitBtn = new JButton("Application exit.");
		private final JButton endOfResolutionControl = new JButton("Return to window mode.");
		AWTFullScreenControllerFX() {
			pseudoWnd = new JWindow();
			SwingUtilities.invokeLater(() -> {
				applicationExitBtn.setVisible(false);
				endOfResolutionControl.setVisible(false);

				pseudoWnd.setLayout(new GridLayout(2, 1));
				pseudoWnd.add(endOfResolutionControl);
				pseudoWnd.add(applicationExitBtn);
				pseudoWnd.setAlwaysOnTop(false);

				applicationExitBtn.addActionListener(e -> System.exit(0));
				endOfResolutionControl.addActionListener(e -> setUnFullScreenWithResolution());
			});
		}

		@Override
		public void setFullScreenWithResolution(double width, double height) {
			if ( FullScreenController.isFullScreen() ) return;
			isOldAlwaysOnTop = stage.isAlwaysOnTop();
			SwingUtilities.invokeLater(() -> {
				FullScreenController.setWindow(pseudoWnd);
				FullScreenController.setFullScreenWindow();
				FullScreenController.setDisplayMode(width, height);
				Platform.runLater(()->{
					if ( !stage.isFullScreen() ) stage.setFullScreen(true);
					stage.setAlwaysOnTop(true);
					SwingUtilities.invokeLater(() -> {
						applicationExitBtn.setVisible(true);
						endOfResolutionControl.setVisible(true);
						pseudoWnd.setVisible(false);
					});
				});
			});
		}

		@Override
		public  void setUnFullScreenWithResolution() {
			if ( !FullScreenController.isFullScreen() ) return;
			SwingUtilities.invokeLater(() -> {
				FullScreenController.setUnFullScreenWindow();
				FullScreenController.setDefaultDisplayMode();
				pseudoWnd.dispose();
				Platform.runLater(()->{
					if ( stage.isFullScreen() ) stage.setFullScreen(false);
					stage.setAlwaysOnTop(isOldAlwaysOnTop);
					SwingUtilities.invokeLater(() -> {
						applicationExitBtn.setVisible(false);
						endOfResolutionControl.setVisible(false);
						Platform.runLater(()->{
							stage.setX(0);
							stage.setY(0);
							stage.centerOnScreen();
							stage.sizeToScene();
							stage.requestFocus();
						});
					});
				});
			});
		}

		@Override
		public boolean isFullScreen() {
			return FullScreenController.isFullScreen() && stage.isFullScreen();
		}

		@Override
		public boolean isDisplayChangeSupported(){
			return FullScreenController.isDisplayChangeSupported();
		}

		@Override
		public boolean isFullScreenSupported() {
			return FullScreenController.isFullScreenSupported();
		}

		@Override
		public void close(Runnable callback) {
			SwingUtilities.invokeLater(() -> {
				FullScreenController.setUnFullScreenWindow();
				FullScreenController.setDefaultDisplayMode();
				pseudoWnd.dispose();
				if ( callback != null ) callback.run();
			});
		}
	}

	private static class NativeFullScreenControllerFX implements FullScreenControllerFXBase{

		@Override
		public boolean isFullScreen() {
			return NativeResolutionController.isResolutionChanged() && stage.isFullScreen();
		}

		@Override
		public boolean isDisplayChangeSupported() {
			return NativeResolutionController.isDisplayChangeSupported();
		}

		@Override
		public boolean isFullScreenSupported() {
			return PlatformUtil.isWindows() || PlatformUtil.isMac();
		}

		@Override
		public void setFullScreenWithResolution(double width, double height) {
			if ( !NativeResolutionController.isDisplayChangeSupported() ) return ;
			if ( !NativeResolutionController.isResolutionChanged() ) {
				NativeResolutionController.setDisplayMode((int)width, (int)height);
				if ( !stage.isFullScreen() ) stage.setFullScreen(true);
			}
		}

		@Override
		public void setUnFullScreenWithResolution() {
			if ( !NativeResolutionController.isDisplayChangeSupported() ) return ;
			if ( NativeResolutionController.isResolutionChanged() ) {
				NativeResolutionController.defaultDisplayMode();
				if ( stage.isFullScreen() ) stage.setFullScreen(false);
				Platform.runLater(()->{
					stage.centerOnScreen();
					stage.sizeToScene();
				});
			}
		}

		@Override
		public void close(Runnable callback) {
			NativeResolutionController.close();
			if ( callback != null ) callback.run();
		}
	}
}
