package com.morcinek.finance.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {

	private Connection conn;

	@Before
	public void setUp() throws ClassNotFoundException {
		String[] contextPath = new String[] { "context_test.xml" };
		Class.forName("org.sqlite.JDBC");
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@After
	public void tearDown() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void simpleTest() throws SQLException {
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists people;");
		stat.executeUpdate("create table people (name, occupation);");
		PreparedStatement prep = conn.prepareStatement("insert into people values (?, ?);");

		prep.setString(1, "Gandhi");
		prep.setString(2, "politics");
		prep.addBatch();
		prep.setString(1, "Turing");
		prep.setString(2, "computers");
		prep.addBatch();
		prep.setString(1, "Wittgenstein");
		prep.setString(2, "smartypants");
		prep.addBatch();

		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);

		ResultSet rs = stat.executeQuery("select * from people;");
		while (rs.next()) {
			System.out.print("name = " + rs.getString("name"));
			System.out.println(", job = " + rs.getString("occupation"));
		}
		rs.close();
	}

}
