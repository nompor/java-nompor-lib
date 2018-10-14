package com.nompor.gtk.animation;

import java.util.HashMap;
import java.util.Map;

public class AbstractMDAAttributedString <A>{

	private String text;
	private Map<A,Object> attributes = new HashMap<>();
	/**
	 * 引数の文字を描画するためのAttributedStringオブジェクトを構築します。
	 * @param text 文字列
	 */
	public AbstractMDAAttributedString(String text){
		this.text = text;
	}
	/**
	 * 属性を付加します
	 * @param attr
	 * @param value
	 */
	public void setAttribute(A attr, Object value) {
		attributes.put(attr, value);
	}
	public Object getAttribute(A attr) {
		return attributes.get(attr);
	}
	public Object removeAttribute(A attr) {
		return attributes.remove(attr);
	}
	/**
	 * 文字列を取得します。
	 * @return
	 */
	public String getText() {
		return text;
	}
	public Map<? extends A,?> getAttributes(){
		return attributes;
	}
}
