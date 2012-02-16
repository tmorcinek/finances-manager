package com.morcinek.finance.parse.objects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.morcinek.finance.parse.exceptions.IncompatibleFormatException;

public class DateParser extends ObjectParser {

	@Override
	public Object parseValue(String string) throws IncompatibleFormatException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(string);
			return date;
		} catch (ParseException e) {
			throw new IncompatibleFormatException(string + " is not a date object", e);
		}
	}

	@Override
	public Class<?> getValueClass() {
		return Date.class;
	}

	@Override
	public Object getValueType() {
		return "DATE";
	}

	@Override
	public int getPriority() {
		return 14;
	}

}
