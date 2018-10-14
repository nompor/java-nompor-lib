package com.nompor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 内部で取得カウンターを保持しておき、0になるとデータが削除されるMapクラス。
 * getメソッドでカウンター加算を行い、removeメソッドでカウンター減算が行われます。
 * @author nompor
 *
 * @param <K>
 * @param <V>
 */
public class UsedCounterMap<K, V> implements Map<K, V>{

	private HashMap<K, CounterEntry> map = new HashMap<>();

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object key) {
		CounterEntry entry = map.get(key);
		entry.cnt++;
		return entry.value;
	}

	public int getCount(Object key) {
		CounterEntry entry = map.get(key);
		return entry == null ? 0 : entry.cnt;
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public V put(K key, V value) {
		CounterEntry entry = new CounterEntry();
		entry.value = value;
		CounterEntry en = map.put(key, entry);
		return en == null ? null : en.value;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		map.forEach(this::put);
	}

	@Override
	public V remove(Object key) {
		CounterEntry entry = map.get(key);
		if ( entry != null ) {
			entry.cnt--;
			if ( entry.cnt <= 0 ) {
				map.remove(key);
				return entry.value;
			}
		}
		return null;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		Collection<V> arr = new ArrayList<>();
		map.forEach((k,v)->arr.add(v.value));
		return arr;
	}
	private class CounterEntry{
		private int cnt;
		private V value;
	}
}
