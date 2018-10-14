package com.nompor.gtk.fx.animation;

import com.nompor.gtk.animation.MessageDrawableAnimation;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class MessageDrawPagingAnimationCanvasFX extends Canvas{

	private MessageDrawPagingAnimationFX paging = new MessageDrawPagingAnimationFX();
	private boolean isAutoClear=true;
	public MessageDrawPagingAnimationCanvasFX(double w, double h) {
		setSize(w, h);
	}
	public MessageDrawPagingAnimationCanvasFX(){
		this(0,0);
	}

	public void add(MessageDrawableAnimation<GraphicsContext> page) {
		paging.add(page);
	}

	public void setSize(double w, double h) {
		setWidth(w);
		setHeight(h);
	}

	private void clear(GraphicsContext g) {
		g.clearRect(0, 0, getWidth(), getHeight());
	}

	public void clear() {
		clear(getGraphicsContext2D());
	}

	public void update() {
		paging.update();
		GraphicsContext g = getGraphicsContext2D();
		if ( isAutoClear ) clear(g);
		paging.draw(getGraphicsContext2D());
	}

	public boolean isEnd() {
		return paging.isEnd();
	}

	public void nextPage() {
		paging.nextPage();
	}

	public void prevPage() {
		paging.prevPage();
	}

	public boolean isNowPageEnd() {
		return paging.isNowPageEnd();
	}

	public int getMaxAlreadyShowPageNo() {
		return paging.getMaxAlreadyShowPageNo();
	}

	public int getPageNo() {
		return paging.getPageNo();
	}

	public int getMaxPageNo() {
		return paging.getMaxPageNo();
	}

	public int length() {
		return paging.length();
	}

	public int getMinPageNo() {
		return paging.getMinPageNo();
	}

	public void doFinal() {
		paging.doFinal();
	}

	public void doInit() {
		paging.doInit();
	}

	public boolean isStart() {
		return paging.isStart();
	}

	public void setAutoNextPageDelayCount(int autoNextPageCount) {
		paging.setAutoNextPageDelayCount(autoNextPageCount);
	}

	public int getAutoNextPageDelayCount() {
		return paging.getAutoNextPageDelayCount();
	}
	public boolean isAutoClear() {
		return isAutoClear;
	}
	public void setAutoClear(boolean isAutoClear) {
		this.isAutoClear = isAutoClear;
	}

	public boolean isAutoPageNext() {
		return paging.getAutoNextPageDelayCount() == MessageDrawPagingAnimationFX.AUTO_NEXT_PAGE_OFF;
	}

	public void autoPageNextOff() {
		paging.setAutoNextPageDelayCount(MessageDrawPagingAnimationFX.AUTO_NEXT_PAGE_OFF);
	}

	public void doInitPageNo(int pageNo) {
		paging.doInitPageNo(pageNo);
	}
	public void doFinalPageNo(int pageNo) {
		paging.doFinalPageNo(pageNo);
	}
	public void doFinalNowPage() {
		paging.doFinalNowPage();
	}
	public void doInitNowPage() {
		paging.doInitNowPage();
	}
}
