package com.nompor.gtk.awt.image;

import java.awt.Component;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import com.nompor.gtk.image.AbstractImageManager;

public class ImageManager extends AbstractImageManager<Image>{


	public ImageManager(Map<Object, Image> map, Component c) {
		super(map, new ImageReader(c));
	}

	public static ImageManager createInstance(Component c) {
		return new ImageManager(new HashMap<>(), c);
	}

	public void release(Image img) {
		img.flush();
	}
}
