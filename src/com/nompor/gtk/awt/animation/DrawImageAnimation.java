package com.nompor.gtk.awt.animation;

import java.awt.Graphics;
import java.awt.Image;

import com.nompor.gtk.animation.AbstractFrameCounterAnimation;
import com.nompor.gtk.animation.AnimationTarget;
import com.nompor.gtk.awt.draw.DrawImage;
import com.nompor.gtk.geom.Point;
import com.nompor.gtk.geom.Rect;


public class DrawImageAnimation extends AbstractFrameCounterAnimation implements AnimationTarget,DrawAnimation {

	private DrawImage img;

	private Point[] p;
	private int width,height;
	private int index;
	private int startIndex;
	private int endIndex;
	private int updateDelay;
	private int animationEndIndex;

	public DrawImageAnimation(Image img, int width, int height, int updateDelay) {

		this.updateDelay = Math.max(updateDelay, 1);
		int x = (int)img.getWidth(null) / width;
		int y = (int)img.getHeight(null) / height;
		p = new Point[x * y];
		for ( int i = 0;i < y;i++ ) {
			for ( int j = 0;j < x;j++ ) {
				p[i*x+j] = new Point(j * width, i * height);
			}
		}
		this.width = width;
		this.height = height;
		this.img = new DrawImage(img);
		this.endIndex = p.length - 1;
		updateViewport();
	}

	public DrawImageAnimation(Image img, int width, int height) {
		this(img, width, height, 1);
	}

	/**
	 * 画像番号の範囲をアニメーションするように設定します。
	 * @param startIndex
	 * @param endIndex
	 */
	public void setAnimationRange(int startIndex, int endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	/**
	 * 全ての画像をアニメーションの対象とします。
	 */
	public void setDefaultAnimationRange() {
		setAnimationRange(0, getMaxIndex());
	}

	/**
	 * 画像番号の最大値を取得します。
	 * @return
	 */
	public int getMaxIndex() {
		return p.length - 1;
	}

	/**
	 * 画像分割数を返します。
	 * @return
	 */
	public int getLength() {
		return p.length;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public void updateFrame() {
		if ( isUpdateDelayFrame(updateDelay) ) {
			if ( endIndex < index + 1 ) {
				index = startIndex;
			} else {
				index++;
			}
			updateViewport();
		}
	}

	private void updateViewport() {
		Rect rect = img.getViewport();
		Point p = this.p[index];
		rect.x = p.x;
		rect.y = p.y;
		rect.w = width;
		rect.h = height;
	}

	@Override
	public void draw(Graphics g) {
		img.draw(g);
	}

	public void draw(Graphics g, int x, int y) {
		img.draw(g, x, y);
	}

	public double getX() {
		return img.getX();
	}

	public void setX(double x) {
		img.setX(x);
	}

	public double getY() {
		return img.getY();
	}

	public void setY(double y) {
		img.setY(y);
	}

	public double getRotate() {
		return img.getRotate();
	}

	public void setRotate(double rotate) {
		img.setRotate(rotate);
	}

	public double getScaleX() {
		return img.getScaleX();
	}

	public void setScaleX(double scaleX) {
		img.setScaleX(scaleX);
	}

	public double getScaleY() {
		return img.getScaleY();
	}

	public void setScaleY(double scaleY) {
		img.setScaleY(scaleY);
	}

	public double getOpacity() {
		return img.getOpacity();
	}

	public void setOpacity(double opacity) {
		img.setOpacity(opacity);
	}

	public double getTranslateX() {
		return img.getTranslateX();
	}

	public void setTranslateX(double translateX) {
		img.setTranslateX(translateX);
	}

	public double getTranslateY() {
		return img.getTranslateY();
	}

	public void setTranslateY(double translateY) {
		img.setTranslateY(translateY);
	}

	public boolean isEnd() {
		return super.isEnd() || index == animationEndIndex;
	}

	public int getAnimationEndIndex() {
		return animationEndIndex;
	}

	public void setAnimationEndIndex(int animationEndIndex) {
		this.animationEndIndex = animationEndIndex;
	}

	public void setAnimationEndOfLastImageIndex() {
		this.animationEndIndex = getMaxIndex();
	}
}
