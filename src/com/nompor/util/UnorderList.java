package com.nompor.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 要素の順序を保持しないシンプルなリスト
 * @author nompor
 *
 * @param <E>
 */
public class UnorderList<E> implements Iterable<E>, Serializable{
	private Object[] element;
	private int size = 0;
	public UnorderList() {
		this(10);
	}
	public UnorderList(int capacity){
		element = new Object[capacity];
	}
	/**
	 * 要素を追加します
	 * @param e 要素
	 */
	public void add(E e){
		int oldCapacity = element.length;
		if(size >= oldCapacity){
			Object[] newElement = new Object[(oldCapacity * 3)/2 + 1];
			for(int i = 0;i < size;i++){
				newElement[i] = element[i];
			}
			element = newElement;
		}
		element[size++] = e;
	}

	/**
	 * 現在のサイズまで配列を縮小させます
	 */
	public void trimToSize(){
		int oldCapacity = element.length;
		if (size < oldCapacity) {
			element = Arrays.copyOf(element, size);
		}
	}

	/**
	 * 現在のサイズを返します
	 */
	public int size(){
		return size;
	}

	/**
	 * 指定した要素を返します
	 */
	@SuppressWarnings("unchecked")
	public E get(int index){
		return (E)element[index];
	}

	/**
	 * 指定した要素を削除します
	 */
	public void remove(int index){
		element[index] = element[--size];
		element[size] = null;
	}

	/**
	 * 指定した要素をすべて削除します
	 */
	public void remove(Object obj){
		for(int i = 0;i < size;i++){
			if(element[i].equals(obj)){
				remove(i);
			}
		}
	}

	/**
	 * 指定された要素がリスト内にあるかどうかを返します
	 */
	public boolean contains(Object obj){
		for(int i = 0;i < size;i++){
			if(element[i].equals(obj)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 要素をすべて削除します。
	 */
	public void clear() {
		for(int i = 0;i < size;i++){
			if(element[i]==null) {
				break;
			}
			element[i] = null;
		}
		size = 0;
	}

	/**
	 * このクラスのIteratorを作成します。
	 * 本来のiteratorはループ中に要素の挿入を許可されませんが、
	 * このクラスのIteratorはループ中に要素の追加を許可します。
	 * データの整合性は保たれないのでマルチスレッドで使用することは推奨されません。
	 */
	public Iterator<E> iterator(){
		return new Iterator<E>(){
			int cursor = 0;
			public E next(){
				return get(cursor++);
			}
			public boolean hasNext(){
				return cursor < size;
			}
			public void remove(){
				UnorderList.this.remove(--cursor);
			}
		};
	}
}
