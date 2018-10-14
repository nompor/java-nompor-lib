package com.nompor.gtk.awt;

import java.awt.Font;

import com.nompor.gtk.GTKFont;
import com.nompor.gtk.GTKFontFactory;
import com.nompor.gtk.GTKFontPosture;
import com.nompor.gtk.GTKFontWeight;

public class GTKFontFactoryAWT implements GTKFontFactory {
	@Override
	public GTKFont createFont(String name, GTKFontWeight weight, GTKFontPosture posture, int size) {
		return new GTKFont(new Font(name, toAWTWeight(weight) | toAWTPosture(posture), size));
	}

	private static int toAWTWeight(GTKFontWeight s) {
		switch(s) {
		case BOLD:return Font.BOLD;
		default:return Font.PLAIN;
		}
	}
	private static int toAWTPosture(GTKFontPosture s) {
		switch(s) {
		case ITALIC:return Font.ITALIC;
		default:return Font.PLAIN;
		}
	}
}
