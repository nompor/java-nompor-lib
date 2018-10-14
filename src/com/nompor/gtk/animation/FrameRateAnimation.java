package com.nompor.gtk.animation;

import java.text.DecimalFormat;

public class FrameRateAnimation extends FrameRateCaster {

    private TextTarget target;
    private DecimalFormat fmt;
    public FrameRateAnimation(TextTarget target, int updateTimeMillis, String format){
    	super(updateTimeMillis);
    	this.target = target;
    	fmt = new DecimalFormat(format);
    }
    public FrameRateAnimation(TextTarget target, int updateTimeMillis){
    	this(target, updateTimeMillis, "00.0 fps");
    }

    public void update() {
    	if ( updateFrame() ) {
    		target.setText(fmt.format(getFrameRate()));
    	}
    }
}
