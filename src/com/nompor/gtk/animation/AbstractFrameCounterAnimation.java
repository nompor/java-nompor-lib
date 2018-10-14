package com.nompor.gtk.animation;

public abstract class AbstractFrameCounterAnimation implements Animation {
	public static final int INFINITE=Integer.MAX_VALUE;
	private int frameCount;
	private int endFramePoint=INFINITE;

	public AbstractFrameCounterAnimation() {}

	public AbstractFrameCounterAnimation(int endFramePoint) {
		setEndFramePoint(endFramePoint);
	}

	public int getFrameCount() {
		return frameCount;
	}

	public boolean isEnd() {
		return endFramePoint <= frameCount;
	}

	protected void setFrameCount(int frameCount) {
		this.frameCount = frameCount;
	}

	public void update() {
		if ( isEnd() ) return;
		frameCount++;
		updateFrame();
	}

	public int getEndFramePoint() {
		return endFramePoint;
	}

	public void setEndFramePoint(int endFramePoint) {
		this.endFramePoint = endFramePoint;
	}

	/**
	 * 遅延更新用メソッド
	 * @param delay 遅延フレーム
	 * @return
	 */
	protected boolean isUpdateDelayFrame(int delay) {
		return frameCount % delay == 0;
	}
	/**
	 * 遅延更新用メソッド
	 * @param delay 遅延フレーム
	 * @param updatePoint 更新フレーム
	 * @return
	 */
	protected boolean isUpdateDelayFrame(int delay, int updatePoint) {
		return frameCount % delay == updatePoint;
	}

	public abstract void updateFrame();
}
