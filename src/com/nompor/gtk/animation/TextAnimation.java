package com.nompor.gtk.animation;

public class TextAnimation extends AbstractFrameCounterAnimation {

	private int index;
	private TextTarget target;
	private String text;
	private boolean isEnd;
	public TextAnimation(TextTarget target, String text) {
		this.target = target;
		this.text = text;
	}

	@Override
	public void updateFrame() {
		target.setText(text.substring(0, index));
		index++;
		isEnd = text.equals(target.getText());
	}

	public boolean isEnd() {
		return super.isEnd() || isEnd;
	}
}
