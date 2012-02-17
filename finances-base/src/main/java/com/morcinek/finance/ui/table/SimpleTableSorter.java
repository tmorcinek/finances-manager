package com.morcinek.finance.ui.table;

import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * SimpleTableSorter is a class which implements simple sorting algorithm.
 * 
 * @author Tomasz Morcinek
 * @date 17-02-2012
 * @time 02:37:31
 * 
 */
@Component
public class SimpleTableSorter {

	private TableModel tableModel;

	@Autowired(required = true)
	@Qualifier("sortableTableModel")
	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public void sort(int[] indexes, int column, boolean isAscent) {
		int n = tableModel.getRowCount();

		for (int i = 0; i < n - 1; i++) {
			int k = i;
			for (int j = i + 1; j < n; j++) {
				if (isAscent) {
					if (compare(column, j, k) < 0) {
						k = j;
					}
				} else {
					if (compare(column, j, k) > 0) {
						k = j;
					}
				}
			}
			int tmp = indexes[i];
			indexes[i] = indexes[k];
			indexes[k] = tmp;
		}
	}

	@SuppressWarnings("unchecked")
	private int compare(int column, int row1, int row2) {
		Object o1 = tableModel.getValueAt(row1, column);
		Object o2 = tableModel.getValueAt(row2, column);
		try {
			return ((Comparable<Object>) o1).compareTo(o2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
