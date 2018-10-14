package com.nompor.gtk.animation;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class AbstractAttributedMessageDrawableAnimation<A extends AbstractMDAAttributedString<?>, G> extends AbstractFrameCounterAnimation implements MessageDrawableAnimation<G>{

	protected ArrayList<A> dataList = new ArrayList<>();
	protected String currentText="";
	protected int attrIndex;

	private int index;
	private boolean isEnd;
	private int updateDelay;

	/**
	 * @param updateDelay メッセージ更新遅延フレーム数
	 */
	public AbstractAttributedMessageDrawableAnimation(int updateDelay){
		this.updateDelay = Math.max(updateDelay, 1);
	}

	/**
	 * 任意属性付で描画を実行する文字列を追加します。
	 * @param str
	 */
	public void add(A as) {
		if ( as == null ) return ;
		dataList.add(as);
	}

	public void updateFrame() {
		while ( isUpdateDelayFrame(updateDelay) && attrIndex < dataList.size() ) {
			A attrText = dataList.get(attrIndex);
			String text = attrText.getText();
			if ( index < text.length() ) {
				currentText += text.charAt(index);
				index++;
				break;
			} else {
				if ( attrIndex+1 >= dataList.size() ) {
					isEnd = true;
					break;
				}
				attrIndex++;
				index = 0;
			}
		}
	}

	/**
	 * メッセージをすべて表示し、アニメーションが終了したかを返します。
	 * @return
	 */
	public boolean isEnd() {
		return isEnd;
	}

	/**
	 * メッセージをすべて表示し、アニメーションを終了させます。
	 */
	public void doFinal() {
		currentText = String.join("", dataList.stream().map(e -> e.getText()).collect(Collectors.toList()));
		index = dataList.get(dataList.size() - 1).getText().length();
		attrIndex = dataList.size() - 1;
		isEnd = true;
	}

	/**
	 * このメッセージアニメーションを初期状態に設定します。
	 */
	public void doInit() {
		currentText = "";
		index = 0;
		attrIndex = 0;
		isEnd = false;
	}
}