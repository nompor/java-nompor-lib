package com.nompor.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * キャッシュ機能を実装したMapクラスです。
 * データはコンストラクタに与えられる、cacheNumの分だけ保持され、容量制限を超えると、
 * 古いデータから削除します。
 */
public class QueueCacheMap<K, V> implements Map<K, V>{

	private LinkedList<K> queue = new LinkedList<>();
	private Map<K, V> map;
	private final int cacheNum;

	/**
	 * 指定されたキャッシュ数とマップを使用したキャッシュマップを構築します。
	 * @param cacheNum
	 * @param map
	 */
	public QueueCacheMap(int cacheNum, Map<K, V> map) {
		this.cacheNum = cacheNum;
		this.map = map;
	}

	/**
	 * キャッシュ機能を実装するMapオブジェクトを構築します。
	 * @param cacheNum 強参照状態で保持するキャッシュオブジェクト数
	 */
	public QueueCacheMap(int cacheNum) {
		this(cacheNum, new HashMap<>());
	}

	/**
	 * 弱参照キャッシュマップオブジェクトを構築します。
	 * 削除されたデータは弱参照状態で保持し、そのあとも削除データを取得できる可能性があります。
	 * @param cacheNum 強参照を保持する数
	 * @return
	 */
	public static <K, V> QueueCacheMap<K, V> createWeakCacheMap(int cacheNum) {
		return new QueueCacheMap<K, V>(cacheNum, new WeakHashMap<>());
	}

	/**
	 * 全ての要素を取得します。
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean containsKey(Object arg0) {
		return map.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) {
		return map.containsValue(arg0);
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
		throw new UnsupportedOperationException("Unsupported method.");
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}


	/**
	 * 指定されたキーで値をキャッシュします。
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public V put(K key, V value) {
		queue.offer(key);
		V v = map.put(key, value);
		if ( queue.size() > cacheNum ) {
			 Object rk = queue.poll();
			 v = map.remove(rk);
		}
		return v;
	}

	/**
	 * キーに関連付く要素を取得します。
	 * @param key
	 * @return
	 */
	@Override
	public V get(Object key) {
		return map.get(key);
	}

	/**
	 * @deprecated このメソッドは処理速度の問題があり、推奨されません。
	 * @param keys
	 */
	@Override
	public V remove(Object key) {
		Object target = null;
		for ( Iterator<K> it = queue.iterator();it.hasNext();target = it.next() ) {
			if ( target.equals(key) ) {
				it.remove();
				V v = map.remove(key);
				return v;
			}
		}
		return null;
	}

	/**
	 * 全ての要素を削除します。
	 */
	@Override
	public void clear() {
		queue.clear();
		map.clear();
	}
}
