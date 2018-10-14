package com.nompor.gtk.fx.animation;

import com.nompor.gtk.fx.event.IndexChangeListener;

import javafx.animation.Interpolator;
import javafx.beans.value.WritableValue;

public final class IndexAnimationProperty implements WritableValue<Integer> {

	private int index = 0;
	private IndexChangeListener listener;

	public IndexAnimationProperty(IndexChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public Integer getValue() {
		return index;
	}

	@Override
	public void setValue(Integer index) {
		boolean isChange = this.index != index;
		this.index = index;
		if ( isChange ) listener.changeIndex(index);
	}

	public static final Interpolator INDEX_LINEAR = new Interpolator() {

		@Override
		protected double curve(double arg0) {
			return arg0;
		}

		@Override
		public int interpolate(int startValue,int endValue,double fraction) {
	        return fraction == 1 ? endValue : startValue
	                + (int) Math.floor((endValue + 1 - startValue) * fraction);
		}

		@Override
		public Object interpolate(Object startValue,Object endValue,double fraction) {
	        return interpolate((int)(Integer)startValue, (int)(Integer)endValue, fraction);
		}
	};
}
