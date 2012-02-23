package com.morcinek.finance.ui.table.sorting;

public interface TableSorterInterface {

	public void sort(int[] indexes, int column, boolean isAscent);

	public void addSort(int[] indexes, int column, boolean isAscent);

	// public void clear();

}
