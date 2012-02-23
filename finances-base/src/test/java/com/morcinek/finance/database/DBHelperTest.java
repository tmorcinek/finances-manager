package com.morcinek.finance.database;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.morcinek.finance.data.Payment;
import com.morcinek.finance.parse.HistoryParsing;

public class DBHelperTest {

	private ClassPathXmlApplicationContext classPathXmlApplicationContext;
	private DBHelper dbHelper;

	@Before
	public void setUp() {
		String[] contextPath = new String[] { "context_test.xml" };
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext(contextPath);

		dbHelper = (DBHelper) classPathXmlApplicationContext.getBean(DBHelper.class);
	}

	@After
	public void tearDown() {
		classPathXmlApplicationContext.close();
	}

	@Test
	public void test() {
		
	}
	
	@Test
	public void getPaymentsTest(){
		List<Payment> payments = new ArrayList<Payment>();
		try {
			payments = dbHelper.getPayments();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(2, payments.size());
	}

}
