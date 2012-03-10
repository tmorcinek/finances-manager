package com.morcinek.finance.util;

public interface Prioritised {

	/**
	 * @return (int) Priority of the parser in parsers queue. The highest
	 *         priority parser will be picked up at first. Parser with priority
	 *         0 (which is now <code>DefaultParser</code>) will be on the last
	 *         position to check.
	 */
	public int getPriority();
}
