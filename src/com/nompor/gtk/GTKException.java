package com.nompor.gtk;

public class GTKException extends RuntimeException {
	public GTKException() {

	}
	public GTKException(Throwable e) {
		initCause(e);
	}
	public GTKException(String msg) {
		super(msg);
	}
	public GTKException(String msg, Throwable e) {
		super(msg, e);
	}
}
