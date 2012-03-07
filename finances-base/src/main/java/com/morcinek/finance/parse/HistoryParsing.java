package com.morcinek.finance.parse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;

import com.morcinek.finance.database.Payment;

@Component
public class HistoryParsing {

	private FieldParser fieldParser;

	private List<Payment> payments = new ArrayList<Payment>();

	private List<List<?>> nonPayments = new ArrayList<List<?>>();

	@Autowired(required = true)
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
			if (data.length > 0) {
				Payment payment;
				try {
					payment = processPaymentData(data);
					payments.add(payment);
				} catch (Exception e) {
					nonPayments.add(Arrays.asList(data));
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

	public List<Payment> getPayments() {
		return payments;
	}

	public List<List<?>> getNonPayments() {
		return nonPayments;
	}

}
