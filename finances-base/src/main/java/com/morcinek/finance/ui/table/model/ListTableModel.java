package com.morcinek.finance.ui.table.model;

import java.util.ArrayList;
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

	private List<List<?>> data = new LinkedList<List<?>>();

	private List<String> header;

	public void setData(List<List<?>> pData) {
		data.clear();
		data.addAll(pData);
	}

	public void setHeader(List<String> pHeader) {
		header = pHeader;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		if (data.size() > 0) {
			return data.get(0).size();
		}
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public String getColumnName(int column) {
		if (header != null) {
			return header.get(column);
		}
		return super.getColumnName(column);
	}

	@Override
	public Class<?> getColumnClass(int column) {
		if (data.size() > 0) {
			return data.get(0).get(column).getClass();
		}
		return super.getColumnClass(column);
	}

	public List<List<?>> getData() {
		return data;
	}

	public List<?> getRowAt(int rowIndex) {
		return data.get(rowIndex);
	}

}
