package com.morcinek.finance;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

public class CSVLibraryTest {

	private CSVReader csvReader;
	
	@Before
	public void setUp() throws FileNotFoundException{
		InputStream resourceAsStream = getClass().getResourceAsStream("src/main/resources/historia.csv");
		InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
		csvReader = new CSVReader(inputStreamReader);
	}
	
	@Test
	public void listTest(){
		try {
			List<String[]> entities = csvReader.readAll();
			for (String[] strings : entities) {
				System.out.println(strings);
			}
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
}
