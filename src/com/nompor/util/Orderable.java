package com.nompor.util;

public interface Orderable<T extends Comparable<T>> {
	T getOrder();
}
