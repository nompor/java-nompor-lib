package com.nompor.gtk.fx;

import com.nompor.gtk.fx.animation.ChangeViewDrawAnimationFX;

import javafx.scene.canvas.GraphicsContext;

public class GameViewDrawChangerFX extends GameCanvasFX{
	private GameWindowFX wnd;
	private GameCanvasFX nextView;
	private GameCanvasFX prevView;
	private ChangeViewDrawAnimationFX screenChangeAnimation;
	private boolean isChangeInEvent;
	@SuppressWarnings("deprecation")
	public GameViewDrawChangerFX(GameCanvasFX nextView, ChangeViewDrawAnimationFX screenChangeAnimation) {
		this.nextView = nextView;
		this.screenChangeAnimation = screenChangeAnimation;
	}

	void prepare(GameWindowFX wnd,GameCanvasFX prevView) {
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
	public void draw(GraphicsContext g) {
		if ( screenChangeAnimation.isEndOfChangeIn() ) {
			nextView.draw();
		} else {
			prevView.draw();
		}
		screenChangeAnimation.draw(g);
	}
}
