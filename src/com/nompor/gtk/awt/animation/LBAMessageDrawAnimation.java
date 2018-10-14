package com.nompor.gtk.awt.animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Map;

import com.nompor.gtk.animation.AbstractAttributedMessageDrawableAnimation;
import com.nompor.gtk.animation.AbstractMDAAttributedString;
import com.nompor.gtk.geom.Rect;

/**
 * このクラスはLineBreakMeasureクラスを使用した自動折り返しメッセージアニメーションを実現するクラスです。
 *
 */
public class LBAMessageDrawAnimation extends AbstractAttributedMessageDrawableAnimation<LBAttributedString,Graphics> implements DrawAnimation{
	private AttributedString ensureCache;
	private Rect rect;
	private Position[] posList;
	private int outLinePadding=0;
	private boolean isCompile;

	/**
	 * 引数の描画領域と設定を利用してメッセージ描画オブジェクトを構築します。
	 * @param rect 描画領域
	 * @param speed メッセージスピード
	 * @param outLinePadding 矩形描画する際の外側に広げる量
	 */
	public LBAMessageDrawAnimation(Rectangle rect, int updateDelay, int outLinePadding){
		this(rect.x,rect.y,rect.width, rect.height, updateDelay, outLinePadding);
	}

	/**
	 * 引数の描画領域と設定を利用してメッセージ描画オブジェクトを構築します。
	 * @param rect 描画領域
	 * @param speed メッセージスピード
	 * @param outLinePadding 矩形描画する際の外側に広げる量
	 */
	public LBAMessageDrawAnimation(Rect rect, int updateDelay, int outLinePadding){
		super(updateDelay);
		this.rect = rect;
		this.outLinePadding = outLinePadding;
	}

	/**
	 * 引数の描画領域と設定を利用してメッセージ描画オブジェクトを構築します。
	 * @param rect 描画領域
	 * @param speed メッセージスピード
	 * @param outLinePadding 矩形描画する際の外側に広げる量
	 */
	public LBAMessageDrawAnimation(double x, double y, double w, double h, int updateDelay, int outLinePadding){
		this(new Rect(x,y,w,h),updateDelay, outLinePadding);
	}

	/**
	 * 引数の描画領域と設定を利用してメッセージ描画オブジェクトを構築します。
	 * @param rect 描画領域
	 * @param speed メッセージスピード
	 */
	public LBAMessageDrawAnimation(Rectangle rect, int speed){
		this(rect, speed, 0);
	}

	/**
	 * 引数の描画領域と設定を利用してメッセージ描画オブジェクトを構築します。
	 * @param rect 描画領域
	 * @param speed メッセージスピード
	 */
	public LBAMessageDrawAnimation(Rect rect, int speed){
		this(rect, speed, 0);
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
		LBAttributedString as = new LBAttributedString(text);
		as.setAttribute(TextAttribute.FONT, font);
		as.setAttribute(TextAttribute.FOREGROUND, color);
		add(as);
	}

	/**
	 * 現在の設定で確定し、必要な情報を計算します。
	 * @param g
	 */
	public void compile(Graphics g) {

		final StringBuilder sb = new StringBuilder();
		int len = dataList.size();
		int startIndex[] = new int[len];
		int endIndex[] = new int[len];
		ArrayList<Map<? extends Attribute, ?>> attrs = new ArrayList<>(len);
		for ( int i = 0;i < dataList.size();i++ ) {
			AbstractMDAAttributedString<Attribute> as = dataList.get(i);
			if ( i == 0 ) {
				startIndex[i] = 0;
				endIndex[i] = as.getText().length();
			} else {
				startIndex[i] = endIndex[i-1];
				endIndex[i] = endIndex[i-1] + as.getText().length();
			}
			attrs.add(as.getAttributes());
			sb.append(as.getText());
		}

		String text = sb.toString();
		AttributedString as2 = new AttributedString(text);
		for ( int i = 0;i < len;i++ ) {
			as2.addAttributes(attrs.get(i), startIndex[i], endIndex[i]);
		}
		Graphics2D g2 = (Graphics2D)g;

		FontRenderContext context = g2.getFontRenderContext();
		LineBreakMeasurer measurer = new LineBreakMeasurer(as2.getIterator(), context);

		int position;
		int wrappingWidth = rect.getIWidth();
		float dy=0;
		float dx=0;
		float y = (float)rect.y;
		float x = (float)rect.x;

		ArrayList<Position> arr = new ArrayList<>();

		// 文字列の最後まで
		while ((position = measurer.getPosition()) < text.length()) {

			TextLayout layout;

			// 改行検索
			int indexOf = text.indexOf("\n", position);

			if (position <= indexOf && indexOf < measurer.nextOffset(wrappingWidth)) {
				// 改行位置の手前の分まで取得
				layout = measurer.nextLayout(wrappingWidth, ++indexOf, false);
			} else {
				// 自動で折り返してるとこまで取得
				layout = measurer.nextLayout(wrappingWidth);
				indexOf = measurer.getPosition();
			}

			if (layout == null) {
				break;
			}

			dy += layout.getAscent();

			dx = layout.isLeftToRight() ? 0 : (wrappingWidth - layout
					.getAdvance());

			// 文字列を書きだす位置情報
			Position pos = new Position();

			//描画位置の算出
			pos.x = x + dx;
			pos.y = y + dy;
			pos.idx = indexOf;
			arr.add(pos);

			dy += layout.getDescent() + layout.getLeading();
		}
		this.posList = arr.toArray(new Position[arr.size()]);

		isCompile = true;
	}

	/**
	 * 文字列を描画します。
	 * @param g
	 */
	public void draw(Graphics g) {
		final Graphics2D g2 = (Graphics2D)g;
		String text = currentText;
		if ( text.length() > 0 ) {
			if ( !isEnd() ) {
				AttributedString result = new AttributedString(text);
				for ( int i = 0,index=0;i < attrIndex+1;i++ ) {
					AbstractMDAAttributedString<Attribute> as = dataList.get(i);
					int len = as.getText().length() + index;
					if ( len > text.length() ) {
						len = text.length();
					}
					result.addAttributes(as.getAttributes(), index, len);
					index = len;
				}
				ensureCache = result;
			}
			_drawString(g2, ensureCache, text);
		}
	}

	private void _drawString(Graphics2D g2, AttributedString as, String text) {

		if ( text == null || text.length() <= 0 ) return;

		if ( !isCompile ) {
			compile(g2);
		}

		int wrappingWidth = rect.getIWidth();

		FontRenderContext context = g2.getFontRenderContext();

		LineBreakMeasurer measurer = new LineBreakMeasurer(as.getIterator(), context);

		for (int i = 0;(measurer.getPosition()) < text.length();i++) {
			TextLayout layout;

			Position pos = posList[i];

			int indexOf = pos.idx;

			//次のindexが現在の文字列より長かった場合は文字の長さにする
			if (text.length() < indexOf) {
				layout = measurer.nextLayout(wrappingWidth, text.length(), false);
			}else {
				layout = measurer.nextLayout(wrappingWidth, indexOf, false);
			}

			//取得不能か、矩形領域からはみ出たら処理を終了
			if (layout == null || pos.y + layout.getDescent()
			 + layout.getLeading() > rect.y + rect.getIHeight()) {
				break;
			}

			// 文字列の描画
			layout.draw(g2, pos.x, pos.y);
		}
	}

	/**
	 * このメッセージアニメーションの枠を描画します。
	 * @param g
	 */
	public void drawRect(Graphics g) {
		g.drawRect(
				rect.getIX()-outLinePadding
				,rect.getIY()-outLinePadding
				,rect.getIWidth()+outLinePadding+outLinePadding
				,rect.getIHeight()+outLinePadding+outLinePadding
		);
	}

	/**
	 * このメッセージアニメーションの枠を塗りつぶします。
	 * @param g
	 */
	public void fillRect(Graphics g) {
		g.fillRect(
				rect.getIX()-outLinePadding
				,rect.getIY()-outLinePadding
				,rect.getIWidth()+outLinePadding+outLinePadding
				,rect.getIHeight()+outLinePadding+outLinePadding
		);
	}

	/**
	 * このメッセージアニメーションの枠を角を丸めて描画します。
	 * @param g
	 */
	public void drawRoundRect(Graphics g) {
		g.drawRoundRect(
				rect.getIX()-outLinePadding
				,rect.getIY()-outLinePadding
				,rect.getIWidth()+outLinePadding+outLinePadding
				,rect.getIHeight()+outLinePadding+outLinePadding
				, 10
				, 10
		);
	}

	/**
	 * このメッセージアニメーションの枠を角を丸めて塗りつぶします。
	 * @param g
	 */
	public void fillRoundRect(Graphics g) {
		g.fillRoundRect(
				rect.getIX()-outLinePadding
				,rect.getIY()-outLinePadding
				,rect.getIWidth()+outLinePadding+outLinePadding
				,rect.getIHeight()+outLinePadding+outLinePadding
				, 10
				, 10
		);
	}

	private class Position{
		float x;
		float y;
		int idx;
	}
}