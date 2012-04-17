package com.morcinek.finance.database;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("select * from payments;");
		List<Payment> payments = getPaymentsFromResultSet(rs);
		rs.close();
		stat.close();
		return payments;
	}

	public Set<Integer> getPaymentsIdsByCategory(Category category) throws SQLException {
		return getPaymentsIdsByCategory(category.getCategoryId());
	}

	public Set<Integer> getPaymentsIdsByCategory(int categoryId) throws SQLException {
		return getPaymentsIdsByCategory(new int[] { categoryId });
	}

	/**
	 * @see this method is just delegating role to
	 *      {@link #getPaymentsIdsByCategory(int[])}
	 * @param categories
	 * @return
	 * @throws SQLException
	 */
	public Set<Integer> getPaymentsIdsByCategory(List<Category> categories) throws SQLException {
		return getPaymentsIdsByCategory(getCategoryIdsArrayFromCategoriesList(categories));
	}

	private int[] getCategoryIdsArrayFromCategoriesList(List<Category> categories) {
		int[] categoryIds = new int[categories.size()];
		int i = 0;
		for (Category category : categories) {
			categoryIds[i++] = category.getCategoryId();
		}
		return categoryIds;
	}

	/**
	 * Base method which gets payments id's from database.
	 * 
	 * @param categoryId
	 * @return
	 * @throws SQLException
	 */
	public Set<Integer> getPaymentsIdsByCategory(int[] categoryId) throws SQLException {
		PreparedStatement stat = connection
				.prepareStatement("SELECT DISTINCT P.paymentsId FROM payments P INNER JOIN paymentsCategories PC on P.paymentsId = PC.paymentsId where PC.categoryId IN (?);");
		Set<Integer> paymentsIds = new TreeSet<Integer>();
		for (int i : categoryId) {
			stat.setInt(1, i);
			ResultSet rs = stat.executeQuery();
			paymentsIds.addAll(getIntegersFromResultSet(rs));
			rs.close();
		}
		stat.close();
		return paymentsIds;
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

	private Set<Integer> getIntegersFromResultSet(ResultSet rs) throws SQLException {
		Set<Integer> payments = new TreeSet<Integer>();
		while (rs.next()) {
			payments.add(rs.getInt(1));
		}
		return payments;
	}

	private List<Payment> getPaymentsFromResultSet(ResultSet rs) throws SQLException {
		List<Payment> payments = new ArrayList<Payment>();
		while (rs.next()) {
			payments.add(getPaymentFromResultSet(rs));
		}
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
		Payment payment = new Payment(bookingDate, realizingDate, amount, currency, account, bankName, recipient,
				title, transactionNumber, additionalInformations);
		payment.setPaymentId(resultSet.getInt("paymentsId"));
		return payment;
	}

	/**
	 * @return (List[Category]) of entities from categories table.
	 * @throws SQLException
	 */
	public Category getCategory(String categoryName) throws SQLException {
		Category category = null;
		PreparedStatement stat = connection.prepareStatement("select * from categories where categoryName = ?;");
		stat.setString(1, categoryName);
		ResultSet rs = stat.executeQuery();
		while (rs.next()) {
			category = getCategoryFromResultSet(rs);
		}
		rs.close();
		stat.close();
		return category;
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
				} catch (SQLException e) {
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
	 */
	public DBReport addCategory(Category category) {
		DBReport dbReport = new DBReport();
		try {
			PreparedStatement prep = connection.prepareStatement("insert into categories values (null,?,?);");
			prep.setString(1, category.getCategoryName());
			Integer parentId = category.getParentId();
			if (parentId != null) {
				prep.setInt(2, parentId);
			} else {
				prep.setNull(2, Types.INTEGER);
			}
			prep.execute();
			prep.close();
			category = getCategory(category.getCategoryName());
			dbReport.add(new DBAction("insert", "categories", category));
		} catch (SQLException e) {
			dbReport.add(new DBAction("insert", "categories", category, e));
		}
		return dbReport;
	}

	public DBReport addPaymentCategory(Payment payment, Category category) {
		DBReport dbReport = new DBReport();
		try {
			PreparedStatement prep = connection.prepareStatement("insert into paymentsCategories values (?,?);");
			prep.setInt(1, payment.getPaymentId());
			prep.setInt(2, category.getCategoryId());
			prep.execute();
			prep.close();
			dbReport.add(new DBAction("insert", "paymentsCategories", category));
		} catch (SQLException e) {
			dbReport.add(new DBAction("insert", "paymentsCategories", category, e));
		}
		return dbReport;

	}

	public DBReport deletePaymentCategory(Payment payment, Category category) {
		DBReport dbReport = new DBReport();
		try {
			PreparedStatement prep = connection
					.prepareStatement("delete from paymentsCategories where paymentsId = ? and  categoryId = ?;");
			prep.setInt(1, payment.getPaymentId());
			prep.setInt(2, category.getCategoryId());
			prep.execute();
			prep.close();
			dbReport.add(new DBAction("delete", "paymentsCategories", new Object[] { payment, category }));
		} catch (SQLException e) {
			dbReport.add(new DBAction("delete", "paymentsCategories", new Object[] { payment, category }, e));
		}
		return dbReport;

	}

	/**
	 * Delete single category from database.
	 * 
	 * @param category
	 *            (Category) to delete from database.
	 * @return (DBReport) of from transaction.
	 */
	public DBReport deleteCategory(Category category) {
		DBReport dbReport = new DBReport();
		try {
			PreparedStatement prep = connection.prepareStatement("delete from categories where categoryId = ?;");
			prep.setInt(1, category.getCategoryId());
			prep.execute();
			prep.close();
			dbReport.add(new DBAction("delete", "categories", category));
		} catch (SQLException e) {
			dbReport.add(new DBAction("delete", "categories", category, e));
		}
		return dbReport;
	}

	public Set<Integer> getPaymentsIdsOnRealizingDate(Date date) throws SQLException {
		return getPaymentsIdsOnDate("realizingDate", date);
	}

	public Set<Integer> getPaymentsIdsOnBookingDate(Date date) throws SQLException {
		return getPaymentsIdsOnDate("bookingDate", date);
	}

	public Set<Integer> getPaymentsIdsOnDate(String dateField, Date date) throws SQLException {
		return getPaymentsIdsOnDate(dateField, getTimestampFromDate(date));
	}

	private Timestamp getTimestampFromDate(Date date) {
		return new Timestamp(date.getTime());
	}

	public Set<Integer> getPaymentsIdsOnDate(String dateField, Timestamp date) throws SQLException {
		return getPaymentsIdsOnDate(dateField, date, '=');
	}

	public Set<Integer> getPaymentsIdsOnDate(String dateField, Timestamp date, char type) throws SQLException {
		PreparedStatement stat = connection.prepareStatement(getStatementWithCondition(type));
		Set<Integer> paymentsIds = new TreeSet<Integer>();
		stat.setString(1, dateField);
		stat.setTimestamp(2, date);
		ResultSet rs = stat.executeQuery();
		paymentsIds.addAll(getIntegersFromResultSet(rs));
		rs.close();
		stat.close();
		return paymentsIds;
	}

	public Set<Integer> getPaymentsIdsOnObject(String dateField, Object object, char type) throws SQLException {
		PreparedStatement stat = connection.prepareStatement(getStatementWithCondition(type));
		Set<Integer> paymentsIds = new TreeSet<Integer>();
		stat.setString(1, dateField);
		stat.setObject(2, object);
		ResultSet rs = stat.executeQuery();
		paymentsIds.addAll(getIntegersFromResultSet(rs));
		rs.close();
		stat.close();
		return paymentsIds;
	}

	public Set<Integer> getPaymentsIdsBetweenDates(String dateField, Date beforeDate, Date afterDate)
			throws SQLException {
		return getPaymentsIdsBetweenDates(dateField, getTimestampFromDate(beforeDate), getTimestampFromDate(afterDate));
	}

	// select * from game WHERE date BETWEEN '2012-1-15' AND '2012-2-15'
	public Set<Integer> getPaymentsIdsBetweenDates(String dateField, Timestamp beforeDate, Timestamp afterDate)
			throws SQLException {
		PreparedStatement stat = connection
				.prepareStatement("SELECT P.paymentsId FROM payments WHERE ? BETWEEN ? and ?;");
		Set<Integer> paymentsIds = new TreeSet<Integer>();
		stat.setString(1, dateField);
		stat.setTimestamp(2, beforeDate);
		stat.setTimestamp(2, afterDate);
		ResultSet rs = stat.executeQuery();
		paymentsIds.addAll(getIntegersFromResultSet(rs));
		rs.close();
		stat.close();
		return paymentsIds;
	}

	private String getStatementWithCondition(char type) {
		return "SELECT paymentsId FROM payments where ? " + type + " ?;";
	}


	public Set<Integer> getPaymentsIdsOnAmount(String dateField, Timestamp date, char type) throws SQLException {
		PreparedStatement stat = connection.prepareStatement(getStatementWithCondition(type));
		Set<Integer> paymentsIds = new TreeSet<Integer>();
		stat.setString(1, dateField);
		stat.setTimestamp(2, date);
		ResultSet rs = stat.executeQuery();
		paymentsIds.addAll(getIntegersFromResultSet(rs));
		rs.close();
		stat.close();
		return paymentsIds;
	}
	
//	public Set<Integer> getPaymentsIdsBetweenDates(String dateField, Timestamp beforeDate, Timestamp afterDate)
//			throws SQLException {
//		PreparedStatement stat = connection
//				.prepareStatement("SELECT P.paymentsId FROM payments WHERE ? BETWEEN ? and ?;");
//		Set<Integer> paymentsIds = new TreeSet<Integer>();
//		stat.setString(1, dateField);
//		stat.setTimestamp(2, beforeDate);
//		stat.setTimestamp(2, afterDate);
//		ResultSet rs = stat.executeQuery();
//		paymentsIds.addAll(getIntegersFromResultSet(rs));
//		rs.close();
//		stat.close();
//		return paymentsIds;
//	}
}
