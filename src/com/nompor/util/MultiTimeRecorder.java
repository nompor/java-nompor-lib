package com.nompor.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MultiTimeRecorder {

	private TimeUnit unit;
	private int cacheBuffer;
	private int recordingFrameCount;
    private LinkedHashMap<Object, TimeRecorder> recorders = new LinkedHashMap<>();

    public MultiTimeRecorder(TimeUnit unit, int cacheBuffer, int recordingFrameCount) {
    	this.unit = unit;
    	this.cacheBuffer = cacheBuffer;
    	this.recordingFrameCount = recordingFrameCount;
    }
    public MultiTimeRecorder(TimeUnit unit, int cacheBuffer) {
    	this(unit, cacheBuffer, 1);
    }
    public MultiTimeRecorder() {
    	this(TimeUnit.MILLISECONDS, 10);
    }
    public static MultiTimeRecorder createAnimationRecorder() {
    	return new MultiTimeRecorder(TimeUnit.MILLISECONDS, 10, 60);
    }
	public void startRecord(Object key){
		TimeRecorder t = recorders.get(key);
		if ( t == null ) {
			t = new TimeRecorder(unit, cacheBuffer, recordingFrameCount);
			recorders.put(key,t);
			onCreateTimeRecord(key, t);
		}
		onReadyStartRecord(key, t);
		t.startTime();
	}
	public void endRecord(Object key){
		TimeRecorder t = recorders.get(key);
		if ( t == null ) return;
		t.endTime();
		onEndRecord(key, t);
	}
	public Set<Map.Entry<Object, TimeRecorder>> entrySet(){
		return recorders.entrySet();
	}
	public Set<Object> keySet(){
		return recorders.keySet();
	}
	public TimeRecorder get(Object key){
		return recorders.get(key);
	}
	public TimeUnit getTimeUnit() {
		return unit;
	}
	public int getCacheBuffer() {
		return cacheBuffer;
	}
	public void onCreateTimeRecord(Object key, TimeRecorder recorder) {}
	public void onReadyStartRecord(Object key, TimeRecorder recorder) {}
	public void onEndRecord(Object key, TimeRecorder recorder) {}
}
