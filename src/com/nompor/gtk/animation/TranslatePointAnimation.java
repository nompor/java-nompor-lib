package com.nompor.gtk.animation;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Arrays;

/**
 * @author nompor
 */
public class TranslatePointAnimation extends AbstractFrameCounterAnimation{
	private double x;
	private double y;
	private TranslateTarget target;
	private final int[] xpoints;
	private final int[] ypoints;
	private final int npoints;
	private boolean isInvert;
	private int currentPoint;
	private int nextPoint;
	private int speed;
	private double mx;
	private double my;
	private double checkValue;
	/**
	 * 指定した各座標を結ぶように移動するVertexMoveオブジェクトを構築します。
	 * @param xpoints x座標配列
	 * @param ypoints y座標配列
	 * @param npoints 頂点数
	 * @param speed 移動速度
	 * @param initPoint 初期位置を表す頂点インデックス
	 */
	public TranslatePointAnimation(TranslateTarget target,int[] xpoints, int[] ypoints, int npoints, int speed, int initPoint) {
		this.target = target;
		this.xpoints = xpoints;
		this.ypoints = ypoints;
		this.npoints = npoints;
		this.speed = Math.abs(speed);
		this.isInvert = speed < 0;
		this.currentPoint = isInvert ? initPoint + 1 : initPoint - 1;
		prepareNext();
	}
	/**
	 * 指定したRectangleオブジェクトの頂点を結ぶように移動するVertexMoveオブジェクトを構築します。
	 * @param rect 矩形オブジェクト
	 * @param speed 移動速度
	 * @param initPoint 初期位置を表す頂点インデックス
	 */
	public TranslatePointAnimation(TranslateTarget target,Rectangle rect, int speed, int initPoint) {
		this(target
			,new int[] {
					rect.x
					,rect.x+rect.width
					,rect.x+rect.width
					,rect.x
			}
			,new int[] {
					rect.y
					,rect.y
					,rect.y+rect.height
					,rect.y+rect.height
			}
			,4
			,speed
			,initPoint
		);
	}
	/**
	 * 指定したPolygonオブジェクトの頂点を結ぶように移動するVertexMoveオブジェクトを構築します。
	 * @param polygon 多角形オブジェクト
	 * @param speed 移動速度
	 * @param initPoint 初期位置を表す頂点インデックス
	 */
	public TranslatePointAnimation(TranslateTarget target,Polygon polygon, int speed, int initPoint) {
		this(target
			,Arrays.copyOf(polygon.xpoints, polygon.xpoints.length)
			,Arrays.copyOf(polygon.ypoints, polygon.ypoints.length)
			,polygon.npoints
			,speed
			,initPoint
		);
	}
	private void prepareNext() {
		if ( isInvert ) {
			int currentPoint = this.currentPoint - 1;
			if ( currentPoint < 0 ) currentPoint = npoints - 1;
			this.currentPoint = currentPoint;
			int nextPoint = currentPoint - 1;
			this.nextPoint = nextPoint < 0 ? npoints - 1 : nextPoint;
		} else {
			int currentPoint = this.currentPoint + 1;
			if ( currentPoint >= npoints ) currentPoint = 0;
			this.currentPoint = currentPoint;
			this.nextPoint = (currentPoint + 1) % npoints;
		}
		int sx = xpoints[currentPoint];
		int sy = ypoints[currentPoint];
		int ex = xpoints[nextPoint];
		int ey = ypoints[nextPoint];
		int vx = ex - sx;
		int vy = ey - sy;
		double rad = Math.atan2(vy, vx);
		mx = Math.cos(rad) * speed;
		my = Math.sin(rad) * speed;
		checkValue = vx * vx + vy * vy;
		x = sx;
		y = sy;
	}
	/**
	 * この頂点移動オブジェクトの頂点を結んだ図形を描画します。
	 * @param g 描画用オブジェクト
	 */
	public void drawPolygon(Graphics g) {
		g.drawPolygon(xpoints, ypoints, npoints);
	}
	/**
	 * この頂点移動オブジェクトの頂点を結んだ図形を塗りつぶします。
	 * @param g 描画用オブジェクト
	 */
	public void fillPolygon(Graphics g) {
		g.fillPolygon(xpoints, ypoints, npoints);
	}
	/**
	 * 移動処理を実行します。
	 */
	public void updateFrame() {
		double xx = x + mx;
		double yy = y + my;

		int sx = xpoints[currentPoint];
		int sy = ypoints[currentPoint];
		double vx = sx - xx;
		double vy = sy - yy;
		if ( checkValue <= vx * vx + vy * vy ) {
			prepareNext();
		} else {
			x = xx;
			y = yy;
		}
		target.setTranslateX(x);
		target.setTranslateY(y);
	}
	/**
	 * この頂点移動オブジェクトの移動方向を逆に設定します。
	 * 次のポイントまで移動した後に逆移動が開始されます。
	 */
	public void invert() {
		isInvert = !isInvert;
	}
	/**
	 * この頂点移動オブジェクトが逆移動に指定されているかどうかを返します。
	 * @return 逆移動かどうか
	 */
	public boolean isInvert() {
		return isInvert;
	}
	/**
	 * この頂点移動オブジェクトの移動速度を返します。
	 * @return 移動速度
	 */
	public int getSpeed() {
		return speed;
	}
	/**
	 * この頂点移動オブジェクトの移動速度を設定します。
	 * @param speed 移動速度
	 */
	public void setSpeed(int speed) {
		if ( speed < 0 ) {
			invert();
			speed = -speed;
		}
		this.speed = speed;
	}
	/**
	 * 現在のx座標を取得します。
	 * @return x座標
	 */
	public double getX() {
		return x;
	}
	/**
	 * 現在のy座標を取得します。
	 * @return y座標
	 */
	public double getY() {
		return y;
	}
}