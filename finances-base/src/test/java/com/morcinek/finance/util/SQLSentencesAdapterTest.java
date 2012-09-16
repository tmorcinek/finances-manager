package com.morcinek.finance.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SQLSentencesAdapterTest {

	private ClassPathXmlApplicationContext classPathXmlApplicationContext;

	private SQLSentencesAdapter sqlSentencesAdapter;

	@Before
	public void setUp() {
		String[] contextPath = new String[] { "properties/context_sql.xml" };
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext(contextPath);
		sqlSentencesAdapter = classPathXmlApplicationContext.getBean(SQLSentencesAdapter.class);
	}

	@Test
	public void test() {
		assertEquals(3, sqlSentencesAdapter.getCreateSentences().size());
		assertEquals(3, sqlSentencesAdapter.getDropSentences().size());
		assertEquals("drop table if exists payments", sqlSentencesAdapter.getDropSentences().get(0));		
		assertEquals("drop table if exists categories", sqlSentencesAdapter.getDropSentences().get(1));		
		assertEquals("drop table if exists paymentsCategories", sqlSentencesAdapter.getDropSentences().get(2));		
	}

}
