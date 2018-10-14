package com.nompor.gtk.awt.image;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import com.nompor.gtk.image.ImageFacotry;

public class ImageReader implements ImageFacotry<Image> {
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private MediaTracker mt;
	public ImageReader(Component c) {
		this.mt = new MediaTracker(c);
	}

	public Image readImage(String filePath) {
		Image img = tk.createImage(filePath);
		mt.addImage(img, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mt.removeImage(img);
		return img;
	}

	@Override
	public Image newImage(String filePath) {
		return readImage(filePath);
	}
}
