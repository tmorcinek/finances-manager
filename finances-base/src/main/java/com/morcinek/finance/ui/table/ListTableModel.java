package com.morcinek.finance.ui.table;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.stereotype.Component;

/**
 * ListTableModel is a class which provides Table Model for List of Lists.
 * 
 * @author Tomasz Morcinek
 * @date 16-02-2012
 * @time 01:44:42
 * 
 */
@Component
public class ListTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7371969815889555383L;

	private List<List<Object>> collections = new LinkedList<List<Object>>();

	public void setData(List<List<Object>> payments) {
		collections = payments;
	}

	@Override
	public int getRowCount() {
		return collections.size();
	}

	@Override
	public int getColumnCount() {
		if (collections.size() > 0) {
			return collections.get(0).size();
		}
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return collections.get(rowIndex).get(columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public String getColumnName(int column) {
		return super.getColumnName(column);
	}

	@Override
	public Class<?> getColumnClass(int column) {
		if (collections.size() > 0) {
			return collections.get(0).get(column).getClass();
		}
		return super.getColumnClass(column);
	}

}
