package com.morcinek.finance.parse;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.morcinek.finance.PrioritisedSet;
import com.morcinek.finance.parse.exceptions.IncompatibleFormatException;
import com.morcinek.finance.parse.objects.ObjectParser;

@Component
public class FieldParser {

	@Autowired(required = true)
	@Qualifier("packageName")
	private String packageName;

	@Autowired(required = true)
	@Qualifier("parsers")
	public void setParsers(List<String> parsersNames) {
		for (String parserName : parsersNames) {
			registerParser(packageName.concat(".").concat(parserName));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private SortedSet<ObjectParser> priorityQueue = new PrioritisedSet();

	public void registerParser(String objectParserName) {
		try {
			@SuppressWarnings("unchecked")
			Class<ObjectParser> objectParserClass = (Class<ObjectParser>) Class.forName(objectParserName + "Parser");
			ObjectParser objectParser = objectParserClass.newInstance();
			registerParser(objectParser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerParser(ObjectParser objectParser) {
		priorityQueue.add(objectParser);
	}

	public ObjectParser getObjectParserByValue(String value) {
		for (ObjectParser objectParser : priorityQueue) {
			try {
				objectParser.parseValue(value);
				return objectParser;
			} catch (IncompatibleFormatException e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}

	public Object getParsedValue(String value) {
		try {
			return getObjectParserByValue(value).parseValue(value);
		} catch (IncompatibleFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ObjectParser getObjectParserByName(String idName) {
		for (ObjectParser objectParser : priorityQueue) {
			if (objectParser.getValueType().equals(idName)) {
				return objectParser;
			}
		}
		return null;
	}

	public Set<ObjectParser> getObjectParsers() {
		return priorityQueue;
	}
}
