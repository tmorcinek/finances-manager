package com.morcinek.finance.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBReport extends ArrayList<DBAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4041627624606831303L;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (DBAction dbAction : this) {
			builder.append(dbAction);
			builder.append("\n");
		}
		return builder.toString();
	}

	public boolean hasError() {
		for (DBAction dbAction : this) {
			if (dbAction.isError()) {
				return true;
			}
		}
		return false;
	}

	// FIXME handle situation when more then one error is thrown
	public SQLException getError() {
		for (DBAction dbAction : this) {
			if (dbAction.isError()) {
				return dbAction.getError();
			}
		}
		return null;
	}
}

class DBAction {

	/**
	 * Name of action made on the database e.g.<blockquote> <b>insert</b> -
	 * entity added to database<br>
	 * <b>modify</b> - entity modified<br>
	 * <b>delete</b> - entity deleted from database<br>
	 * </blockquote>
	 */
	private final String action;

	/**
	 * Name of the table on which the modification was performed.
	 */
	private final String table;

	/**
	 * Value of entity, depends on the <b>action</b> type: <br>
	 * for <b>insert</b> this value is inserted entity. <br>
	 * for <b>modify</b> this value is the modified entity <br>
	 * for <b>delete</b> this value is deleted entity.
	 */
	private final Object value;

	/**
	 * Exception thrown during Database update.
	 */
	private final SQLException exception;

	public DBAction(String action, String table, Object value) {
		this(action, table, value, null);
	}

	public DBAction(String action, String table, Object value, SQLException exception) {
		this.action = action;
		this.table = table;
		this.value = value;
		this.exception = exception;
	}

	@Override
	public String toString() {
		return ((exception != null) ? exception.getMessage() : "") + "[action=" + action + ", column=" + table
				+ ", value=" + value + "]";
	}

	public List<?> getObjects() {
		ArrayList<Object> arrayList = new ArrayList<Object>();
		arrayList.add(action);
		arrayList.add(table);
		arrayList.add(value);
		return arrayList;
	}

	public String getAction() {
		return action;
	}

	public String getTable() {
		return table;
	}

	public Object getValue() {
		return value;
	}
	
	public SQLException getError(){
		return exception;
	}

	public boolean isError() {
		return exception != null;
	}

}
