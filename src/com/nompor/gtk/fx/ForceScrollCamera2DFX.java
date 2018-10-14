package com.nompor.gtk.fx;

import com.nompor.gtk.CameraTarget2D;
import com.nompor.gtk.geom.Point;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

/**
 * 強制スクロールを実現するためのカメラ
 * @author nompor
 *
 */
public class ForceScrollCamera2DFX extends FixedTargetCamera2DFX {
	private CameraTargetNode2D target;
	private SequentialTransition anime;
	public ForceScrollCamera2DFX(Camera camera, double x, double y, double cameraWidth, double cameraHeight) {
		super(camera, cameraWidth, cameraHeight, new CameraTargetNode2D(new Ellipse(x, y, 5, 5)));
		target = (CameraTargetNode2D)getTarget();
		setOffsetX(cameraWidth/2);
		setOffsetY(cameraHeight/2);
	}
	public ForceScrollCamera2DFX(Camera camera, double cameraWidth, double cameraHeight) {
		this(camera, 0, 0, cameraWidth, cameraHeight);
	}

	/**
	 * @return
	 */
	public static ForceScrollCamera2DFX createRangeScrollCamera(double cameraWidth, double cameraHeight, double minX, double minY, double maxX, double maxY) {
		ForceScrollCamera2DFX camera = createSimpleCamera(cameraWidth, cameraHeight);
		camera.setMinX(minX);
		camera.setMinY(minY);
		camera.setMaxX(maxX);
		camera.setMaxY(maxY);
		camera.setMoveRange(true);
		return camera;
	}
	/**
	 * @return
	 */
	public static ForceScrollCamera2DFX createSimpleCamera(double x, double y, double cameraWidth, double cameraHeight) {
		return new ForceScrollCamera2DFX(new PerspectiveCamera(), x, y, cameraWidth, cameraHeight);
	}
	/**
	 * @return
	 */
	public static ForceScrollCamera2DFX createRangeScrollCamera(double x, double y, double cameraWidth, double cameraHeight, double minX, double minY, double maxX, double maxY) {
		ForceScrollCamera2DFX camera = createSimpleCamera(x, y, cameraWidth, cameraHeight);
		camera.setMinX(minX);
		camera.setMinY(minY);
		camera.setMaxX(maxX);
		camera.setMaxY(maxY);
		camera.setMoveRange(true);
		return camera;
	}
	@Override
	public final void setTarget(CameraTarget2D target) {
		throw new UnsupportedOperationException();
	}
	/**
	 */
	public static ForceScrollCamera2DFX createSimpleCamera(double cameraWidth, double cameraHeight) {
		return new ForceScrollCamera2DFX(new PerspectiveCamera(), cameraWidth, cameraHeight);
	}
	public SequentialTransition getAnime() {
		return anime;
	}
	public void setAnime(Animation... anime) {
		this.anime = new SequentialTransition(anime);
		this.anime.setNode(target.getNode());
	}
	public void setPositions(Duration dur, Point2D... positions) {
		Animation[] anime = new Animation[positions.length];
		for (int i = 0;i < positions.length;i++) {
			TranslateTransition a = new TranslateTransition(dur);
			a.setToX(positions[i].getX());
			a.setToY(positions[i].getY());
			anime[i] = a;
		}
		this.setAnime(anime);
	}
	public void setPositions(Duration dur, Point... positions) {
		Animation[] anime = new Animation[positions.length];
		for (int i = 0;i < positions.length;i++) {
			TranslateTransition a = new TranslateTransition(dur);
			a.setToX(positions[i].x);
			a.setToY(positions[i].y);
			anime[i] = a;
		}
		this.setAnime(anime);
	}

	public void pause() {
		anime.pause();
	}
	public void play() {
		anime.play();
	}
	public final void setAutoReverse(boolean arg0) {
		anime.setAutoReverse(arg0);
	}
	public final void setCycleCount(int arg0) {
		anime.setCycleCount(arg0);
	}
	public void stop() {
		anime.stop();
	}
}
