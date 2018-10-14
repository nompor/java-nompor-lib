package com.nompor.gtk.fx.image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.nompor.gtk.image.AbstractImageManager;

import javafx.scene.image.Image;

public class ImageManagerFX extends AbstractImageManager<Image>{
	public ImageManagerFX(Map<Object, Image> map) {
		super(map, e -> new Image(new File(e).toURI().toString()));
	}

	public static ImageManagerFX createFXInstance() {
		return new ImageManagerFX(new HashMap<>());
	}
}