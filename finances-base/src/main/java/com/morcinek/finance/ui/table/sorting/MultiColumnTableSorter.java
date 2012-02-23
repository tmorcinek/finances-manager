package com.morcinek.finance.ui.table.sorting;

import java.util.Arrays;

import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * SimpleTableSorter is a class which implements simple sorting algorithm
 * (BubleSort).
 * 
 * @author Tomasz Morcinek
 * @date 17-02-2012
 * @time 02:37:31
 * 
 */
@Component
public class MultiColumnTableSorter implements TableSorterInterface {

	private TableModel tableModel;

	/**
	 * Table with values of indexes. This field is needed while sorting with
	 * more than one column. Table <code>values</code> works like this:
	 * <blockquote> <code>values[k]</code> - is a value of on index
	 * <code>k</code>
	 * 
	 * </blockquote>
	 */
	private int[] values;

	@Autowired(required = true)
	@Qualifier("sortableTableModel")
	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public void sort(int[] indexes, int column, boolean isAscent) {
		values = new int[indexes.length];
		System.out.println(Arrays.toString(indexes));
		sortColumn(indexes, column, isAscent);
		System.out.println(Arrays.toString(indexes));
		System.out.println(Arrays.toString(values));		
	}

	@Override
	public void addSort(int[] indexes, int column, boolean isAscent) {
		for (int i = 0; i < indexes.length - 1; i++) {
			values[i] *= indexes.length;
		}
//		int[] indexes2
		sortColumn(indexes, column, isAscent);
	}

	/**
	 * Method sorts indexes by comparing values in given column.
	 * 
	 * @param indexes
	 *            (int[]) table of indexes which will be sorted.
	 * @param column
	 *            (int) given column by which we sort the indexes.
	 * @param isAscent
	 *            (boolean) indicates if the sorting should be ascending
	 *            <code>true</code> or descending <code>false</code>.
	 */
	private void sortColumn(int[] indexes, int column, boolean isAscent) {
		int n = indexes.length;
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
			values[indexes[i]] = i;
		}
	}

	/**
	 * This method compares objects in given column and rows.
	 * 
	 * @param column
	 *            (int) Column from which we want to get objects to compare.
	 * @param row1
	 *            (int) first row from which we want to get objects to compare.
	 * @param row2
	 *            (int) second row from which we want to get objects to compare.
	 * @return
	 */
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
