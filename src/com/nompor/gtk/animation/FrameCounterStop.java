package com.nompor.gtk.animation;

/**
 * 指定フレームカウントが経つまで処理を停止させるためのクラス
 * @author nompor
 *
 */
public class FrameCounterStop extends AbstractFrameCounterAnimation {
	public FrameCounterStop(int endCount) {
		super(endCount);
	}
	@Override
	public void updateFrame() {}
}
