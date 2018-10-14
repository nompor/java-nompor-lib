package com.nompor.gtk;

import com.nompor.gtk.animation.ChangeGTKViewDrawAnimation;
import com.nompor.gtk.draw.GTKGraphics;

public class GTKViewChanger implements GTKView {
	private GTKCanvas wnd;
	private GTKView nextView;
	private GTKView prevView;
	private ChangeGTKViewDrawAnimation screenChangeAnimation;
	private boolean isChangeInEvent;
	public GTKViewChanger(GTKView nextView, ChangeGTKViewDrawAnimation screenChangeAnimation) {
		this.nextView = nextView;
		this.screenChangeAnimation = screenChangeAnimation;
	}

	public void prepare(GTKCanvas wnd) {
		this.prevView = wnd.getGTKView();
		wnd.changeView(this);
		this.wnd = wnd;
		prevView.changeInStart();
	}

	@Override
	public void process() {
		screenChangeAnimation.update();
		if ( screenChangeAnimation.isEndOfChangeIn() ) {
			nextView.process();
		} else {
			prevView.process();
		}

		if ( !isChangeInEvent && screenChangeAnimation.isEndOfChangeIn() ) {
			isChangeInEvent = true;
			prevView.changeInEnd();
			nextView.changeOutStart();
		}
		if ( screenChangeAnimation.isEnd() ) {
			nextView.changeOutEnd();
			wnd.changeView(nextView);
		}
	}
	@Override
	public void draw(GTKGraphics g) {
		if ( screenChangeAnimation.isEndOfChangeIn() ) {
			nextView.draw(g);
		} else {
			prevView.draw(g);
		}
		screenChangeAnimation.draw(g);
	}
}
