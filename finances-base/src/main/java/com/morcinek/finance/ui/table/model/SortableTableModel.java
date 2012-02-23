package com.morcinek.finance.ui.table.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morcinek.finance.ui.table.sorting.SimpleTableSorter;

/**
 * SortableTableModel is a class which provides Table Model for List of Lists,
 * extended of sorting the table.
 * 
 * @author Tomasz Morcinek
 * @date 17-02-2012
 * @time 02:34:22
 * 
 */
@Component
public class SortableTableModel extends ListTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5220844108100221059L;

	private int indexes[];

	@Autowired
	private SimpleTableSorter tableSorter;

	@Override
	public Object getValueAt(int row, int col) {
		int rowIndex = row;
		if (indexes != null) {
			rowIndex = indexes[row];
		}
		return super.getValueAt(rowIndex, col);
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		int rowIndex = row;
		if (indexes != null) {
			rowIndex = indexes[row];
		}
		super.setValueAt(value, rowIndex, col);
	}

	public void sortByColumn(int column, boolean isAscent) {
		tableSorter.sort(getIndexes(), column, isAscent);
		fireTableDataChanged();
	}

	// @Override
	public int[] getIndexes() {
		int n = getRowCount();
		if (indexes != null) {
			if (indexes.length == n) {
				return indexes;
			}
		}
		indexes = new int[n];
		for (int i = 0; i < n; i++) {
			indexes[i] = i;
		}
		return indexes;
	}
}
