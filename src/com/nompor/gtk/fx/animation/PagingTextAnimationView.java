package com.nompor.gtk.fx.animation;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;

public class PagingTextAnimationView extends Group {
	private ArrayList<PageEntry> list = new ArrayList<>();
	private int pageNo;
	private int maxAlreadyShowPageNo;
	private PauseTransition autoNextPagePause;
	private boolean isAutoNext;

	public PagingTextAnimationView() {

	}
	public PagingTextAnimationView(double x, double y) {
		setLayoutX(x);
		setLayoutY(y);
	}

	private class PageEntry{
		Animation anime;
		Node node;
		boolean end=false;
		PageEntry(TextAnimationView view){
			anime = view.getAnimation();
			node = view;
		}
		PageEntry(SequentialTextAnimationView view){
			anime = view.getAnimation();
			node = view;
		}
	}

	public void add(TextAnimationView textAnime) {
		list.add(new PageEntry(textAnime));
		getChildren().add(textAnime);
		textAnime.setVisible(false);
	}

	public void add(SequentialTextAnimationView textAnime) {
		list.add(new PageEntry(textAnime));
		getChildren().add(textAnime);
		textAnime.setVisible(false);
	}

	private void _nowPageIsEnd(boolean flg) {
		list.get(pageNo).end = flg;
	}

	public void doPauseNowPage() {
		list.get(pageNo).anime.pause();
	}

	public void doPlayNowPage() {
		PageEntry entry = list.get(pageNo);
		Animation anime = entry.anime;
		if ( autoNextPagePause != null ) {
			anime.setOnFinished(e -> {
				_nowPageIsEnd(true);
				if ( autoNextPagePause != null ) {
					autoNextPagePause.setOnFinished(e2 -> {
						if ( isAutoNext ) {
							nextPage();
						}
					});
					autoNextPagePause.stop();
					autoNextPagePause.play();
				}
			});
		} else {
			anime.setOnFinished(e -> _nowPageIsEnd(true));
		}
		entry.node.setVisible(true);
		if ( !entry.end ) anime.play();
	}

	public boolean isNowPagePlay() {
		Animation anime = list.get(pageNo).anime;
		return anime.getStatus() == Animation.Status.RUNNING;
	}

	public void doInitNowPage() {
		list.get(pageNo).anime.jumpTo("start");
		_nowPageIsEnd(false);
	}

	public void doFinalNowPage() {
		Animation anime = list.get(pageNo).anime;
		anime.pause();
		anime.jumpTo("end");
	}

	public void nextPage() {
		doPauseNowPage();
		list.get(pageNo).node.setVisible(false);
		if ( pageNo < getMaxPageNo() ) pageNo++;
		maxAlreadyShowPageNo = Math.max(pageNo, maxAlreadyShowPageNo);
		doPlayNowPage();
	}

	public void prevPage() {
		doPauseNowPage();
		list.get(pageNo).node.setVisible(false);
		if ( pageNo > 0 ) pageNo--;
		doPlayNowPage();
	}

	public boolean isNowPageEnd() {
		return list.get(pageNo).anime.getCurrentRate() == 1;
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

	public void setAutoNextPageDuration(Duration dur) {
		if ( dur == null ) {
			isAutoNext = false;
			if ( autoNextPagePause != null ) {
				PauseTransition anime = autoNextPagePause;
				autoNextPagePause = null;
				anime.stop();
			}
		} else {
			isAutoNext = true;
			if ( autoNextPagePause == null ) autoNextPagePause = new PauseTransition();
			this.autoNextPagePause.setDuration(dur);
		}
	}

	public Duration getAutoNextPageDuration() {
		if ( autoNextPagePause == null ) return null;
		return autoNextPagePause.getDuration();
	}

	public void autoPageNextOff() {
		setAutoNextPageDuration(null);
	}
}
