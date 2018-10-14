package com.nompor.gtk.fx.animation;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ChangeViewAnimationsFX {
	public static ChangeViewAnimationFX createFadeChangeAnimation(Node changeInNode, Node changeOutNode, Duration changeInTime, Duration changeOutTime) {
		FadeTransition fadeIn = new FadeTransition(changeInTime);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);
		FadeTransition fadeOut = new FadeTransition(changeOutTime);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		changeInNode.setOpacity(0.0);
		changeOutNode.setOpacity(1.0);
		fadeIn.setNode(changeInNode);
		fadeOut.setNode(changeOutNode);
		ChangeViewAnimationFX changeAnime = new ChangeViewAnimationFX(fadeIn, fadeOut);
		changeAnime.addNode(changeInNode);
		changeAnime.addChangeInOnFinished(e -> {
			changeAnime.clearNode();
			changeAnime.addNode(changeOutNode);
		});
		return changeAnime;
	}
	public static ChangeViewAnimationFX createFadeChangeAnimation(Node changeInNode, Node changeOutNode, Duration changeTime) {
		return createFadeChangeAnimation(changeInNode, changeOutNode, changeTime, changeTime);
	}
	public static ChangeViewAnimationFX createFadeChangeAnimation(Node node, Duration changeTime) {
		return createFadeChangeAnimation(node, node, changeTime);
	}
	public static ChangeViewAnimationFX createFadeChangeFillRect(double x, double y, double width, double height, Paint color, Duration changeTime) {
		Rectangle rect = new Rectangle(x, y, width, height);
		rect.setFill(color);
		return createFadeChangeAnimation(rect, changeTime);
	}
	public static ChangeViewAnimationFX createFadeChangeFillRect(double width, double height,Paint color, Duration changeTime) {
		return createFadeChangeFillRect(0, 0, width, height, color, changeTime);
	}
	public static ChangeViewAnimationFX createFadeChangeFillRect(double width, double height,Paint color) {
		return createFadeChangeFillRect(width, height, color, Duration.millis(300));
	}
	public static ChangeViewAnimationFX createFadeChangeFillRect(double width, double height, Duration changeTime) {
		return createFadeChangeFillRect(width, height, Color.BLACK, changeTime);
	}
	public static ChangeViewAnimationFX createFadeChangeFillRect(double width, double height) {
		return createFadeChangeFillRect(width, height, Color.BLACK);
	}
}
