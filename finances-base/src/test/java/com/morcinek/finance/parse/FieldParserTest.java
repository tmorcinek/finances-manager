package com.morcinek.finance.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.morcinek.finance.data.Payment;
import com.morcinek.finance.parse.objects.AccountParser;
import com.morcinek.finance.parse.objects.AmountParser;
import com.morcinek.finance.parse.objects.CurrencyParser;
import com.morcinek.finance.parse.objects.DateParser;
import com.morcinek.finance.parse.objects.DefaultParser;
import com.morcinek.finance.parse.objects.ObjectParser;
import com.morcinek.finance.parse.objects.TransactionParser;

public class FieldParserTest {

	FieldParser fieldParser;
	
	@Before
	public void setUp(){
		fieldParser = new FieldParser();
		fieldParser.registerParser(new DefaultParser());
		fieldParser.registerParser(new AccountParser());
		fieldParser.registerParser(new AmountParser());
		fieldParser.registerParser(new CurrencyParser());
		fieldParser.registerParser(new DateParser());
		fieldParser.registerParser(new TransactionParser());
		fieldParser.registerParser(new CurrencyParser());
	}
	
	@Test
	public void historyParsingTest() throws IOException{
		HistoryParsing historyParsing = new HistoryParsing(fieldParser);
		historyParsing.process("src/main/resources/history/historia.csv");
		List<String> headerList = historyParsing.getHeaderList();
		List<Payment> payments = historyParsing.getPayments();
		assertTrue(true);
	}
	
	@Test
	public void test1(){
		ObjectParser objectParser = fieldParser.getObjectParserByValue("2012-02-03");
		assertEquals(Date.class,objectParser.getValueClass());
		
		objectParser = fieldParser.getObjectParserByValue("-31,48");
		assertEquals(Double.class,objectParser.getValueClass());
		
		objectParser = fieldParser.getObjectParserByValue("PLN");
		assertTrue(objectParser.getClass().equals(CurrencyParser.class));
		assertEquals(String.class,objectParser.getValueClass());

		objectParser = fieldParser.getObjectParserByValue("12345678901");
		assertEquals(Double.class,objectParser.getValueClass());
		
		objectParser = fieldParser.getObjectParserByValue("'12345678901'");
		assertEquals(BigInteger.class,objectParser.getValueClass());
		assertTrue(objectParser.getClass().equals(TransactionParser.class));
		
		objectParser = fieldParser.getObjectParserByValue("'123456-327/8901'");
		assertEquals(String.class,objectParser.getValueClass());
		assertTrue(objectParser.getClass().equals(AccountParser.class));
	}
}
