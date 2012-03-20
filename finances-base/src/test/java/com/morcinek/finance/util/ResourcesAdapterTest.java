package com.morcinek.finance.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.swing.Icon;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ResourcesAdapterTest {

	private ClassPathXmlApplicationContext classPathXmlApplicationContext;

	public ResourcesAdapter propertiesAdapter;

	@Before
	public void setUp() {
		String[] contextPath = new String[] { "properties/context_properties.xml" };
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext(contextPath);
		propertiesAdapter = (ResourcesAdapter) classPathXmlApplicationContext.getBean(ResourcesAdapter.class);

	}

	@Test
	public void test() {
		Icon icon = propertiesAdapter.getIcon("icon.png");
		assertNotNull(icon);
		icon = propertiesAdapter.getIcon("icon2.png");
		assertNull(icon);
	}

}
