package com.morcinek.finance.data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	private String recipient;
	private String title;
	private BigInteger transactionNumber;
	private String additionalInformations;

	private int paymentId = -1;
	
	private List<Category> categories;

	public Payment(Object... values) {
		super(Arrays.asList(values));
		bookingDate = (Date) values[0];
		realizingDate = (Date) values[1];
		amount = (Double) values[2];
		currency = (String) values[3];
		account = (String) values[4];
		bankName = (String) values[5];
		title = (String) values[6];
		recipient = (String) values[7];
		transactionNumber = (BigInteger) values[8];
		additionalInformations = (String) values[9];
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

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
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
	
	public void setPaymentId(int pPaymentId){
		paymentId = pPaymentId;
	}

	public int getPaymentId() {
		return paymentId;
	}

//	public List<Category> getCategories() {
//		if(categories == null && paymentId != -1){
//			
//		}
//		return categories;
//	}
}
