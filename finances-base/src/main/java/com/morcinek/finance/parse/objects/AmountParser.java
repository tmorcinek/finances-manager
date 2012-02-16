package com.morcinek.finance.parse.objects;

import com.morcinek.finance.parse.exceptions.IncompatibleFormatException;

public class AmountParser extends ObjectParser {

	@Override
	public Object parseValue(String string) throws IncompatibleFormatException{
		try {
			return Double.parseDouble(string.replace(",", "."));
		} catch (NumberFormatException e) {
			throw new IncompatibleFormatException(string + "is not Double Object", e);
		}
	}

	@Override
	public Class<?> getValueClass() {
		return Double.class;
	}

	@Override
	public Object getValueType() {
		return "AMOUNT";
	}

	@Override
	public int getPriority() {
		return 9;
	}

}
