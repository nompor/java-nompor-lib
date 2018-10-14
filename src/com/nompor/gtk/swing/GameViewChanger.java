package com.nompor.gtk.swing;

import java.awt.Graphics;

import com.nompor.gtk.awt.animation.ChangeViewDrawAnimation;

public class GameViewChanger extends GameView {
	private GameWindow wnd;
	private GameView nextView;
	private GameView prevView;
	private ChangeViewDrawAnimation screenChangeAnimation;
	private boolean isChangeInEvent;
	public GameViewChanger(GameView nextView, ChangeViewDrawAnimation screenChangeAnimation) {
		this.nextView = nextView;
		this.screenChangeAnimation = screenChangeAnimation;
	}

	void prepare(GameWindow wnd,GameView prevView) {
		wnd.changeView(this);
		this.wnd = wnd;
		this.prevView = prevView;
	}

	@Override
	public void process() {
		screenChangeAnimation.update();
		if ( !isChangeInEvent && screenChangeAnimation.isEndOfChangeIn() ) {
			isChangeInEvent = true;
		}
		if ( screenChangeAnimation.isEnd() ) {
			wnd.changeView(nextView);
		}
	}
	@Override
	public void draw(Graphics g) {
		if ( screenChangeAnimation.isEndOfChangeIn() ) {
			nextView.draw(g);
		} else {
			prevView.draw(g);
		}
		screenChangeAnimation.draw(g);
	}
}
