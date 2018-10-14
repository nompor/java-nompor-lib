package com.nompor.gtk.fx;

import com.nompor.gtk.animation.TranslateTarget;

import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Translate;

public class GameCameraFX implements TranslateTarget{
	private Translate translate = new Translate();

	public final double getTranslateX() {
		return translate.getX();
	}

	public final double getTranslateY() {
		return translate.getY();
	}

	public final double getTranslateZ() {
		return translate.getZ();
	}

	public final void setTranslateX(double arg0) {
		translate.setX(arg0);
	}

	public final void setTranslateY(double arg0) {
		translate.setY(arg0);
	}

	public final void setTranslateZ(double arg0) {
		translate.setZ(arg0);
	}
	private Camera camera;
	public GameCameraFX(Camera camera) {
		this.camera = camera;
		camera.getTransforms().add(translate);
	}

	public Camera getCamera() {
		return camera;
	}
	public static GameCameraFX createPerspectiveCamera() {
		return new GameCameraFX(new PerspectiveCamera());
	}
	public static GameCameraFX createParallelCamera() {
		return new GameCameraFX(new ParallelCamera());
	}
	public Translate getTranslate() {
		return translate;
	}
	public void update() {}
}
