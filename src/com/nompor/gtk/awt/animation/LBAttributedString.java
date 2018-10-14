package com.nompor.gtk.awt.animation;

import java.text.AttributedCharacterIterator;

import com.nompor.gtk.animation.AbstractMDAAttributedString;

/**
 * 書式付文字列クラスです。
 * 文字列に任意の属性を付加したい場合に利用できます。
 */
public class LBAttributedString extends AbstractMDAAttributedString<AttributedCharacterIterator.Attribute>{

	public LBAttributedString(String text) {
		super(text.replaceAll("\\r\\n|\\r","\n"));
	}
}