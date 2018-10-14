package com.nompor.gtk.image;

import java.awt.Component;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.nompor.gtk.awt.draw.GTKGraphicsAWT;
import com.nompor.gtk.awt.image.ImageReader;
import com.nompor.gtk.draw.Drawable;
import com.nompor.gtk.draw.GTKGraphics;
import com.nompor.gtk.fx.draw.GTKGraphicsFX;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public abstract class GTKImageManager extends AbstractImageManager<GTKImage>{

	public GTKImageManager(Map<Object, GTKImage> map, ImageFacotry<GTKImage> factory) {
		super(map, factory);
	}

	public abstract GTKImage createImage(int width, int height,Drawable<GTKGraphics> draw);
	public GTKImage createImage(int width, int height) {
		return createImage(width, height, g -> {}) ;
	}

	private static class GTKImageFX implements GTKImage{

		Image img = null;
		public GTKImageFX (Image img){
			this.img = img;
		}

		@Override
		public int getWidth() {
			return (int) img.getWidth();
		}

		@Override
		public int getHeight() {
			return (int) img.getHeight();
		}

		@Override
		public Image getImage() {
			return img;
		}
	}

	private static class GTKImageAWT implements GTKImage{

		java.awt.Image img = null;
		public GTKImageAWT (java.awt.Image img){
			this.img = img;
		}

		@Override
		public int getWidth() {
			return (int) img.getWidth(null);
		}

		@Override
		public int getHeight() {
			return (int) img.getHeight(null);
		}

		@Override
		public java.awt.Image getImage() {
			return img;
		}
	}

	public static GTKImageManager createFXInstance() {
		return new GTKImageManager(new HashMap<>(), e -> {
			return new GTKImageFX(new Image(new File(e).toURI().toString()));
		}) {

			@Override
			public GTKImage createImage(int width, int height, Drawable<GTKGraphics> draw) {
				if ( draw != null ) {
					Canvas cvs = new Canvas(width,height);
					GTKGraphicsFX g = new GTKGraphicsFX(cvs);
					draw.draw(g);
					SnapshotParameters params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					return new GTKImageFX(cvs.snapshot(params, new WritableImage(width, height)));
				}
				return new GTKImageFX(new WritableImage(width, height));
			}
		};
	}

	public static GTKImageManager createInstance(Component c) {
		return new GTKImageManager(new HashMap<>(), new GTKImageReader(c)) {

			@Override
			public void release(GTKImage img) {
				((java.awt.Image)img.getImage()).flush();
			}

			@Override
			public GTKImage createImage(int width, int height, Drawable<GTKGraphics> draw) {
				if ( draw != null ) {
					java.awt.Image img = c.createImage(width, height);
					GTKGraphicsAWT g = new GTKGraphicsAWT(img.getGraphics());
					draw.draw(g);
					return new GTKImageAWT(img);
				}
				return new GTKImageAWT(c.createImage(width, height));
			}
		};
	}

	private static class GTKImageReader implements ImageFacotry<GTKImage>{

		private ImageReader reader;
		public GTKImageReader(Component c) {
			reader = new ImageReader(c);
		}

		@Override
		public GTKImage newImage(String filePath) {
			GTKImage img = new GTKImage() {

				java.awt.Image img = reader.readImage(filePath);

				@Override
				public int getWidth() {
					return img.getWidth(null);
				}

				@Override
				public int getHeight() {
					return img.getHeight(null);
				}

				@Override
				public java.awt.Image getImage() {
					return img;
				}

			};
			return img;
		}
	}
}
