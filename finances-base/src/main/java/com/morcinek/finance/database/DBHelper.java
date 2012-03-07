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

	@PreDestroy
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getDatabaseName() {
		return Features.database_subname + ".db";
	}

	/**
	 * Creates all tables by executing create statements.
	 * 
	 * @throws SQLException
	 */
	public void createDatabase() throws SQLException {
		Statement stat = connection.createStatement();
		for (String sentence : sqlSentencesAdapter.getCreateSentences()) {
			stat.executeUpdate(sentence + ";");
		}
		stat.close();
	}

	/**
	 * Drops all tables by executing drop statements.
	 * 
	 * @throws SQLException
	 */
	public void dropDatabase() throws SQLException {
		Statement stat = connection.createStatement();
		for (String sentence : sqlSentencesAdapter.getDropSentences()) {
			stat.executeUpdate(sentence + ";");
		}
		stat.close();
	}

	/**
	 * @return (List[Payments]) which contains all Payments from database;
	 * @throws SQLException
	 */
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

	/**
	 * @return (List[BigInteger]) of All TransactionNumbers from Payments table.
	 * @throws SQLException
	 */
	public List<BigInteger> getPaymentsTransactionNumbers() throws SQLException {
		List<BigInteger> transactionNumbers = new ArrayList<BigInteger>();
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("select transactionNumber from payments;");
		while (rs.next()) {
			BigInteger transactionNumber = new BigInteger(rs.getString(1));
			transactionNumbers.add(transactionNumber);
		}
		rs.close();
		stat.close();
		return transactionNumbers;
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
		Payment payment = new Payment(bookingDate, realizingDate, amount, currency, account, bankName, recipient,
				title, transactionNumber, additionalInformations);
		payment.setPaymentId(resultSet.getInt("paymentsId"));
		return payment;
	}

	/**
	 * @return (List[Category]) of entities from categories table.
	 * @throws SQLException
	 */
	public List<Category> getCategories() throws SQLException {
		List<Category> categories = new ArrayList<Category>();
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("select * from categories;");
		while (rs.next()) {
			categories.add(getCategoryFromResultSet(rs));
		}
		rs.close();
		stat.close();
		return categories;
	}

	/**
	 * @param payment
	 *            (Payment) object from which we retrieve paymentId.
	 * @return (List[Category]) of entities from Category table which have
	 *         <code>paymentId</code> equal to <code>payment.paymentId</code>.
	 * @throws SQLException
	 */
	public List<Category> getPaymentCategory(Payment payment) throws SQLException {
		List<Category> categories = new ArrayList<Category>();
		PreparedStatement stat = connection
				.prepareStatement("SELECT C.categoryId, C.categoryName, C.parentId FROM paymentsCategories PC INNER JOIN categories C on PC.categoryId = C.categoryId where PC.paymentsId = ?;");
		stat.setInt(1, payment.getPaymentId());
		ResultSet rs = stat.executeQuery();
		while (rs.next()) {
			categories.add(getCategoryFromResultSet(rs));
		}
		rs.close();
		stat.close();
		return categories;
	}

	private Category getCategoryFromResultSet(ResultSet rs) throws SQLException {
		return new Category(rs.getInt("categoryId"), rs.getString("categoryName"), rs.getInt("parentId"));
	}

	public DBReport addPayment(Payment payment) {
		DBReport dbReport = new DBReport();
		try {
			PreparedStatement prep = connection
					.prepareStatement("insert into payments values (null,?,?,?,?,?,?,?,?,?,?);");
			addPayment(payment, prep);
			prep.executeBatch();
			prep.close();
			dbReport.add(new DBAction("insert", "payments", payment));
		} catch (SQLException e) {
			dbReport.add(new DBAction("insert", "payments", payment, e));
		}
		return dbReport;
	}

	public DBReport addPayments(List<Payment> pPayments) throws SQLException {
		DBReport dbReport = new DBReport();
		connection.setAutoCommit(false);
		try {
			PreparedStatement prep = connection
					.prepareStatement("insert into payments values (null,?,?,?,?,?,?,?,?,?,?);");
			for (Payment payment : pPayments) {
				try {
					addPayment(payment, prep);
					dbReport.add(new DBAction("insert", "payments", payment));
				} catch (Exception e) {
					dbReport.add(new DBAction("insert", "payments", payment, e));
				}
			}
			prep.executeBatch();
			prep.close();
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			dbReport.add(new DBAction("insert", "payments", pPayments, e));
		} finally {
			connection.setAutoCommit(true);
		}
		return dbReport;
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

	/**
	 * Adding single category to database.
	 * 
	 * @param category
	 *            (Category) to add to database.
	 * @return (DBReport) of from transaction.
	 * @throws SQLException
	 */
	public DBReport addCategory(Category category) {
		DBReport dbReport = new DBReport();
		try {
			PreparedStatement prep = connection.prepareStatement("insert into categories values (null,?,?);");
			prep.setString(1, category.getCategoryName());
			prep.setInt(2, category.getParentId());
			prep.executeBatch();
			prep.close();
			dbReport.add(new DBAction("insert", "payments", category));
		} catch (SQLException e) {
			dbReport.add(new DBAction("insert", "payments", category, e));
		}
		return dbReport;
	}

	public DBReport addPaymentCategory(Payment payment, Category category) {
		DBReport dbReport = new DBReport();
		try {
			PreparedStatement prep = connection.prepareStatement("insert into paymentsCategories values (?,?);");
			prep.setString(1, category.getCategoryName());
			prep.setInt(2, category.getCategoryId());
			prep.executeBatch();
			prep.close();
			dbReport.add(new DBAction("insert", "payments", category));
		} catch (SQLException e) {
			dbReport.add(new DBAction("insert", "payments", category, e));
		}
		return dbReport;

	}

}
