package com.morcinek.finance.parse.objects;

import com.morcinek.finance.parse.exceptions.IncompatibleFormatException;

public class AccountParser extends ObjectParser {

	@Override
	public Object parseValue(String string) throws IncompatibleFormatException {
		char[] charString = string.toCharArray();
		if(charString.length > 0 && charString[0] == '\'' && charString[charString.length-1] == '\''){
			return string.substring(1, string.length() - 1);			
		}
		throw new IncompatibleFormatException(string + " is not Account Number Object");
	}

	@Override
	public Class<?> getValueClass() {
		return String.class;
	}

	@Override
	public Object getValueType() {
		return "ACCOUNT";
	}

	@Override
	public int getPriority() {
		return 10;
	}

}
