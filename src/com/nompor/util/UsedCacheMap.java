package com.nompor.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class UsedCacheMap<K,V> implements Map<K, V>{

	private TreeMap<Long,CacheNode> timeMap = new TreeMap< >();
	private Map<K,CacheNode> map;
	private int cacheNum;

	/**
	 * 指定されたキャッシュ数とマップを使用したキャッシュマップを構築します。
	 * @param cacheNum
	 * @param map
	 */
	private UsedCacheMap(int cacheNum, Map<K, CacheNode> map) {
		this.cacheNum = cacheNum;
		this.map = map;
	}

	/**
	 * キャッシュ機能を実装するMapオブジェクトを構築します。
	 * @param cacheNum 強参照状態で保持するキャッシュオブジェクト数
	 */
	public UsedCacheMap(int cacheNum) {
		this(cacheNum, new HashMap<>());
	}

	@Override
	public void clear() {
		timeMap.clear();
		map.clear();
	}

	@Override
	public V get(Object arg0) {
		CacheNode v = map.get(arg0);
		timeMap.remove(v.time);
		v.time = System.nanoTime();
		timeMap.put(v.time, v);
		return v.value;
	}

	@Override
	public V put(K arg0, V arg1) {
		CacheNode node = new CacheNode();
		node.key = arg0;
		node.value = arg1;
		node.time = System.nanoTime();
		CacheNode oldNode = timeMap.put(node.time, node);
		if (oldNode!=null) {
			timeMap.put(node.time, oldNode);
			throw new RuntimeException("ライブラリのバグによりうまく処理できません。");
		}
		CacheNode v = map.put(arg0, node);
		if ( v != null ) {
			timeMap.remove(v.time);
		} else if ( map.size() > cacheNum ) {
			Entry<Long, UsedCacheMap<K, V>.CacheNode> entry = timeMap.firstEntry();
			CacheNode removeNode = entry.getValue();
			v = map.remove(removeNode.key);
		}
		return v == null ? null : v.value;
	}

	@Override
	public V remove(Object arg0) {
		CacheNode node = map.remove(arg0);
		timeMap.remove(node.time);
		return node.value;
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object val) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
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
	public void putAll(Map<? extends K, ? extends V> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}

	private class CacheNode{
		private K key;
		private V value;
		private Long time;
	}
}
