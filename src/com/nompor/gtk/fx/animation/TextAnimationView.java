package com.nompor.gtk.fx.animation;

import com.nompor.gtk.fx.event.IndexChangeListener;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TextAnimationView extends Text implements IndexChangeListener{
	private Timeline timeline = new Timeline();
	private Duration dur;
	private IndexAnimationProperty index = new IndexAnimationProperty(this);
	private String text;
	public TextAnimationView(String text) {
		this.text = text;
	}
	public TextAnimationView(Duration dur, String text) {
		this(text);
		setDuration(dur);
	}
	public TextAnimationView(int oneCharDur, String text) {
		this(text);
		setDurationOneChar(oneCharDur);
	}
	public TextAnimationView(Duration dur, String text, Color clr, Font f) {
		this(dur, text);
		setDuration(dur);
		setFill(clr);
		setFont(f);
	}
	public TextAnimationView(int oneCharDur, String text, Color clr, Font f) {
		this(oneCharDur, text);
		setDurationOneChar(oneCharDur);
		setFill(clr);
		setFont(f);
	}

	/**
	 * 指定時間でアニメーションを初期化します
	 * @param dur
	 */
	public void setDuration(Duration dur) {
		this.dur = dur;
		doInit();
	}
	public void setDurationOneChar(int oneCharDur) {
		timeline.stop();
		int durCnt = text.length() * oneCharDur;
		this.dur = new Duration(durCnt);
		doInit();
	}
	public void doInit() {
		if ( dur == null ) {
			setDurationOneChar(80);
			return;
		}
		timeline.stop();
		timeline.getKeyFrames().clear();
		setText("");
		KeyFrame kf1 = new KeyFrame(Duration.millis(0), new KeyValue(index, 0, Interpolator.DISCRETE));
		KeyFrame kf2 = new KeyFrame(dur, new KeyValue(index, text.length(), Interpolator.LINEAR));
		timeline.getKeyFrames().add(kf1);
		timeline.getKeyFrames().add(kf2);
	}

	@Override
	public void changeIndex(int index) {
		super.setText(text.substring(0, index));
	}

	public Animation getAnimation() {
		return timeline;
	}

	public void setOnFinished(EventHandler<ActionEvent> e) {
		timeline.setOnFinished(e);
	}

	public void play() {
		timeline.play();
	}

	public void stop() {
		timeline.stop();
	}

	public void pause() {
		timeline.pause();
	}

	public void setAutoReverse(boolean val) {
		timeline.setAutoReverse(val);
	}

	public void setCycleCount(int val) {
		timeline.setCycleCount(val);
	}
}
