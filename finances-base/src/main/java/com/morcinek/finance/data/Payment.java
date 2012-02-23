package com.morcinek.finance.data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Payment extends ArrayList<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1917069929244969776L;

	private Date bookingDate;
	private Date realizingDate;
	private Double amount;
	private String currency;
	private String account;
	private String bankName;
	private String recepient;
	private String title;
	private BigInteger transactionNumber;
	private String additionalInformations;

	public Payment(Object... values) {
		super(Arrays.asList(values));
		bookingDate = extractDate(values[0]);
		realizingDate = extractDate(values[1]);
		amount = Double.valueOf(values[2].toString());
		currency = (String) values[3];
		account = (String) values[4];
		bankName = (String) values[5];
		title = (String) values[6];
		recepient = (String) values[7];
		transactionNumber = extractBigInteger(values[8]);
		additionalInformations = (String) values[9];
	}

	private Date extractDate(Object value) {
		Date date;
		try {
			date = (Date) value;
		} catch (ClassCastException e) {
			date = new Date(Long.parseLong(value.toString()));
		}
		return date;
	}

	private BigInteger extractBigInteger(Object value) {
		BigInteger bigInteger;
		try {
			bigInteger = (BigInteger) value;
		} catch (Exception e) {
			bigInteger = new BigInteger(value.toString());
		}
		return bigInteger;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getRealizingDate() {
		return realizingDate;
	}

	public void setRealizingDate(Date realizingDate) {
		this.realizingDate = realizingDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRecepient() {
		return recepient;
	}

	public void setRecepient(String recepient) {
		this.recepient = recepient;
	}

	public BigInteger getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(BigInteger transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getAdditionalInformations() {
		return additionalInformations;
	}

	public void setAdditionalInformations(String additionalInformations) {
		this.additionalInformations = additionalInformations;
	}
}
