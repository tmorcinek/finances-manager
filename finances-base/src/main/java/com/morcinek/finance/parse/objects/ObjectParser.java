package com.morcinek.finance.parse.objects;

import com.morcinek.finance.parse.exceptions.IncompatibleFormatException;
import com.morcinek.finance.util.Prioritised;

public abstract class ObjectParser implements Prioritised {

	public static final String DATE = "DATE";
	public static final String AMOUNT = "AMOUNT";
	public static final String CURRENCY = "CURRENCY";
	public static final String ACCOUNT = "ACCOUNT";
	public static final String TRANSACTION = "TRANSACTION";
	public static final String DEFAULT = "DEFAULT";

	/**
	 * @param string
	 *            (String) to be parsed by this parser.
	 * @return (Object) Specific object parsed by this parser.
	 * @throws IncompatibleFormatException
	 *             if <code>string</code> parameter cannot be parsed by this
	 *             parser.
	 */
	public abstract Object parseValue(String string) throws IncompatibleFormatException;

	/**
	 * @return (Class) class type of returned object.
	 */
	public abstract Class<?> getValueClass();

	/**
	 * @return (Object) parsed value type. For now the returned value will be
	 *         String with type name. E.g.:<blockquote> DATE, AMOUNT, CURRENCY,
	 *         ACCOUNT, TRANSACTION, DEFAULT </blockquote>
	 */
	public abstract Object getValueType();

}
