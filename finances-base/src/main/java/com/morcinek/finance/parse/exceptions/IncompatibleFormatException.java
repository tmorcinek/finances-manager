package com.morcinek.finance.parse.exceptions;

public class IncompatibleFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncompatibleFormatException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IncompatibleFormatException(String arg0) {
		super(arg0);
	}

}
