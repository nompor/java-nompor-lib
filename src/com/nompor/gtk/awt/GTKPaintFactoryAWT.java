package com.nompor.gtk.awt;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;

import com.nompor.gtk.GTKColor;
import com.nompor.gtk.GTKCycleMethod;
import com.nompor.gtk.GTKGradientParam;
import com.nompor.gtk.GTKPaint;
import com.nompor.gtk.GTKPaintFactory;

public class GTKPaintFactoryAWT implements GTKPaintFactory {
	@Override
	public GTKPaint createLinearGradient(int sx, int sy, int ex, int ey, GTKCycleMethod method, float[] fractions, GTKColor[] colors) {
		Color[] color = new Color[colors.length];
		for ( int i = 0;i < colors.length;i++ ) {
			color[i] = (Color) colors[i].getColor();
		}
		LinearGradientPaint grad = new LinearGradientPaint(sx, sy, ex, ey, fractions, color, toCycleMethod(method));
		return new GTKPaint(grad);
	}
	@Override
	public GTKPaint createLinearGradient(int sx, int sy, int ex, int ey, GTKCycleMethod method,
			GTKGradientParam... param) {
		Color[] c = new Color[param.length];
		float[] f = new float[param.length];
		for ( int i = 0;i < param.length;i++ ) {
			c[i] = (Color) param[i].color.getColor();
			f[i] = param[i].fraction;
		}
		LinearGradientPaint grad = new LinearGradientPaint(sx, sy, ex, ey, f, c, toCycleMethod(method));
		return new GTKPaint(grad);
	}
	@Override
	public GTKPaint createRadialGradient() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	@Override
	public GTKColor createColor(double r, double g, double b, double a) {
		return new GTKColorAWT(new Color((float)r,(float)g,(float)b,(float)a));
	}
	@Override
	public GTKColor createColor(double r, double g, double b) {
		// TODO 自動生成されたメソッド・スタブ
		return createColor(r,g,b,1);
	}
	@Override
	public GTKColor color(Object color) {
		if ( color instanceof Color ) {
			return new GTKColorAWT((Color) color);
		} else if ( color instanceof javafx.scene.paint.Color ) {
			javafx.scene.paint.Color cl = (javafx.scene.paint.Color)color;
			Color clr = new Color((float)cl.getRed(),(float)cl.getGreen(),(float)cl.getBlue(),(float)cl.getOpacity());
			return new GTKColorAWT(clr);
		}
		return null;
	}

	public MultipleGradientPaint.CycleMethod toCycleMethod(GTKCycleMethod method) {
		switch(method) {
		case NO_CYCLE:return CycleMethod.NO_CYCLE;
		case REFLECT:return CycleMethod.REFLECT;
		case REPEAT:return CycleMethod.REPEAT;
		}
		return null;
	}
}
