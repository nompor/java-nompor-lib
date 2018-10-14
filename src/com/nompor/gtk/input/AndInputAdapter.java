package com.nompor.gtk.input;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.nompor.util.UnorderList;

/**
 * すべての入力イベントが発生した時にtrueを返すシンプルな論理積入力アダプター
 * @author nompor
 *
 * @param <K>
 */
public class AndInputAdapter<K> implements InputAdapter<K> {

	private UnorderList<Entry<K>> entries;

	@SuppressWarnings("unchecked")
	public AndInputAdapter(K... ks) {
		entries = new UnorderList<>(ks.length);
		for ( int i = 0;i < ks.length;i++ ) {
			Entry<K> entry = new Entry<>(ks[i]);
			entries.add(entry);
		}
	}

	@Override
	public boolean isPress() {
		return isDown() && anyMatch(e -> e.isPress());
	}

	@Override
	public boolean isRelease() {
		return isUp() && anyMatch(e -> e.isRelease());
	}

	@Override
	public boolean isDown() {
		return allMatch(e -> e.isDown());
	}

	@Override
	public boolean isUp() {
		return allMatch(e -> e.isUp());
	}

	@Override
	public void forEach(Consumer<Entry<K>> con) {
		entries.forEach(con);
	}

	private boolean anyMatch(Predicate<InputInfo> p) {
		for ( int i = 0;i < entries.size();i++ ) {
			Entry<K> entry = entries.get(i);
			if ( p.test(entry.info) ) {
				return true;
			}
		}
		return false;
	}

	private boolean allMatch(Predicate<InputInfo> p) {
		if ( entries.size() <= 0 ) return false;
		for ( int i = 0;i < entries.size();i++ ) {
			Entry<K> entry = entries.get(i);
			if ( !p.test(entry.info) ) {
				return false;
			}
		}
		return true;
	}
}
