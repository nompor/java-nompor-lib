package com.nompor.gtk.input;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.nompor.util.UnorderList;

/**
 * 何れかの入力イベントが発生した場合にtrueを返す論理和入力アダプター
 * @author nompor
 *
 * @param <K>
 */
public class OrInputAdapter<K> implements InputAdapter<K> {

	private UnorderList<Entry<K>> entries;

	@SuppressWarnings("unchecked")
	public OrInputAdapter(K... ks) {
		entries = new UnorderList<>(ks.length);
		for ( int i = 0;i < ks.length;i++ ) {
			Entry<K> entry = new Entry<>(ks[i]);
			entries.add(entry);
		}
	}

	@Override
	public boolean isPress() {
		return anyMatch(e -> e.isPress());
	}

	@Override
	public boolean isRelease() {
		return anyMatch(e -> e.isRelease());
	}

	@Override
	public boolean isDown() {
		return anyMatch(e -> e.isDown());
	}

	@Override
	public boolean isUp() {
		return anyMatch(e -> e.isUp());
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
}
