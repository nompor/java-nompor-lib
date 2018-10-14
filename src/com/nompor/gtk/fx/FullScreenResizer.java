package com.nompor.gtk.fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FullScreenResizer {
	private Stage stg;
	private double dWidth, dHeight;
	private boolean isActive;
	private Parent root;
	private Translate t;
	private Scale s;
	private Pane newRoot = new Pane();
	private ChangeListener<Boolean> l;
	private Rectangle tl = new Rectangle(),rb = new Rectangle();
	public FullScreenResizer(Stage stg, double dWidth, double dHeight) {
		this.stg = stg;
		this.dWidth = dWidth;
		this.dHeight = dHeight;
		this.root = stg.getScene().getRoot();
		newRoot.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}
	public FullScreenResizer(GameWindowFX wnd) {
		this(wnd.getPrimaryStage(), wnd.getWidth(), wnd.getHeight());
	}
	public FullScreenResizer(Stage stg) {
		this(stg, stg.getScene().getWidth(), stg.getScene().getHeight());
	}
	public void setAutoChangeEvent(boolean isAutoActive) {
		if ( isAutoActive ) {
			if ( l == null ) {
				l = (ObservableValue<? extends Boolean> ob, Boolean o, Boolean n)->{
					if ( o && !n ) {
						setActive(false);
					} else if ( !o && n ) {
						setActive(true);
					}
				};
			}
			removeAutoActiveEvent();
			stg.fullScreenProperty().addListener(l);
		} else {
			removeAutoActiveEvent();
		}
	}
	private void removeAutoActiveEvent() {
		stg.fullScreenProperty().removeListener(l);
	}

	public void setActive(boolean isActive) {
		if ( isActive == this.isActive ) return;
		this.isActive = isActive;
		Scene scene = stg.getScene();
		if ( isActive != stg.isFullScreen() ) stg.setFullScreen(isActive);
		if ( isActive ) {
			scene.setRoot(newRoot);
			newRoot.getChildren().add(root);
			newRoot.getChildren().add(tl);
			newRoot.getChildren().add(rb);
			Screen screen = Screen.getPrimary();
			Rectangle2D b = screen.getBounds();
			double w = dWidth;
			double h = dHeight;
			double w2 = b.getWidth();
			double h2 = b.getHeight();
			double scw = w2 / w;
			double sch = h2 / h;
			double sc = Math.min(scw, sch);

			t = new Translate(w2/2-w*sc/2, h2/2-h*sc/2);
			s = new Scale(sc,sc);
			root.getTransforms().add(t);
			root.getTransforms().add(s);

			if ( scw > sch ) {
				double ww = ((int)w2 - (int)w*sc) / 2;
				double hh = h2;
				tl.setWidth(ww);
				rb.setWidth(ww);
				tl.setHeight(hh);
				rb.setHeight(hh);
				tl.setX(0);
				tl.setY(0);
				rb.setX(w2 - ww);
				rb.setY(0);
			} else {
				double ww = w2;
				double hh = ((int)h2 - (int)h*sc) / 2;
				tl.setWidth(ww);
				rb.setWidth(ww);
				tl.setHeight(hh);
				rb.setHeight(hh);
				tl.setX(0);
				tl.setY(0);
				rb.setX(0);
				rb.setY(h2 - hh);
			}
		} else {
			root.getTransforms().remove(t);
			root.getTransforms().remove(s);
			newRoot.getChildren().remove(root);
			newRoot.getChildren().remove(tl);
			newRoot.getChildren().remove(rb);
			scene.setRoot(root);
		}
	}
	public boolean isActive() {
		return isActive;
	}
	/**
	 * 指定したx座標をサイズ変更適用前の座標系に変換します。
	 * リサイズを適用していない場合はそのまま値を返します。
	 * @param x
	 */
	public double toBasePositionX(double x) {
		if ( isActive ) {
			return (x - t.getX()) / s.getX();
		}
		return x;
	}
	/**
	 * 指定したy座標をサイズ変更処理適用前の座標系に変換します。
	 * リサイズを適用していない場合はそのまま値を返します。
	 * @param y
	 */
	public double toBasePositionY(double y) {
		if ( isActive ) {
			return (y - t.getY()) / s.getY();
		}
		return y;
	}
}
