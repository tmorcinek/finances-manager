package com.morcinek.finance.exceptions;

public class FinanceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8428704513937560332L;

	public FinanceException() {
	}

	public FinanceException(String message) {
		super(message);
	}

	public FinanceException(Throwable cause) {
		super(cause);
	}

}
