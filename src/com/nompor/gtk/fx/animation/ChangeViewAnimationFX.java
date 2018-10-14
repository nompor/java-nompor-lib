package com.nompor.gtk.fx.animation;

import com.nompor.gtk.fx.event.MultiEventHandler;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

public final class ChangeViewAnimationFX extends Group {
	private SequentialTransition anime;
	private Animation changeIn;
	private Animation changeOut;
	private MultiEventHandler<ActionEvent> changeInEvent = new MultiEventHandler<>();
	private MultiEventHandler<ActionEvent> changeOutEvent = new MultiEventHandler<>();
	public ChangeViewAnimationFX(Animation changeIn, Animation changeOut, Node...nodes) {
		this.changeIn = changeIn;
		this.changeOut = changeOut;
		for ( Node n : nodes ) {
			addNode(n);
		}
	}
	public ChangeViewAnimationFX(Animation changeIn, Animation changeOut) {
		this(changeIn, changeOut, new Node[0]);
	}
	public ChangeViewAnimationFX(Animation changeOut) {
		this(null, changeOut);
	}
	public ChangeViewAnimationFX() {
		this(null);
	}
	public void setChangeInAnimation(Animation changeIn) {
		this.changeIn = changeIn;
	}
	public void setChangeOutAnimation(Animation changeOut) {
		this.changeOut = changeOut;
	}
	public void addChangeInOnFinished(EventHandler<ActionEvent> e) {
		changeInEvent.add(e);
	}
	public void addChangeOutOnFinished(EventHandler<ActionEvent> e) {
		changeOutEvent.add(e);
	}
	public void addNode(Node node) {
		getChildren().add(node);
	}
	public void clearNode() {
		getChildren().clear();
	}
	public void start() {
		if ( anime != null ) return;
		if ( changeIn == null )	changeIn = new PauseTransition(Duration.millis(0));
		if ( changeIn.getOnFinished() != null ) {
			addChangeInOnFinished(changeIn.getOnFinished());
		}
		changeIn.setOnFinished(this.changeInEvent);
		if ( changeOut == null ) changeOut = new PauseTransition(Duration.millis(0));
		if ( changeOut.getOnFinished() != null ) {
			addChangeOutOnFinished(changeOut.getOnFinished());
		}
		changeOut.setOnFinished(this.changeOutEvent);
		anime = new SequentialTransition(changeIn, changeOut);
		anime.play();
	}
}
