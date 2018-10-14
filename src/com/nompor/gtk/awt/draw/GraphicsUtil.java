package com.nompor.gtk.awt.draw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.nompor.gtk.animation.AnimationTarget;
import com.nompor.gtk.geom.Circle;
import com.nompor.gtk.geom.Point;
import com.nompor.gtk.geom.Polygon;
import com.nompor.gtk.geom.Rect;


public class GraphicsUtil {
	private static AffineTransform affine = new AffineTransform();

	/**
	 * アンチエイリアシングの有効無効を設定します。
	 * @param g
	 * @param isAntialiasing
	 */
	public static void setTextAntialiasing(Graphics g, boolean isAntialiasing){
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,isAntialiasing ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}
	public static void setAntialiasing(Graphics g, boolean isAntialiasing){
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,isAntialiasing?RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	public static void setDefaultAntialiasing(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT);
	}
	public static Object getTextAntialiasing(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		return g2.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
	}
	public static void setAlpha(Graphics g, float a) {
		Graphics2D g2 = (Graphics2D)g;
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a);
		g2.setComposite(alpha);
	}
	public static void setDefaultAlpha(Graphics g) {
		setAlpha(g, 1.0f);
	}
	public static void setRotate(Graphics g, double theta) {
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at = g2.getTransform();
		at.setToRotation(theta);
		g2.setTransform(at);
	}
	public static void setRotate(Graphics g, int angle) {
		setRotate(g, Math.toRadians(angle));
	}
	public static void setIdentity(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at = g2.getTransform();
		at.setToIdentity();
		g2.setTransform(at);
	}
	/**
	 * テキストを指定座標のなるべく中央に描画されるように描画します。
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 */
	public static void drawCenteringString(Graphics g, String text, int x, int y) {
		FontMetrics fm = g.getFontMetrics();
		g.drawString(text, x - fm.stringWidth(text) / 2, y + fm.getAscent() - fm.getHeight() / 2);
	}
	/**
	 * テキストを指定座標のなるべく中央に描画されるように描画します。
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 */
	public static void drawCenteringString(Graphics g, String text, int x, int y, Font f, Color color, AnimationTarget at) {
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform b = g2.getTransform();
		float a = ((AlphaComposite)g2.getComposite()).getAlpha();
		doAffine(at, x, y);
		GraphicsUtil.setAlpha(g2, (float)at.getOpacity());
		g2.setTransform(affine);
		Color bc = g.getColor();
		g.setColor(color == null ? bc : color);
		Font bf = g.getFont();
		g.setFont(f);
		GraphicsUtil.drawCenteringString(g, text, x, y);
		g.setFont(bf);
		g.setColor(bc);
		GraphicsUtil.setAlpha(g2, a);
		g2.setTransform(b);
	}

	public static void fillRect(Graphics g, int x, int y, int w, int h, Color color, AnimationTarget at) {
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform b = g2.getTransform();
		float a = ((AlphaComposite)g2.getComposite()).getAlpha();
		doAffine(at, x+w/2, y+h/2);
		g2.setTransform(affine);
		GraphicsUtil.setAlpha(g2, (float)at.getOpacity());
		Color bc = g.getColor();
		g.setColor(color == null ? bc : color);
		g.fillRect(x, y, w, h);
		g.setColor(bc);
		GraphicsUtil.setAlpha(g2, a);
		g2.setTransform(b);
	}

	public static void fillRect(Graphics g, Rect r, Color color, AnimationTarget at) {
		fillRect(g, r.getIX() , r.getIY(), r.getIWidth(), r.getIHeight(), color, at);
	}
	/**
	 * 画像を座標中央に描画します。
	 * @param g
	 * @param img
	 * @param x
	 * @param y
	 */
	public static void drawCenteringImage(Graphics g, Image img, int x, int y) {
		g.drawImage(img, x - img.getWidth(null) / 2, y - img.getHeight(null) / 2, null);
	}
	/**
	 * 画像を左右反転に描画します。
	 * @param g
	 * @param img
	 * @param x
	 * @param y
	 */
	public static void drawHInvertImage(Graphics g, Image img, int x, int y) {
		g.drawImage(img, x + img.getWidth(null), y, -img.getWidth(null), img.getHeight(null), null);
	}
	/**
	 * 画像を上下反転に描画します。
	 * @param g
	 * @param img
	 * @param x
	 * @param y
	 */
	public static void drawVInvertImage(Graphics g, Image img, int x, int y) {
		g.drawImage(img, x, y + img.getHeight(null), img.getWidth(null), -img.getHeight(null), null);
	}
	/**
	 * 画像を上下左右反転に描画します。
	 * @param g
	 * @param img
	 * @param x
	 * @param y
	 */
	public static void drawInvertImage(Graphics g, Image img, int x, int y) {
		g.drawImage(img, x + img.getWidth(null), y + img.getHeight(null), -img.getWidth(null), -img.getHeight(null), null);
	}
	/**
	 * 画像を上下左右反転状態で座標中央に描画します。
	 * @param g
	 * @param img
	 * @param x
	 * @param y
	 */
	public static void drawCenteringInvertImage(Graphics g, Image img, int x, int y) {
		g.drawImage(img, x + img.getWidth(null) / 2, y + img.getHeight(null) / 2, -img.getWidth(null), -img.getHeight(null), null);
	}
	/**
	 * 画像を左右反転状態で座標中央に描画します。
	 * @param g
	 * @param img
	 * @param x
	 * @param y
	 */
	public static void drawCHIImage(Graphics g, Image img, int x, int y) {
		g.drawImage(img, x + img.getWidth(null) / 2, y - img.getHeight(null) / 2, -img.getWidth(null), img.getHeight(null), null);
	}
	public static void drawImage(Graphics g, Image img, int dx, int dy, int dx2, int dy2, int sx, int sy, int sx2, int sy2, AnimationTarget at) {
		int cx = dx + (dx2 - dx) / 2;
		int cy = dy + (dy2 - dy) / 2;
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform b = g2.getTransform();
		float a = ((AlphaComposite)g2.getComposite()).getAlpha();
		doAffine(at, cx, cy);
		setAlpha(g2, (float)at.getOpacity());
		g2.setTransform(affine);
		g.drawImage(img, dx, dy, dx2, dy2, sx, sy, sx2, sy2, null);
		setAlpha(g2, a);
		g2.setTransform(b);
	}
	public static void drawImage(Graphics g, Image img, int x, int y, AnimationTarget at) {
		int cx = x + img.getWidth(null) / 2;
		int cy = y + img.getHeight(null) / 2;
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform b = g2.getTransform();
		float a = ((AlphaComposite)g2.getComposite()).getAlpha();
		doAffine(at, cx, cy);
		setAlpha(g2, (float)at.getOpacity());
		g2.setTransform(affine);
		g.drawImage(img, x, y, null);
		setAlpha(g2, a);
		g2.setTransform(b);
	}
	private static void doAffine(AnimationTarget at, int cx, int cy) {
		affine.setToIdentity();
		affine.translate(cx, cy);
		affine.rotate(at.getRotate());
		affine.scale(at.getScaleX(), at.getScaleY());
		affine.translate(at.getTranslateX()-cx, at.getTranslateY()-cy);
	}
	/**
	 * 画像を上下反転状態で座標中央に描画します。
	 * @param g
	 * @param img
	 * @param x
	 * @param y
	 */
	public static void drawCVIImage(Graphics g, Image img, int x, int y) {
		g.drawImage(img, x - img.getWidth(null) / 2, y + img.getHeight(null) / 2, img.getWidth(null), -img.getHeight(null), null);
	}
	public static Rectangle2D getStringBounds(Graphics g, String text) {
		return g.getFontMetrics().getStringBounds(text, g);
	}
	public static void draw(Graphics g, Circle circle) {
		int r = circle.getIRadius()*2;
		g.drawOval(circle.getILeft(), circle.getITop(), r, r);
	}
	public static void fill(Graphics g, Circle circle) {
		int r = circle.getIRadius()*2;
		g.fillOval(circle.getILeft(), circle.getITop(), r, r);
	}
	public static void draw(Graphics g, Rect rect) {
		g.drawRect(rect.getIX(), rect.getIY(), rect.getIWidth(), rect.getIHeight());
	}
	public static void fill(Graphics g, Rect rect) {
		g.fillRect(rect.getIX(), rect.getIY(), rect.getIWidth(), rect.getIHeight());
	}
	public static void draw(Graphics g, Polygon poly) {
		Point[] p = poly.points;
		int[] x = new int[p.length];
		int[] y = new int[p.length];
		for ( int  i = 0;i < p.length;i++ ) {
			x[i] = (int)p[i].x;
			y[i] = (int)p[i].y;
		}
		g.drawPolygon(x,y,p.length);
	}
	public static void fill(Graphics g, Polygon poly) {
		Point[] p = poly.points;
		int[] x = new int[p.length];
		int[] y = new int[p.length];
		for ( int  i = 0;i < p.length;i++ ) {
			x[i] = p[i].getIX();
			y[i] = p[i].getIY();
		}
		g.fillPolygon(x,y,p.length);
	}
	public static void draw(Graphics g, Point p) {
		g.fillRect(p.getIX()-1, p.getIY()-1, 2, 2);
	}
}
