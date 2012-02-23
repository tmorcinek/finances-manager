package com.morcinek.finance.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.morcinek.finance.data.Payment;

@Component
public class DBHelper {

	private static final int PAYMENTS_COLUMNS = 10;

	private static final String DB_PAYMENTS_DROP = "drop table if exists payments;";
	// private static final String DB_VERSION_DROP =
	// "drop table if exists versions;";

	// private Date bookingDate;
	// private Date realizingDate;
	// private Double amount;
	// private String currency;
	// private String account;
	// private String bankName;
	// private String recepient;
	// private String title;
	// private BigInteger transactionNumber;
	// private String additionalInformations;
	private static final String DB_PAYMENTS_CREATE = "create table payments (bookingDate TIMESTAMP not null, realizingDate TIMESTAMP not null, amount NUMERIC(10,2) not null, currency VARCHAR(5), account TEXT, bankName TEXT, recepient TEXT, title TEXT, transactionNumber VARCHAR(11) primary key, additionalInformations TEXT);";
	// private static final String DB_VERSION_CREATE =
	// "create table versions (version INTEGER primary key, date TIMESTAMP)";

	private Connection connection;

	public DBHelper() throws ClassNotFoundException, SQLException {
		this(0);
	}

	public DBHelper(int version) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:test.db");
			if (version == 0) {
				Statement stat = connection.createStatement();
				stat.executeUpdate(DB_PAYMENTS_DROP);
				stat.executeUpdate(DB_PAYMENTS_CREATE);
				// stat.executeUpdate(DB_VERSION_DROP);
				// stat.executeUpdate(DB_VERSION_CREATE);
				stat.close();
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	public List<Payment> getPayments() throws SQLException {
		List<Payment> payments = new ArrayList<Payment>();
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("select * from payments;");
		while (rs.next()) {
			List<Object> objects = new ArrayList<Object>();
			for (int i = 0; i < PAYMENTS_COLUMNS; i++) {
				Object object = rs.getObject(i + 1);
				objects.add(object);
			}
			payments.add(new Payment(objects.toArray()));
		}
		rs.close();
		stat.close();
		return payments;
	}

	public void addPayment(Payment payment) throws SQLException {
		PreparedStatement prep = connection.prepareStatement("insert into payments values (?,?,?,?,?,?,?,?,?,?);");
		addPayment(payment, prep);
		prep.executeBatch();
	}

	public void addPayments(List<Payment> pPayments) throws SQLException {
		connection.setAutoCommit(false);
		try {
			PreparedStatement prep = connection.prepareStatement("insert into payments values (?,?,?,?,?,?,?,?,?,?);");
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
		prep.setString(7, payment.getRecepient());
		prep.setString(8, payment.getTitle());
		prep.setString(9, payment.getTransactionNumber().toString());
		prep.setString(10, payment.getAdditionalInformations());
		prep.addBatch();
	}

	@PreDestroy
	public void cleanUp() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// @PostConstruct
	// private void initialize() {
	// try {
	// PreparedStatement prep =
	// connection.prepareStatement("insert into payments values (?,?,?,?,?,?,?,?,?,?);");
	// Date date = new Date(1988 - 1900, 02, 21);
	// prep.setTimestamp(1, new Timestamp(date.getTime()));
	// prep.setTimestamp(2, new Timestamp(5));
	// prep.setDouble(3, new Double(2123.09));
	// prep.setString(4, "politics");
	// prep.setString(5, "PLN");
	// prep.setString(6, "account");
	// prep.setString(7, "bank");
	// prep.setString(8, "recipient");
	// prep.setString(9, "12345678901");
	// prep.setString(10, "dafdsafas");
	// prep.addBatch();
	// prep.setTimestamp(1, new Timestamp(date.getTime()));
	// prep.setTimestamp(2, new Timestamp(5));
	// prep.setDouble(3, new Double(2123.03));
	// prep.setString(4, "politics");
	// prep.setString(5, "PLN");
	// prep.setString(6, "account");
	// prep.setString(7, "bank");
	// prep.setString(8, "recipient");
	// prep.setString(9, "12345678902");
	// prep.setString(10, "dafdsafas");
	// prep.addBatch();
	//
	// connection.setAutoCommit(false);
	// prep.executeBatch();
	// connection.setAutoCommit(true);
	// // prep.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
}
