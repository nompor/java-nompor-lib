package com.nompor.util;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class LinkOrderList<E extends Orderable<? extends Comparable<?>>> implements Iterable<E> {
	private TreeMap<Comparable<?>, OrderList> map;

	public LinkOrderList() {
		map = new TreeMap<>();
	}

	public void add(E element) {
		OrderList list = getList(element);
		list.add(element);
	}

	private OrderList getList(E element) {
		Comparable<?> key = element.getOrder();
		OrderList list = map.get(key);
		if ( list == null ) {
			list = new OrderList(key);
			map.put(key, list);
		}
		return list;
	}

	public void change(LinkNodeList<E>.LinkNode node) {
		remove(node);
		OrderList list = getList(node.element);
		list.add(node);
	}

	public void remove(LinkNodeList<E>.LinkNode node) {
		LinkNodeList<E> parent = node.parent();
		if ( parent.parent() != this ) throw new IllegalArgumentException();
		parent.remove(node);
		if ( parent.isEmpty() ) {
			map.remove(parent.key());
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>(){
			NodeIterator it = new NodeIterator();
			public E next(){
				return it.next().element;
			}
			public boolean hasNext(){
				return it.hasNext();
			}
			public void remove(){
				it.remove();
			}
		};
	}

	public Iterator<LinkNodeList<E>.LinkNode> nodeIterator() {
		return new NodeIterator();
	}

	private class NodeIterator implements Iterator<LinkNodeList<E>.LinkNode>{

		private Iterator<Entry<Comparable<?>, OrderList>> it = map.entrySet().iterator();
		private Iterator<LinkNodeList<E>.LinkNode> linkIt;

		{
			if ( it.hasNext() ) {
				linkIt = it.next().getValue().nodeIterator();
			}
		}

		@Override
		public boolean hasNext() {
			return linkIt != null;
		}

		@Override
		public LinkNodeList<E>.LinkNode next() {
			LinkNodeList<E>.LinkNode e = linkIt.next();
			if ( !linkIt.hasNext() ) {
				if ( it.hasNext() ) {
					linkIt = it.next().getValue().nodeIterator();
				} else {
					linkIt = null;
				}
			}
			return e;
		}
		public void remove(){
			linkIt.remove();
		}
	}

	private class OrderList extends LinkNodeList<E>{
		private Comparable<?> key;
		private OrderList(Comparable<?> key) {this.key = key;}
		@Override
		public LinkOrderList<E> parent() {
			return LinkOrderList.this;
		}
		@Override
		public Comparable<?> key() {
			return key;
		}
	}
}
