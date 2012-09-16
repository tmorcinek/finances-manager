package com.morcinek.finance.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PropertiesAdapterTest {

	private ClassPathXmlApplicationContext classPathXmlApplicationContext;

	public PropertiesAdapter propertiesAdapter;

	@Before
	public void setUp() {
		String[] contextPath = new String[] { "properties/context_properties.xml" };
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext(contextPath);

		propertiesAdapter = (PropertiesAdapter) classPathXmlApplicationContext.getBean(PropertiesAdapter.class);
	}

	@Test
	public void test() {
		assertEquals("Finance Manager", propertiesAdapter.getProperty("applicationName"));
		assertEquals("sqlite", propertiesAdapter.getProperty("database_subprotocol"));
		assertEquals(true, propertiesAdapter.getBoolean("hasTags"));
		assertEquals(true, propertiesAdapter.getBoolean("hasComments"));
		assertEquals("marek", propertiesAdapter.getProperty("marek"));
		assertEquals("cipa", propertiesAdapter.getProperty("cipa"));
		assertEquals("Finance Manager", propertiesAdapter.getProperty("applicationName"));
		assertEquals(false, propertiesAdapter.getBoolean("paymentDialog_resizable"));
		assertEquals(8, propertiesAdapter.getPropertiesList().size());
	}

}
