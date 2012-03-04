package com.morcinek.finance.database;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morcinek.finance.data.Category;
import com.morcinek.finance.data.Payment;
import com.morcinek.finance.util.SQLSentencesAdapter;
import com.morcinek.properties.Features;

@Component
public class DBHelper {

	@Autowired(required = true)
	private SQLSentencesAdapter sqlSentencesAdapter;

	private Connection connection;

	public DBHelper() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
	}

	@PostConstruct
	public void init() throws SQLException {
		try {
			connection = DriverManager.getConnection("jdbc:" + Features.database_subprotocol + ":" + getDatabaseName());
			createDatabase();
		} catch (SQLException e) {
			throw e;
		}
	}

	private String getDatabaseName() {
		return Features.database_subname + ".db";
	}

	public void cleanDatabase() throws SQLException {
		Statement stat = connection.createStatement();
		for (String sentence : sqlSentencesAdapter.getDropSentences()) {
			stat.executeUpdate(sentence + ";");
		}
		stat.close();
	}

	public void createDatabase() throws SQLException {
		Statement stat = connection.createStatement();
		for (String sentence : sqlSentencesAdapter.getCreateSentences()) {
			stat.executeUpdate(sentence + ";");
		}
		stat.close();
	}

	public List<Payment> getPayments() throws SQLException {
		List<Payment> payments = new ArrayList<Payment>();
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("select * from payments;");
		while (rs.next()) {
			payments.add(getPaymentFromResultSet(rs));
		}
		rs.close();
		stat.close();
		return payments;
	}

	private Payment getPaymentFromResultSet(ResultSet resultSet) throws SQLException {
		Date bookingDate = resultSet.getTimestamp("bookingDate");
		Date realizingDate = resultSet.getTimestamp("realizingDate");
		Double amount = resultSet.getDouble("amount");
		String currency = resultSet.getString("currency");
		String account = resultSet.getString("account");
		String bankName = resultSet.getString("bankName");
		String recipient = resultSet.getString("recipient");
		String title = resultSet.getString("title");
		BigInteger transactionNumber = new BigInteger(resultSet.getString("transactionNumber"));
		String additionalInformations = resultSet.getString("additionalInformations");
		Payment payment = new Payment(bookingDate, realizingDate, amount, currency, account, bankName, recipient, title,
				transactionNumber, additionalInformations);
		payment.setPaymentId(resultSet.getInt("paymentsId"));
		return payment;
	}

	public void addPayment(Payment payment) throws SQLException {
		PreparedStatement prep = connection.prepareStatement("insert into payments values (null,?,?,?,?,?,?,?,?,?,?);");
		addPayment(payment, prep);
		prep.executeBatch();
	}

	public void addPayments(List<Payment> pPayments) throws SQLException {
		connection.setAutoCommit(false);
		try {
			PreparedStatement prep = connection
					.prepareStatement("insert into payments values (null,?,?,?,?,?,?,?,?,?,?);");
			for (Payment payment : pPayments) {
				addPayment(payment, prep);
			}
			prep.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
		}
	}

	protected void addPayment(Payment payment, PreparedStatement prep) throws SQLException {
		prep.setTimestamp(1, new Timestamp(payment.getBookingDate().getTime()));
		prep.setTimestamp(2, new Timestamp(payment.getRealizingDate().getTime()));
		prep.setDouble(3, payment.getAmount());
		prep.setString(4, payment.getCurrency());
		prep.setString(5, payment.getAccount());
		prep.setString(6, payment.getBankName());
		prep.setString(7, payment.getRecipient());
		prep.setString(8, payment.getTitle());
		prep.setString(9, payment.getTransactionNumber().toString());
		prep.setString(10, payment.getAdditionalInformations());
		prep.addBatch();
	}

	public List<Category> getCategories() throws SQLException {
		List<Category> categories = new ArrayList<Category>();
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("select * from payments;");
		while (rs.next()) {
			categories.add(getCategoryFromResultSet(rs));
		}
		rs.close();
		stat.close();
		return categories;
	}

	public void addCategory(Category category) throws SQLException {
		PreparedStatement prep = connection.prepareStatement("insert into categories values (null,?);");
		prep.setString(1, category.getCategoryName());
		prep.executeBatch();
	}
	
	public List<Category> getPaymentCategory(Payment payment) throws SQLException{
		List<Category> categories = new ArrayList<Category>();
		PreparedStatement stat = connection.prepareStatement("select * from paymentsCategories where paymentsId = ?;");
		stat.setInt(1, payment.getPaymentId());
		ResultSet rs = stat.executeQuery();
		while (rs.next()) {
			categories.add(getCategoryFromResultSet(rs));
		}
		rs.close();
		stat.close();
		return categories;
	}

	public Category getCategoryFromResultSet(ResultSet rs) throws SQLException {
		return new Category(rs.getInt("categoryId"), rs.getString("categoryName"));
	}

	public void addPaymentCategory(Payment payment, Category category) throws SQLException {
		PreparedStatement prep = connection.prepareStatement("insert into paymentsCategories values (?,?);");
		prep.setString(1, category.getCategoryName());
		prep.setInt(2, category.getCategoryId());
		prep.executeBatch();
	}

	@PreDestroy
	public void cleanUp() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
