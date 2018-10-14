package com.nompor.gtk.fx.animation;

import com.nompor.gtk.animation.AnimationTarget;

import javafx.scene.Node;

public class AnimationNode implements AnimationTarget{

	private Node node;
	public AnimationNode(Node node) {
		this.node = node;
	}
	public final double getOpacity() {
		return node.getOpacity();
	}
	public final double getRotate() {
		return node.getRotate();
	}
	public final double getScaleX() {
		return node.getScaleX();
	}
	public final double getScaleY() {
		return node.getScaleY();
	}
	public final double getTranslateX() {
		return node.getTranslateX();
	}
	public final double getTranslateY() {
		return node.getTranslateY();
	}
	public final void setOpacity(double arg0) {
		node.setOpacity(arg0);
	}
	public final void setRotate(double arg0) {
		node.setRotate(arg0);
	}
	public final void setScaleX(double arg0) {
		node.setScaleX(arg0);
	}
	public final void setScaleY(double arg0) {
		node.setScaleY(arg0);
	}
	public final void setTranslateX(double arg0) {
		node.setTranslateX(arg0);
	}
	public final void setTranslateY(double arg0) {
		node.setTranslateY(arg0);
	}
}
