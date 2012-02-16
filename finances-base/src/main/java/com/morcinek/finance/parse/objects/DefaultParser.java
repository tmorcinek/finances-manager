package com.morcinek.finance.parse.objects;

import com.morcinek.finance.parse.exceptions.IncompatibleFormatException;

public class DefaultParser extends ObjectParser {

	@Override
	public Object parseValue(String string) throws IncompatibleFormatException {
		return string;
	}

	@Override
	public Class<?> getValueClass() {
		return String.class;
	}

	@Override
	public Object getValueType() {
		return "DEFAULT";
	}

	@Override
	public int getPriority() {
		return 0;
	}

}
