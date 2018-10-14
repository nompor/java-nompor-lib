package com.nompor.gtk;

public class GTKMath {
	/**
	 * 指定された範囲のランダム値を生成します。
	 * @param start
	 * @param end
	 * @return
	 */
	public static int randBetween(int start, int end) {
		int min = Math.min(start, end);
		return (int)(Math.random() * (Math.max(start, end) + 1 - min)) + min;
	}
	/**
	 * ベクトルの内積を計算します。
	 * @param vx1
	 * @param vy1
	 * @param vx2
	 * @param vy2
	 * @return
	 */
	public static double dot(double vx1, double vy1, double vx2, double vy2) {
		return vx1*vx2 + vy1*vy2;
	}
	/**
	 * ベクトルの外積を計算します。
	 * @param vx1
	 * @param vy1
	 * @param vx2
	 * @param vy2
	 * @return
	 */
	public static double cross(double vx1, double vy1, double vx2, double vy2) {
		return vx1*vy2 - vy1*vx2;
	}
	/**
	 * ベクトルの2乗を計算します。
	 * @param vx
	 * @param vy
	 * @return
	 */
	public static double vector2(double vx, double vy) {
		return vx*vx + vy*vy;
	}
	/**
	 * 二点間の距離の2乗を計算します。
	 * @param vx
	 * @param vy
	 * @return
	 */
	public static double vector2(double x1, double y1, double x2, double y2) {
		return vector2(x2-x1,y2-y1);
	}
	/**
	 * 二点間の距離を計算します。
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(vector2(x1,y1,x2,y2));
	}
}
