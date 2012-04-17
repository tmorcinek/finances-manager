package com.morcinek.finance.database;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class DBHelperTest {

	private ClassPathXmlApplicationContext classPathXmlApplicationContext;
	private DBHelper dbHelper;

	@Before
	public void setUp() {
		String[] contextPath = new String[] { "database/context_test.xml" };
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext(contextPath);
		dbHelper = (DBHelper) classPathXmlApplicationContext.getBean(DBHelper.class);
	}

	@After
	public void tearDown() {
		classPathXmlApplicationContext.close();
	}

	@Test
	@Ignore
	public void test() {
		
	}
	
	@Test
	@Ignore
	public void getPaymentsTest() throws SQLException{
		List<Payment> payments = new ArrayList<Payment>();
		payments = dbHelper.getPayments();
//		assertEquals(2, payments.size());
	}
	
	@Test
	@Ignore
	public void getPayments2Test() throws SQLException{
		Set<Integer> payments = dbHelper.getPaymentsIdsByCategory(1);
		payments.addAll(dbHelper.getPaymentsIdsByCategory(2));
		payments.addAll(dbHelper.getPaymentsIdsByCategory(0));
		payments.addAll(dbHelper.getPaymentsIdsByCategory(3));
		Set<Integer> payments2 = dbHelper.getPaymentsIdsByCategory(new int[]{0,1,2,3});
		System.out.println(payments2);
		assertEquals(payments, payments2);
	}
	
	@Test
	public void getPaymentsDatesTest() throws SQLException{
		Set<Integer> payments = dbHelper.getPaymentsIdsOnDate("bookingDate", new Timestamp(new Date().getTime()), '>');
		System.out.println(payments);
		payments = dbHelper.getPaymentsIdsOnObject("bookingDate", new Timestamp(new Date().getTime()), '>');
		System.out.println(payments);
	}
	
	@Test
	@Ignore
	public void getPaymentsDates2Test() throws SQLException{
		Set<Integer> payments = dbHelper.getPaymentsIdsByCategory(1);
		payments.addAll(dbHelper.getPaymentsIdsByCategory(2));
		payments.addAll(dbHelper.getPaymentsIdsByCategory(0));
		payments.addAll(dbHelper.getPaymentsIdsByCategory(3));
		Set<Integer> payments2 = dbHelper.getPaymentsIdsByCategory(new int[]{0,1,2,3});
		System.out.println(payments2);
		assertEquals(payments, payments2);
	}

}
