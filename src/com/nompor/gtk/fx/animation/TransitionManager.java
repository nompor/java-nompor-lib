package com.nompor.gtk.fx.animation;

import java.util.function.Consumer;

import com.nompor.util.UnorderList;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class TransitionManager {

	private UnorderList<Animation> arr = new UnorderList<>();
	private TransitionManager() {}
	public static TransitionManager create() {
		return new TransitionManager();
	}

	public SequentialTransition createSequentialTransition() {
		SequentialTransition a = new SequentialTransition();
		add(a);
		return a;
	}

	public SequentialTransition createSequentialTransition(Animation... args) {
		SequentialTransition a = new SequentialTransition(args);
		add(a);
		return a;
	}

	public ParallelTransition createParallelTransition() {
		ParallelTransition a = new ParallelTransition();
		add(a);
		return a;
	}

	public ParallelTransition createParallelTransition(Animation... args) {
		ParallelTransition a = new ParallelTransition(args);
		add(a);
		return a;
	}

	public TranslateTransition createTranslateTransition() {
		TranslateTransition a = new TranslateTransition();
		add(a);
		return a;
	}

	public TranslateTransition createTranslateTransition(Duration dur, Node node) {
		TranslateTransition a = new TranslateTransition(dur, node);
		add(a);
		return a;
	}

	public FadeTransition createFadeTransition() {
		FadeTransition a = new FadeTransition();
		add(a);
		return a;
	}

	public FadeTransition createFadeTransition(Duration dur, Node node) {
		FadeTransition a = new FadeTransition(dur, node);
		add(a);
		return a;
	}

	public RotateTransition createRotateTransition() {
		RotateTransition a = new RotateTransition();
		add(a);
		return a;
	}

	public RotateTransition createRotateTransition(Duration dur, Node node) {
		RotateTransition a = new RotateTransition(dur, node);
		add(a);
		return a;
	}

	public StrokeTransition createStrokeTransition() {
		StrokeTransition a = new StrokeTransition();
		add(a);
		return a;
	}

	public StrokeTransition createStrokeTransition(Duration dur, Shape node) {
		StrokeTransition a = new StrokeTransition(dur, node);
		add(a);
		return a;
	}

	public FillTransition createFillTransition() {
		FillTransition a = new FillTransition();
		add(a);
		return a;
	}

	public FillTransition createFillTransition(Duration dur, Shape node) {
		FillTransition a = new FillTransition(dur, node);
		add(a);
		return a;
	}

	public PathTransition createPathTransition() {
		PathTransition a = new PathTransition();
		add(a);
		return a;
	}

	public PathTransition createPathTransition(Duration dur, Shape node) {
		PathTransition a = new PathTransition(dur, node);
		add(a);
		return a;
	}

	public ScaleTransition createScaleTransition() {
		ScaleTransition a = new ScaleTransition();
		add(a);
		return a;
	}

	public ScaleTransition createScaleTransition(Duration dur, Node node) {
		ScaleTransition a = new ScaleTransition(dur, node);
		add(a);
		return a;
	}

	public PauseTransition createPauseTransition() {
		PauseTransition a = new PauseTransition();
		add(a);
		return a;
	}

	public PauseTransition createPauseTransition(Duration dur) {
		PauseTransition a = new PauseTransition(dur);
		add(a);
		return a;
	}

	public Timeline createTimeline() {
		Timeline a = new Timeline();
		add(a);
		return a;
	}

	public Timeline createTimeline(KeyFrame... keyFrames) {
		Timeline a = new Timeline(keyFrames);
		add(a);
		return a;
	}

	public ImageAnimationView createImageAnimationView(Duration dur, Image img, int width, int height) {
		ImageAnimationView a = new ImageAnimationView(dur, img, width, height);
		add(a.getAnimation());
		return a;
	}

	public TextAnimationCanvas createTextAnimationCanvas(Duration dur, String text, double x, double y, double width, double height, double ox, double oy) {
		TextAnimationCanvas a = new TextAnimationCanvas(dur, text, x, y, width, height, ox, oy);
		add(a.getAnimation());
		return a;
	}

	public TextAnimationView createTextAnimation(int oneCharDuration, String text) {
		TextAnimationView a = new TextAnimationView(oneCharDuration, text);
		add(a.getAnimation());
		return a;
	}

	public SequentialTextAnimationView createSequentialTextAnimation(int oneCharDuration, TextAnimationView... text) {
		SequentialTextAnimationView a = new SequentialTextAnimationView(text);
		a.setAllDurationOneChar(oneCharDuration);
		add(a.getAnimation());
		return a;
	}

	public SequentialTextAnimationView createSequentialTextAnimation(TextAnimationView... text) {
		SequentialTextAnimationView a = new SequentialTextAnimationView(text);
		add(a.getAnimation());
		return a;
	}

	public synchronized void add(Animation e) {
		arr.add(e);
	}

	public int size() {
		return arr.size();
	}

	public synchronized void clear() {
		forEach(e -> {
			if ( e.getStatus() == Status.RUNNING ) e.stop();
		});
		arr.clear();
	}

	public synchronized void forEach(Consumer<? super Animation> arg0) {
		arr.forEach(arg0);
	}

	public synchronized Animation get(int index) {
		return arr.get(index);
	}

	public synchronized void remove(int index) {
		Animation anime = arr.get(index);
		if ( anime.getStatus() == Status.RUNNING ) anime.stop();
		arr.remove(index);
	}
}
