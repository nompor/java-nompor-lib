package com.nompor.gtk;

public interface GTKPaintFactory {
	GTKPaint createLinearGradient(int sx, int sy, int ex, int ey, GTKCycleMethod method, GTKGradientParam... param);
	GTKPaint createLinearGradient(int sx, int sy, int ex, int ey, GTKCycleMethod method, float[] fractions, GTKColor[] colors);
	GTKPaint createRadialGradient();
	GTKColor color(Object color);
	GTKColor createColor(double r,double g,double b,double a);
	default GTKColor createColor(double r,double g,double b) {return createColor(r,g,b,1);}
	default GTKColor createIntColor(int r,int g,int b) {return createColor((double)r/255,(double)g/255,(double)b/255,1);}
}
