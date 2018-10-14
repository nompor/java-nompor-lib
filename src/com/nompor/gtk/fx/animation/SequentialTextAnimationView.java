package com.nompor.gtk.fx.animation;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.TextFlow;

public class SequentialTextAnimationView extends TextFlow {
	private ArrayList<TextAnimationView> list = new ArrayList<>();
	private SequentialTransition seq = new SequentialTransition();
	public SequentialTextAnimationView(TextAnimationView... text) {
		for ( TextAnimationView t : text ) add(t);
	}
	public SequentialTextAnimationView() {
	}
	public static SequentialTextAnimationView createTextOrPause(Object... objects) {
		SequentialTextAnimationView sta = new SequentialTextAnimationView();
		for ( Object o : objects ) {
			if ( o instanceof PauseTransition ) {
				PauseTransition t = (PauseTransition)o;
				sta.add(t);
			} else if ( o instanceof TextAnimationView ) {
				TextAnimationView t = (TextAnimationView)o;
				sta.add(t);
			}
		}
		return sta;

	}
	public void add(TextAnimationView t) {
		list.add(t);
		seq.getChildren().add(t.getAnimation());
	}
	public void add(PauseTransition t) {
		seq.getChildren().add(t);
	}

	public void setAllDurationOneChar(int oneCharDur) {
		list.forEach(e -> e.setDurationOneChar(oneCharDur));
	}

	/**
	 * アニメーションを初期化します
	 * @param dur
	 */
	public void doInit() {
		list.forEach(e -> e.doInit());
	}

	public Animation getAnimation() {
		return seq;
	}

	public void setOnFinished(EventHandler<ActionEvent> e) {
		seq.setOnFinished(e);
	}

	public void play() {
		seq.play();
	}

	public void stop() {
		seq.stop();
		doInit();
	}

	public void pause() {
		seq.pause();
	}

	public void setAutoReverse(boolean val) {
		seq.setAutoReverse(val);
	}

	public void setCycleCount(int val) {
		seq.setCycleCount(val);
	}
}

