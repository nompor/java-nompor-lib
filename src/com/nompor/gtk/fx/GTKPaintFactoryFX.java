package com.nompor.gtk.fx;


import java.util.Arrays;
import java.util.stream.Collectors;

import com.nompor.gtk.GTKColor;
import com.nompor.gtk.GTKCycleMethod;
import com.nompor.gtk.GTKGradientParam;
import com.nompor.gtk.GTKPaint;
import com.nompor.gtk.GTKPaintFactory;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class GTKPaintFactoryFX implements GTKPaintFactory {

	@Override
	public GTKPaint createLinearGradient(int sx, int sy, int ex, int ey, GTKCycleMethod method, float[] fractions, GTKColor[] colors) {
		Stop[] stops=new Stop[fractions.length];
		for ( int i = 0;i < fractions.length;i++ ) {
			stops[i] = new Stop(fractions[i], (Color) colors[i].getColor());
		}
		LinearGradient grad = new LinearGradient(sx, sy, ex, ey, false, toCycleMethod(method), stops);
		return new GTKPaint(grad);
	}

	@Override
	public GTKPaint createLinearGradient(int sx, int sy, int ex, int ey, GTKCycleMethod method, GTKGradientParam... param) {
		return new GTKPaint(new LinearGradient(sx, sy, ex, ey, false, toCycleMethod(method)
				, Arrays.stream(param).map(e -> new Stop(e.fraction, (Color)e.color.getColor())).collect(Collectors.toList())));
	}

	@Override
	public GTKPaint createRadialGradient() {
		return null;//return GTKGradientFX.radial(cx, cy, radius, fraction, colors);
	}

	@Override
	public GTKColor createColor(double r, double g, double b, double a) {
		return new GTKColorFX(new Color(r,g,b,a));
	}

	@Override
	public GTKColor createColor(double r, double g, double b) {
		return createColor(r, g, b, 1);
	}

	@Override
	public GTKColor color(Object color) {
		if ( color instanceof Color ) {
			return new GTKColorFX((Color) color);
		} else if ( color instanceof java.awt.Color ) {
			java.awt.Color cl = (java.awt.Color)color;
			Color clr = new Color((double)cl.getRed()/255,(double)cl.getGreen()/255,(double)cl.getBlue()/255,(double)cl.getAlpha()/255);
			return new GTKColorFX(clr);
		}
		return null;
	}

	public CycleMethod toCycleMethod(GTKCycleMethod method) {
		switch(method) {
		case NO_CYCLE:return CycleMethod.NO_CYCLE;
		case REFLECT:return CycleMethod.REFLECT;
		case REPEAT:return CycleMethod.REPEAT;
		}
		return null;
	}
}
