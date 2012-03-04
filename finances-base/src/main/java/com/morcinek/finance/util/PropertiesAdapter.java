package com.morcinek.finance.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * This class is a spring singleton used where properties are needed. Should be
 * initialized in lazy way. Properties can not be changed during runtime, that
 * is why in <code>getProperties()</code> method clone is returned.
 * 
 * @author Tomasz Morcinek
 * @projectName football-manager
 * @date 15-09-2011
 * @time 13:52:43
 */
public class PropertiesAdapter {

	private Properties properties = new Properties();

	/**
	 * Loads properties from given files to object.
	 * 
	 * @see PropertiesAdapter#setPropertiesFile(String)
	 * @param propertiesName
	 *            (List) With names of files placed in main application
	 *            directory, or absolute path to file.
	 */
	public void setPropertiesFiles(List<String> propertiesName) {
		for (String string : propertiesName) {
			try {
				setPropertiesFile(string);
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
	 * @param propertiesName
	 *            (String) Name of the file placed in main application
	 *            directory, or absolute path to file.
	 * @throws IOException
	 *             When the file has not been found in main application
	 *             directory or path is not correct.
	 */
	public void setPropertiesFile(String propertiesName) throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(propertiesName);
			properties.load(inputStream);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}

	}

	/**
	 * Method returns clone object to disable properties modifications. This
	 * method is protected because user outside of this package should not be
	 * able to get cloned properties all at once (he could be tempted to write
	 * redundant functions to get data from properties).
	 * 
	 * @return clone from <code>properties</code>.
	 */
	protected Properties getProperties() {
		return (Properties) properties.clone();
	}

	/**
	 * Delegate method for retrieving property.
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Method retrieving boolean types from properties.
	 * 
	 * @param key
	 *            (String) Name of property we want to retrieve.
	 * @return Boolean value of property.
	 * @throws IllegalArgumentException
	 *             When corresponding property is not a boolean type.
	 */
	public Boolean getBoolean(String key) throws IllegalArgumentException {
		String property = properties.getProperty(key);
		if (property == null) {
			return null;
		}
		if ("true".equalsIgnoreCase(property) || "false".equalsIgnoreCase(property)) {
			return Boolean.parseBoolean(property);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Method retrieving integer types from properties.
	 * 
	 * @param key
	 *            (String) Name of property we want to retrieve.
	 * @return Integer value of property.
	 * @throws NumberFormatException
	 * @see {@link java.lang.Integer#parseInt(String)}
	 */
	public Integer getInteger(String key) throws NumberFormatException {
		return Integer.parseInt(properties.getProperty(key));
	}

	public Set<Entry<Object, Object>> getPropertiesList() {
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		return entrySet;
	}
}
