package com.morcinek.finance.parse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;

import com.morcinek.finance.data.Payment;

@Component
public class HistoryParsing {

	private FieldParser fieldParser;

	private List<String> headerList = new ArrayList<String>();

	private List<Payment> payments = new ArrayList<Payment>();

	@Autowired(required=true)
	public HistoryParsing(FieldParser pFieldParser) {
		fieldParser = pFieldParser;
	}

	public void process(String pFilePath) throws IOException {
		CSVReader csvReader = null;
		try {
			csvReader = new CSVReader(new InputStreamReader(new FileInputStream(pFilePath)), ';');
			processData(csvReader.readAll());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (csvReader != null) {
				csvReader.close();
			}
		}
	}

	private void processData(List<String[]> parsedData) {
		payments.clear();
		for (String[] data : parsedData) {
			if (data.length == 1) {
				headerList.add(data[0]);
			} else if (data.length > 1) {
				Payment payment;
				try {
					payment = processPaymentData(data);
					payments.add(payment);
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Payment processPaymentData(String[] row) {
		Object[] objects = new Object[row.length];
		for (int i = 0; i < row.length; i++) {
			objects[i] = fieldParser.getParsedValue(row[i]);
		}
		return new Payment(objects);
	}

	public List<String> getHeaderList() {
		return headerList;
	}

	public List<Payment> getPayments() {
		return payments;
	}
	
	

}
