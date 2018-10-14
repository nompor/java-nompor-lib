package com.nompor.util;

public class ThreadUtil {

	public static String getStackClassAndMethodName(int stackNum) {
		StackTraceElement elem = Thread.currentThread().getStackTrace()[stackNum];
		return elem.getClassName() +"."+ elem.getMethodName();
	}

	public static String getStackClassAndMethodName() {
		return getStackClassAndMethodName(2);
	}

	public static String getStackCClassName(int stackNum) {
		StackTraceElement elem = Thread.currentThread().getStackTrace()[stackNum];
		return elem.getClassName();
	}

	public static String getStackCClassName() {
		return getStackCClassName(2);
	}

	public static String getStackCMethodName(int stackNum) {
		StackTraceElement elem = Thread.currentThread().getStackTrace()[stackNum];
		return elem.getMethodName();
	}

	public static String getStackCMethodName() {
		return getStackCMethodName(2);
	}
}
