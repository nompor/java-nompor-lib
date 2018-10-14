package com.nompor.gtk.input;

import java.io.Serializable;
import java.util.Map;

public abstract class GTKInputManager<K> implements Serializable {
	protected final AbstractInputManager<?> mng;
	protected final Map<Object, ? extends InputAdapter<?>> map;
	protected GTKInputManager(AbstractInputManager<?> mng) {
		this.mng = mng;
		map = mng.getRegistMap();
	}
	public void regist(K key) {regist(key,key);}
	public abstract void regist(Object key, K code);
	@SuppressWarnings("unchecked")
	public abstract void orRegist(Object key,K... codes);
	@SuppressWarnings("unchecked")
	public abstract void andRegist(Object key,K... codes);
	public boolean isPress(Object key) {
		return map.get(key).isPress();
	}
	public boolean isDown(Object key) {
		return map.get(key).isDown();
	}
	public boolean isRelease(Object key) {
		return map.get(key).isRelease();
	}
	public boolean isUp(Object key) {
		return map.get(key).isUp();
	}
	public void remove(Object key) {
		mng.remove(key);
	}
	public void clear() {
		mng.clear();
	}
	public boolean isUpdateAdapter() {
		return mng.isUpdateAdapter();
	}
	public void setUpdateAdapter(boolean isUpdateAdapter) {
		mng.setUpdateAdapter(isUpdateAdapter);
	}
}
