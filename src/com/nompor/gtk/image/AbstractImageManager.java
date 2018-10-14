package com.nompor.gtk.image;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class AbstractImageManager<I> {

	private Map<Object, I> map;
	private ImageFacotry<I> factory;
	public AbstractImageManager(Map<Object, I> map, ImageFacotry<I> factory) {
		this.map = map;
		this.factory = factory;
	}

	public I getImage(String filePath) {
		return getImage(filePath, filePath);
	}
	public I getImage(String filePath, Object key) {
		I img = map.get(key);
		if ( img == null ) {
			load(filePath, key);
			img = map.get(key);
		}
		return img;
	}
	public void load(String filePath) {
		load(filePath, filePath);
	}
	public void load(String filePath, Object key) {
		I img = map.put(key, factory.newImage(filePath));
		if ( img != null ) release(img);
	}
	public void clear() {
		map.forEach((k,v)->release(v));
		map.clear();
	}
	public void unload(Object key) {
		I img = map.remove(key);
		if ( img != null ) release(img);
	}
	public I getCache(Object key) {
		return map.get(key);
	}
	public void setCache(Object key, I img) {
		map.put(key, img);
	}
	public void forEach(BiConsumer<? super Object, ? super I> action) {
		map.forEach(action);
	}
	public Collection<I> getCacheList() {
		return map.values();
	}
	protected void release(I img) {}
}
