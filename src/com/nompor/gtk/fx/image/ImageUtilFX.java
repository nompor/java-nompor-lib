package com.nompor.gtk.fx.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.nompor.gtk.GTKException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageUtilFX {
	/**
	 * 元の画像に指定のエフェクトを適用し、新しい画像を作成します。
	 * @param target
	 * @param effect
	 * @param params
	 * @return
	 */
	public static Image toEffectorImage(Image target, Effect effect, SnapshotParameters params) {
		ImageView base = new ImageView(target);
		WritableImage newImage = new WritableImage((int)target.getWidth(), (int)target.getHeight());
		base.setEffect(effect);
		base.snapshot(params, newImage);
		return newImage;
	}
	/**
	 * 元の画像に指定のエフェクトを適用し、新しい画像を作成します。
	 * パラメータは自動で透明の塗りつぶしを指定します。
	 * @param target
	 * @param effect
	 * @return
	 */
	public static Image toEffectorImage(Image target, Effect effect) {
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		return toEffectorImage(target, effect, params);
	}

	/**
	 * JavaFX画像をファイルに書き出します。
	 * ファイル形式は引数fileの拡張子により自動判断されます。
	 * @param img
	 * @param file
	 */
	public static void writeImage(Image img, String file) {
		writeImage(img, file.substring(file.lastIndexOf("."),file.length()), new File(file));
	}

	/**
	 * JavaFX画像をファイルに書き出します。
	 * @param img
	 * @param type
	 * @param file
	 */
	public static void writeImage(Image img, String type, File file) {
		BufferedImage resultImg = new BufferedImage((int)img.getWidth(), (int)img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		SwingFXUtils.fromFXImage(img, resultImg);
		try {
			ImageIO.write(resultImg, type, file);
		} catch (IOException e) {
			throw new GTKException(e);
		}
	}

	public static Image readImage(File file) {
		return new Image(file.toURI().toString());
	}
}
