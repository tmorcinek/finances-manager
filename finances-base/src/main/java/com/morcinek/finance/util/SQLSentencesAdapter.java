package com.morcinek.finance.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SQLSentencesAdapter {

	private List<String> createSentences = new ArrayList<String>();

	private List<String> dropSentences = new ArrayList<String>();

	/**
	 * Loads properties from given files to object.
	 * 
	 * @see PropertiesAdapter#setPropertiesFile(String)
	 * @param sqlFiles
	 *            (List) With names of files placed in main application
	 *            directory, or absolute path to file.
	 */
	public void setSqlFiles(List<String> sqlFiles) {
		for (String string : sqlFiles) {
			try {
				setSqlFile(string);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * While creating object from this class, there is an assumption that the
	 * file from which you want to load the properties is in the main
	 * application folder, or you are creating object with absolute path to the
	 * file.
	 * 
	 * @param sqlFile
	 *            (String) Name of the file placed in main application
	 *            directory, or absolute path to file.
	 * @throws IOException
	 *             When the file has not been found in main application
	 *             directory or path is not correct.
	 */
	public void setSqlFile(String sqlFile) throws IOException {
		String content = getFileContent(sqlFile).replaceAll("\t", " ");
		String[] sentences = content.split(";");
		for (String sentence : sentences) {
			if (sentence.toLowerCase().contains("create")) {
				createSentences.add(sentence);
			} else if (sentence.toLowerCase().contains("drop")) {
				dropSentences.add(sentence);
			}
		}
	}

	private String getFileContent(String sqlFile) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(sqlFile);
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return stringBuilder.toString();
	}

	public List<String> getCreateSentences() {
		return createSentences;
	}

	public List<String> getDropSentences() {
		return dropSentences;
	}

}
