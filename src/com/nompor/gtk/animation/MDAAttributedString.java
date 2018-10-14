package com.nompor.gtk.animation;

import java.text.AttributedCharacterIterator;

public class MDAAttributedString extends AbstractMDAAttributedString<AttributedCharacterIterator.Attribute> {

	public MDAAttributedString(String text) {
		super(text.replaceAll("\\r\\n|\\r","\n"));
	}

}
