package com.nompor.gtk.fx;

import com.nompor.gtk.CameraTarget2D;

import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;

/**
 * CameraTarget2Dが示すオブジェクトが画面の中央に来るように移動させるカメラ
 * @author nompor
 *
 */
public class FixedTargetCamera2DFX extends GameCameraFX {

	private CameraTarget2D target;
	private boolean isMoveRange;
	private final double width;
	private final double height;
	private double offsetX;
	private double offsetY;
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;

	public FixedTargetCamera2DFX(Camera camera, double cameraWidth, double cameraHeight, CameraTarget2D target) {
		super(camera);
		this.width = cameraWidth;
		this.height = cameraHeight;
		this.target = target;
	}
	/**
	 * 引数が表す座標が中心となるように移動させるカメラ
	 * ただし、移動範囲は指定範囲内に限定される
	 * @param target
	 * @return
	 */
	public static FixedTargetCamera2DFX createRangeCamera(double cameraWidth, double cameraHeight, CameraTarget2D target, double minX, double minY, double maxX, double maxY) {
		FixedTargetCamera2DFX camera = createSimpleCamera(cameraWidth, cameraHeight, target);
		camera.setMinX(minX);
		camera.setMinY(minY);
		camera.setMaxX(maxX);
		camera.setMaxY(maxY);
		camera.setMoveRange(true);
		return camera;
	}
	/**
	 * 引数が表す座標が中心となるように移動させるカメラ
	 * @param target
	 * @return
	 */
	public static FixedTargetCamera2DFX createSimpleCamera(double cameraWidth, double cameraHeight, CameraTarget2D target) {
		return new FixedTargetCamera2DFX(new PerspectiveCamera(), cameraWidth, cameraHeight, target);
	}
	@Override
	public void update() {
		double rx = target.getCTX() - width / 2 + offsetX;
		double ry = target.getCTY() - height / 2 + offsetY;
		if ( isMoveRange ) {
			if ( rx < minX ) {
				rx = minX;
			} else if ( rx + width > maxX ) {
				rx = maxX - width;
			}
			if ( ry < minY ) {
				ry = minY;
			} else if ( ry + height > maxY ) {
				ry = maxY - height;
			}
		}
		setTranslateX(rx);
		setTranslateY(ry);
	}

	public CameraTarget2D getTarget() {
		return target;
	}
	public void setTarget(CameraTarget2D target) {
		this.target = target;
	}
	public void setTarget(Node target) {
		this.target = new CameraTargetNode2D(target);
	}
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	public double getOffsetX() {
		return offsetX;
	}
	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}
	public double getOffsetY() {
		return offsetY;
	}
	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}
	public double getMinY() {
		return minY;
	}
	public void setMinY(double minY) {
		this.minY = minY;
	}
	public double getMaxX() {
		return maxX;
	}
	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}
	public double getMaxY() {
		return maxY;
	}
	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}
	public boolean isMoveRange() {
		return isMoveRange;
	}
	public void setMoveRange(boolean isMoveRange) {
		this.isMoveRange = isMoveRange;
	}
	public double getMinX() {
		return minX;
	}
	public void setMinX(double minX) {
		this.minX = minX;
	}
	public double getRight() {
		return (int)getTranslateX() + (int)width;
	}
	public double getLeft() {
		return getTranslateX();
	}
	public double getBottom() {
		return (int)getTranslateY() + (int)height;
	}
	public double getTop() {
		return getTranslateY();
	}
}
