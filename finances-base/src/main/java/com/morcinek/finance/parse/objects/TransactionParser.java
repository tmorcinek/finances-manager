package com.morcinek.finance.parse.objects;

import java.math.BigInteger;

import com.morcinek.finance.parse.exceptions.IncompatibleFormatException;

public class TransactionParser extends AccountParser {

	@Override
	public Object parseValue(String string) throws IncompatibleFormatException {
		try {
			String string2 = super.parseValue(string).toString();
			if(string2.length() != 11){
				throw new IncompatibleFormatException(string + " is not a BigInteger object");
			}
			return new BigInteger(string2);
		} catch (Exception e) {
			throw new IncompatibleFormatException(string + " is not a BigInteger object", e);
		}
	}

	@Override
	public Class<?> getValueClass() {
		return BigInteger.class;
	}

	@Override
	public Object getValueType() {
		return "TRANSACTION";
	}

	@Override
	public int getPriority() {
		return super.getPriority() + 1;
	}
}
