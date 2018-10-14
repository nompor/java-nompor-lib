package com.nompor.gtk.animation;

import java.util.ArrayList;

import com.nompor.gtk.GTKException;

public class AbstractMessageDrawablePagingAnimation<G> implements MessageDrawableAnimation<G> {

	public static final int AUTO_NEXT_PAGE_OFF=-1;

	private ArrayList<MessageDrawableAnimation<G>> list = new ArrayList<>();
	private int pageNo;
	private int maxAlreadyShowPageNo;
	private boolean isStart;
	private int endOfFrameCount;
	private int autoNextPageCount=AUTO_NEXT_PAGE_OFF;

	public void add(MessageDrawableAnimation<G> page) {
		if ( isStart ) throw new GTKException("already animation started.");
		list.add(page);
	}

	@Override
	public void update() {
		isStart = true;
		if ( isEnd() ) return;
		MessageDrawableAnimation<G> mda = list.get(pageNo);
		mda.update();
		if ( autoNextPageCount != AUTO_NEXT_PAGE_OFF && mda.isEnd() ) {
			endOfFrameCount++;
			if ( endOfFrameCount > autoNextPageCount ) {
				nextPage();
			}
		}
	}

	@Override
	public boolean isEnd() {
		return list.stream().allMatch(e -> e.isEnd());
	}

	public void nextPage() {
		isStart = true;
		endOfFrameCount = 0;
		if ( pageNo < getMaxPageNo() ) pageNo++;
		maxAlreadyShowPageNo = Math.max(pageNo, maxAlreadyShowPageNo);
	}

	public void prevPage() {
		isStart = true;
		endOfFrameCount = 0;
		if ( pageNo > 0 ) pageNo--;
	}

	public boolean isNowPageEnd() {
		return list.get(pageNo).isEnd();
	}

	public void doFinalNowPage() {
		list.get(pageNo).doFinal();
	}

	public void doInitNowPage() {
		list.get(pageNo).doInit();
	}

	public int getMaxAlreadyShowPageNo() {
		return maxAlreadyShowPageNo;
	}

	public int getPageNo() {
		return pageNo;
	}
	public int getMaxPageNo() {
		return list.size() - 1;
	}
	public int length() {
		return list.size();
	}
	public int getMinPageNo() {
		return 0;
	}

	public void doInitPageNo(int pageNo) {
		list.get(pageNo).doInit();
	}

	public void doFinalPageNo(int pageNo) {
		list.get(pageNo).doFinal();
	}

	@Override
	public void doFinal() {
		list.stream().forEach(e -> e.doFinal());
		pageNo = getMaxPageNo();
		maxAlreadyShowPageNo = pageNo;
		isStart = true;
	}

	@Override
	public void doInit() {
		list.stream().forEach(e -> e.doInit());
		pageNo = 0;
		maxAlreadyShowPageNo = 0;
		isStart = false;
	}

	@Override
	public void draw(G g) {
		list.get(pageNo).draw(g);
	}

	public boolean isStart() {
		return isStart;
	}

	public void setAutoNextPageDelayCount(int autoNextPageCount) {
		this.autoNextPageCount = autoNextPageCount;
	}

	public int getAutoNextPageDelayCount() {
		return autoNextPageCount;
	}
}
