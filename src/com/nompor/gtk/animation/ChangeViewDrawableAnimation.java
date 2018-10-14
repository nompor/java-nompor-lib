package com.nompor.gtk.animation;

import com.nompor.gtk.draw.Drawable;

public class ChangeViewDrawableAnimation<G> extends AbstractChangeViewAnimation implements DrawableAnimation<G>{

	private Drawable<G> changeIn;
	private Drawable<G> changeOut;

	public ChangeViewDrawableAnimation(DrawableAnimation<G> changeIn, DrawableAnimation<G> changeOut) {
		super(changeIn, changeOut);
		this.changeIn = changeIn;
		this.changeOut = changeOut;
	}

	@Override
	public void draw(G g) {
		if ( isEnd() ) return;
		if ( !isEndOfChangeIn() ) {
			changeIn.draw(g);
		} else if ( !isEndOfChangeOut() ) {
			changeOut.draw(g);
		}
	}
}
