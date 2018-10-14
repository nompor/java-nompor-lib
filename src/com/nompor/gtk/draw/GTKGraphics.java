package com.nompor.gtk.draw;

import com.nompor.gtk.GTKColor;
import com.nompor.gtk.GTKFont;
import com.nompor.gtk.GTKPaint;
import com.nompor.gtk.image.GTKImage;

public interface GTKGraphics {
	void strokeRect(double x, double y, double w, double h);
	default void drawRect(double x, double y, double w, double h) {strokeRect(x,y,w,h);}
	void fillRect(double x, double y, double w, double h);
	void strokeOval(double x, double y, double w, double h);
	default void drawOval(double x, double y, double w, double h) {strokeOval(x,y,w,h);}
	void fillOval(double x, double y, double w, double h);
	void strokeLine(double x1, double y1, double x2, double y2);
	default void drawLine(double x1, double y1, double x2, double y2) {strokeLine(x1,y1,x2,y2);}
	void drawImage(GTKImage img, double x, double y, double w, double h);
	void drawImage(GTKImage img, double x, double y);
	default void setColor(Object clr) {setPaint(clr);}
	void drawImage(GTKImage img, double sx, double sy, double sw, double sh, double dx, double dy, double dw, double dh);
	void setPaint(Object clr);
	default void setColor(GTKColor clr) {setPaint(clr.getColor());}
	default void setPaint(GTKPaint clr) {setPaint(clr.getPaint());}
	void setFont(Object font);
	default void setFont(GTKFont font) {setFont(font.getFont());}
	void drawString(String text, double x, double y);
	double stringWidth(String text);
	double stringHeight(String text);
	void setOpacity(double opacity);
	default void setAlpha(double alpha) {setOpacity(alpha);}
	void setTextAntialias(boolean isAntialias);
	boolean isTextAntialias();
}
