package com.morcinek.finance.ui.table.model;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
public class ListTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7371969815889555383L;

	private List<List<?>> data = new LinkedList<List<?>>();

	private List<String> header;

	private int count;

	public void clear() {
		data.clear();
	}

	public void setData(List<List<?>> pData) {
		data.addAll(pData);
		for (List<?> list : pData) {
			count = Math.max(count, list.size());
		}
		fireTableStructureChanged();
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
		return count;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		List<?> rowData = data.get(rowIndex);
		if (rowData.size() > columnIndex) {
			return rowData.get(columnIndex);
		}
		return null;
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
	public Class<?> getColumnClass(int columnIndex) {
		List<?> rowData = data.get(0);
		if (rowData.size() > columnIndex) {
			return rowData.get(columnIndex).getClass();
		}
		return super.getColumnClass(columnIndex);
	}

	public List<List<?>> getData() {
		return data;
	}

	public List<?> getRowAt(int rowIndex) {
		return data.get(rowIndex);
	}
}
