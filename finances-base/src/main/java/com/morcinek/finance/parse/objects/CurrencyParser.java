package com.morcinek.finance.parse.objects;

import com.morcinek.finance.parse.exceptions.IncompatibleFormatException;

public class CurrencyParser extends ObjectParser {

	@Override
	public Object parseValue(String string) throws IncompatibleFormatException {
		if(string.length() == 3 && string.equals(string.toUpperCase())){
			return string;
		}
		throw new IncompatibleFormatException(string + " is not Currency object.");
	}

	@Override
	public Class<?> getValueClass() {
		return String.class;
	}

	@Override
	public Object getValueType() {
		return "CURRENCY";
	}

	@Override
	public int getPriority() {
		return 15;
	}

}
