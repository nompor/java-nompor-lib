package com.nompor.gtk.fx;

import com.nompor.gtk.CameraTarget2D;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public class CameraTargetNode2D implements CameraTarget2D{

	private Node node;
	public CameraTargetNode2D(Node node) {
		this.node = node;
	}
	@Override
	public double getCTX() {
		Bounds b = node.getBoundsInParent();
		return b.getMinX() + b.getWidth() / 2;
	}

	@Override
	public double getCTY() {
		Bounds b = node.getBoundsInParent();
		return b.getMinY() + b.getHeight() / 2;
	}

	public Node getNode() {
		return node;
	}
}
