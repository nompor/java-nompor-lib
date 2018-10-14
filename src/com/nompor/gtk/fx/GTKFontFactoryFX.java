package com.nompor.gtk.fx;

import com.nompor.gtk.GTKFont;
import com.nompor.gtk.GTKFontFactory;
import com.nompor.gtk.GTKFontPosture;
import com.nompor.gtk.GTKFontWeight;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class GTKFontFactoryFX implements GTKFontFactory {

	@Override
	public GTKFont createFont(String name, GTKFontWeight weight, GTKFontPosture posture, int size) {
		return new GTKFont(Font.font(name, toFXWeight(weight), toFXPosture(posture), size));
	}

	private static FontWeight toFXWeight(GTKFontWeight s) {
		switch(s) {
		case BOLD:return FontWeight.BOLD;
		default:return FontWeight.NORMAL;
		}
	}

	private static FontPosture toFXPosture(GTKFontPosture s) {
		switch(s) {
		case ITALIC:return FontPosture.ITALIC;
		default:return FontPosture.REGULAR;
		}
	}
}
