package com.nompor.gtk.awt.animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.util.ArrayList;

import com.nompor.gtk.animation.AbstractAttributedMessageDrawableAnimation;
import com.nompor.gtk.animation.MDAAttributedString;

public class AttributedMessageDrawAnimation extends AbstractAttributedMessageDrawableAnimation<MDAAttributedString, Graphics> implements DrawAnimation {

	private ArrayList<PositionLayout> layoutList = new ArrayList<>();
	public double x;
	public double y;
	private boolean isCompile;

	public AttributedMessageDrawAnimation(double x, double y, int updateDelay) {
		super(updateDelay);
		this.x = x;
		this.y = y;
	}
	/**
	 * デフォルトの設定で描画を実行する文字列を追加します。
	 * @param str
	 */
	public void add(String text) {
		add(text, new Font(Font.MONOSPACED, Font.PLAIN, 12), Color.BLACK);
	}

	/**
	 * 指定されたフォントと色設定で描画を実行する文字列を追加します。
	 * @param str
	 */
	public void add(String text, Font font, Color color) {
		MDAAttributedString as = new MDAAttributedString(text);
		as.setAttribute(TextAttribute.FONT, font);
		as.setAttribute(TextAttribute.FOREGROUND, color);
		add(as);
	}

	public void compile(Graphics2D g) {
		FontRenderContext context = g.getFontRenderContext();
		float x = 0;
		float y = 0;
		int rootIdx = 0;
		float lineMaxAscent = 0;
		int layoutStIdx = 0;
		for ( int i = 0;i < dataList.size();i++ ) {
			MDAAttributedString e = dataList.get(i);
			int stIdx = 0;
			String text = e.getText();
			while(true) {
				int idx = text.indexOf('\n', stIdx);
				if ( idx == -1 ) break;
				PositionLayout layout = new PositionLayout();
				String s = text.substring(stIdx, idx);
				TextLayout tl = null;
				if ( s.length() == 0 ) {
					for ( int j = layoutStIdx;j < layoutList.size();j++ ) {
						layoutList.get(j).y = y + lineMaxAscent;
					}
					layoutStIdx = layoutList.size();
					TextLayout tl2 = new TextLayout(" ", e.getAttributes(), context);
					y += tl2.getDescent() + tl2.getLeading() + tl2.getAscent();
					rootIdx += idx - stIdx + 1;
					stIdx = idx + 1;
					x = 0;
					continue;
				}
				tl = new TextLayout(s, e.getAttributes(), context);
				lineMaxAscent = Math.max(tl.getAscent(), lineMaxAscent);
				y += lineMaxAscent;
				rootIdx += idx - stIdx + 1;
				layout.y = y;
				layout.x = x;
				layout.idx = rootIdx;
				layout.layout = tl;
				stIdx = idx + 1;
				for ( int j = layoutStIdx;j < layoutList.size();j++ ) {
					layoutList.get(j).y = layout.y;
				}
				y += tl.getDescent() + tl.getLeading();
				layoutList.add(layout);
				layoutStIdx = layoutList.size();
				lineMaxAscent = 0;
				x = 0;
			}
			PositionLayout layout = new PositionLayout();
			String s = text.substring(stIdx, text.length());
			if ( s.length() == 0 ) {
				continue;
			}
			TextLayout tl = new TextLayout(s, e.getAttributes(), context);
			lineMaxAscent = Math.max(tl.getAscent(), lineMaxAscent);
			rootIdx += text.length() - stIdx;
			layout.x = x;
			layout.idx = rootIdx;
			layout.layout = tl;
			x+=tl.getAdvance();
			layoutList.add(layout);
		}
		for ( int j = layoutStIdx;j < layoutList.size();j++ ) {
			layoutList.get(j).y = y + lineMaxAscent;
		}
		isCompile = true;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		if ( !isCompile ) compile(g2);
		int len = attrIndex + 1;

		int layoutIdx = 0;
		int lastTextStIdx = 0;
		for ( int i = 0;i < len;i++ ) {
			MDAAttributedString as = dataList.get(i);
			String text = as.getText();
			if ( attrIndex == i ) {
				text = currentText;
				while(layoutIdx < layoutList.size()) {
					PositionLayout layout = layoutList.get(layoutIdx);
					if(layout.idx >= currentText.length()) {
						if ( currentText.length() > 0 ) {
							new TextLayout(currentText.substring(lastTextStIdx), as.getAttributes(), g2.getFontRenderContext()).draw(g2, layout.x+(float)x, layout.y+(float)y);
						}
						break;
					}
					layoutIdx++;
					layout.layout.draw(g2, layout.x+(float)x, layout.y+(float)y);
					lastTextStIdx=layout.idx;
				}
			} else {
				while(layoutIdx < layoutList.size()) {
					PositionLayout layout = layoutList.get(layoutIdx);
					if(layout.idx >= text.length()) {
						break;
					}
					layoutIdx++;
					layout.layout.draw(g2, layout.x+(float)x, layout.y+(float)y);
					lastTextStIdx=layout.idx;
				}
			}
		}
	}

	private class PositionLayout{
		float x;
		float y;
		int idx;
		TextLayout layout;
	}
}
