package com.nompor.util;

import java.util.Iterator;

/**
 * 最低限の機能を有したLinkedListです
 * @author nompor
 *
 * @param <E>
 */
public class LinkNodeList<E> implements Iterable<E> {
	private LinkNode header = new LinkNode();

	public LinkNodeList(){
		header.prev = header.next = header;
	}
	/**
	 * リストの最初に要素を追加します
	 */
	public void addFirst(E element){
		final LinkNode h = header;
		LinkNode entry = new LinkNode(element,h.next,h);
		h.next.prev = entry;
		h.next = entry;
	}

	/**
	 * 指定ノードを高速に削除します
	 * @param node
	 */
	public void remove(final LinkNode node) {
		if ( node.parent() != this ) throw new IllegalArgumentException();
		node.prev.next = node.next;
		node.next.prev = node.prev;
	}

	/**
	 * リストの最後の要素を削除します
	 * @return 削除されたオブジェクト
	 */
	public void removeLast(){
		LinkNode obj = header.prev;
		header.prev = obj.prev;
		obj.prev.next = header;
	}

	/**
	 * リストの最初の要素を削除します
	 * @return 削除されたオブジェクト
	 */
	public void removeFirst(){
		LinkNode obj = header.next;
		header.next = obj.next;
		obj.next.prev = header;
	}

	/**
	 * リストの最初の要素を削除します
	 * @return 削除されたオブジェクト
	 */
	public E getFirst(){
		return header.next.element;
	}

	/**
	 * リストの最初の要素を削除します
	 * @return 削除されたオブジェクト
	 */
	public E getLast(){
		return header.prev.element;
	}

	/**
	 * 要素が空かどうか判定します
	 * @return
	 */
	public boolean isEmpty() {
		return header.next == header;
	}

	/**
	 * すべての要素を削除します
	 */
	public void clear() {
		LinkNode e = header.next;
		while (e != header) {
			LinkNode next = e.next;
			e.next = e.prev = null;
			e.element = null;
			e = next;
		}
		header.next = header.prev = header;
	}

	/**
	 * リストの最後に要素を追加します
	 */
	public void add(E element){
		addLast(element);
	}

	/**
	 * リストの最後に要素を追加します
	 */
	public void add(LinkNode node){
		addLast(node);
	}

	/**
	 * リストの最後に要素を追加します
	 */
	public void addLast(E element){
		addLast(new LinkNode(element,header,header.prev));
	}

	/**
	 * リストの最後に要素を追加します
	 */
	public void addLast(LinkNode node){
		lastLink(node);
	}

	private void lastLink(LinkNode entry) {
		header.prev.next = entry;
		header.prev = entry;
	}

	public class LinkNode{
		LinkNode next;
		LinkNode prev;
		E element;
		LinkNode(){}
		LinkNode(E element,LinkNode next,LinkNode prev){
			this.element = element;
			this.next = next;
			this.prev = prev;
		}
		public LinkNode next(){
			return next;
		}
		public LinkNode prev(){
			return prev;
		}
		public E element(){
			return element;
		}
		public LinkNodeList<E> parent(){
			return LinkNodeList.this;
		}
	}
	public Iterator<E> iterator(){
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
	public Iterator<LinkNode> nodeIterator(){
		return new NodeIterator();
	}
	private class NodeIterator implements Iterator<LinkNode>{
		LinkNode now = header;
		public LinkNode next(){
			now = now.next;
			return now;
		}
		public boolean hasNext(){
			return now.next != header;
		}
		public void remove(){
			now.prev.next = now.next;
			now.next.prev = now.prev;
		}
	}
	Object parent() {
		return null;
	}
	Object key() {
		return null;
	}
}
