package com.nompor.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class TimeRecorder {

	private LinkedList<BigDecimal> cache = new LinkedList<>();
	private long time = -1L;
	private long lastRecordTime = -1L;
	private long sum = 0L;
	private long avgCnt = 0;
	private BigDecimal bt = new BigDecimal(0);
	private int cacheBuffer;
	private final BigDecimal divide;
	private int recordingFrameCount=1;
	private int frameCount=0;
	private int recordingTime=0;
	public TimeRecorder(TimeUnit unit, int cacheBuffer, int recordingFrameCount){
		this.cacheBuffer =cacheBuffer;
		this.recordingFrameCount = recordingFrameCount;
		switch(unit) {
		case NANOSECONDS:divide = new BigDecimal("1");break;
		case MICROSECONDS:divide = new BigDecimal("1000");break;
		case MILLISECONDS:divide = new BigDecimal("1000000");break;
		case SECONDS:divide = new BigDecimal("1000000000");break;
		default:throw new IllegalArgumentException();
		}
	}
	public TimeRecorder(TimeUnit unit, int cacheBuffer){
		this(unit, cacheBuffer, 1);
	}
	public TimeRecorder(TimeUnit unit){
		this(unit, 10);
	}
	public TimeRecorder(){
		this(TimeUnit.MILLISECONDS, 1);
	}
	public static TimeRecorder createSimpleRecorder() {
		return new TimeRecorder();
	}
	public static TimeRecorder createAnimationRecorder() {
		return new TimeRecorder(TimeUnit.MILLISECONDS, 1, 60);
	}
	public void setRecordingFrameCount(int recordingFrameCount) {
		if ( this.recordingFrameCount <= 0 ) throw new IllegalArgumentException();
		this.recordingFrameCount = recordingFrameCount;
	}
	public int getRecordingFrameCount() {
		return recordingFrameCount;
	}
	public void startTime(){
		if ( time != -1 ) return;
		time = System.nanoTime();
	}
	public void endTime(){
		if ( time == -1 ) return;
		time = System.nanoTime() - time;
		recordingTime += time;
		if ( ++frameCount >= recordingFrameCount ) {
			sum += time;
			avgCnt++;
			lastRecordTime = recordingTime;
			this.bt = new BigDecimal(time).divide(divide,1,RoundingMode.HALF_UP);
			cache.offerFirst(bt);
			if ( cache.size() > cacheBuffer ) cache.removeLast();
			recordingTime = 0;
			frameCount = 0;
		}
		time = -1;
	}
	public BigDecimal[] getCacheList(){
		return cache.toArray(new BigDecimal[cache.size()]);
	}
	public BigDecimal getAverage(){
		return new BigDecimal(String.valueOf(sum)).divide(divide,1,RoundingMode.HALF_UP).divide(new BigDecimal(String.valueOf(avgCnt)),1,RoundingMode.HALF_UP);
	}
	public long getAverageCount() {
		return avgCnt;
	}
	public long getSumTime() {
		return sum;
	}
	public long getRecordTime() {
		return lastRecordTime;
	}
}
