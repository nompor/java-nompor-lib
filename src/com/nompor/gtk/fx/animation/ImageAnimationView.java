package com.nompor.gtk.fx.animation;

import java.io.File;

import com.nompor.gtk.fx.event.IndexChangeListener;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;

/**
 * アニメーション処理を実行できるImageViewクラス
 *
 */
public class ImageAnimationView extends ImageView implements IndexChangeListener{

	private Rectangle2D[] viewports;
	private IndexAnimationProperty index = new IndexAnimationProperty(this);
	private Timeline timeline;
	private Duration dur;
	/**
	 * 指定した画像を横幅、縦幅で分割し、指定期間で1アニメーションを実行するImageAnimationViewオブジェクトを構築します。
	 * @param dur
	 * @param img
	 * @param width
	 * @param height
	 */
	public ImageAnimationView(Duration dur, Image img, int width, int height) {
		int x = (int)img.getWidth() / width;
		int y = (int)img.getHeight() / height;
		Rectangle2D[] viewports = new Rectangle2D[x * y];
		for ( int i = 0;i < y;i++ ) {
			for ( int j = 0;j < x;j++ ) {
				viewports[i*x+j] = new Rectangle2D(j * width, i * height, width, height);
			}
		}
		this.dur = dur;
		this.viewports = viewports;
		setViewport(viewports[0]);
		setImage(img);
		timeline = new Timeline();
	}
	/**
	 * 指定したファイルが表す画像を横幅、縦幅で分割し、指定期間で1アニメーションを実行するImageAnimationViewオブジェクトを構築します。
	 * @param dur
	 * @param img
	 * @param width
	 * @param height
	 */
	public ImageAnimationView(Duration dur, File file, int width, int height) {
		this(dur, new Image(file.toURI().toString()), width, height);
	}

	/**
	 * 画像番号の範囲をアニメーションするように設定します。
	 * @param startIndex
	 * @param endIndex
	 */
	public void setAnimationRange(int startIndex, int endIndex) {
		timeline.stop();
		timeline.getKeyFrames().clear();
		KeyFrame kf1 = new KeyFrame(Duration.millis(0), new KeyValue(index, startIndex, Interpolator.DISCRETE));
		KeyFrame kf2 = new KeyFrame(dur, new KeyValue(index, endIndex, IndexAnimationProperty.INDEX_LINEAR));
		timeline.getKeyFrames().add(kf1);
		timeline.getKeyFrames().add(kf2);
	}

	/**
	 * 全ての画像をアニメーションの対象とします。
	 */
	public void setDefaultAnimationRange() {
		setAnimationRange(0, getMaxIndex());
	}

	/**
	 * アニメーションを開始します。
	 */
	public void play() {
		timeline.play();
	}

	/**
	 * アニメーションを一時停止します。
	 */
	public void pause() {
		timeline.pause();
	}

	/**
	 * アニメーションを停止します。
	 */
	public void stop() {
		timeline.stop();
	}

	/**
	 * アニメーションステータスを返します。
	 * @return
	 */
	public Status getStatus() {
		return timeline.getStatus();
	}

	/**
	 * アニメーションのループ回数を指定します。
	 * @param value
	 */
	public void setCycleCount(int value) {
		timeline.setCycleCount(value);
	}

	/**
	 * アニメーションを反転ループさせるかを指定します。
	 * @param value
	 */
	public void setAutoReverse(boolean value) {
		timeline.setAutoReverse(value);
	}

	/**
	 * 現在の画像番号を返します。
	 * @return
	 */
	public int getIndex() {
		return index.getValue();
	}

	/**
	 * 指定したindexに該当する画像を切り出し、新しいImageオブジェクトを作成します。
	 * @param index
	 * @return
	 */
	public Image createImage(int index) {
		Rectangle2D r = viewports[index];
		return new WritableImage(getImage().getPixelReader(), (int)r.getMinX(), (int)r.getMinY(), (int)r.getWidth(), (int)r.getHeight());
	}

	/**
	 * 現在の画像番号を指定したindexに設定します。
	 * @param index
	 */
	public void setIndex(int index) {
		this.changeIndex(index);
	}

	/**
	 * このアニメーションの遅延時間を設定します。
	 * @param value
	 */
	public void setDelay(Duration value) {
		timeline.setDelay(value);
	}

	/**
	 * このアニメーションを再生する方向/速度を設定します。
	 * @param value
	 */
	public void setRate(double value) {
		timeline.setRate(value);
	}

	/**
	 * 画像番号の最大値を取得します。
	 * @return
	 */
	public int getMaxIndex() {
		return viewports.length - 1;
	}

	/**
	 * 画像分割数を返します。
	 * @return
	 */
	public int getLength() {
		return viewports.length;
	}

	@Override
	public void changeIndex(int index) {
		setViewport(viewports[index]);
	}

	public Animation getAnimation() {
		return timeline;
	}
}