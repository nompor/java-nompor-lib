package com.nompor.gtk.fx.animation;

import com.nompor.gtk.fx.TextCanvas;
import com.nompor.gtk.fx.event.IndexChangeListener;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TextAnimationCanvas extends TextCanvas implements IndexChangeListener {

	private IndexAnimationProperty index = new IndexAnimationProperty(this);
	private Timeline timeline = new Timeline();
	private Duration dur;
	private String text;

	public TextAnimationCanvas(Duration dur, String text, double x, double y, double width, double height, double ox, double oy) {
		super(false, text, x, y, width, height, ox, oy);
		this.dur = dur;
		init();
	}

	public TextAnimationCanvas(Duration dur, String text, double width, double height, double ox, double oy) {
		super(false, text, 0, 0, width, height, ox, oy);
		this.dur = dur;
		init();
	}

	public void init() {
		timeline.stop();
		timeline.getKeyFrames().clear();
		setText("");
		KeyFrame kf1 = new KeyFrame(Duration.ZERO, new KeyValue(index, 0, Interpolator.DISCRETE));
		KeyFrame kf2 = new KeyFrame(dur, new KeyValue(index, text.length(), Interpolator.LINEAR));
		timeline.getKeyFrames().add(kf1);
		timeline.getKeyFrames().add(kf2);
	}

	public void play() {
		timeline.play();
	}

	public void stop() {
		timeline.stop();
	}

	public void setCycleCount(int value) {
		timeline.setCycleCount(value);
	}

	public Status getStatus() {
		return timeline.getStatus();
	}

	public TextAnimationCanvas(Duration dur, String text, double width, double height) {
		this(dur, text, 0, 0, width, height);
	}

	@Override
	public void setText(String s) {
		this.text = s;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void changeIndex(int index) {
		super.setText(text.substring(0, index));
		super.draw();
	}

	public Animation getAnimation(){
		return timeline;
	}
}
