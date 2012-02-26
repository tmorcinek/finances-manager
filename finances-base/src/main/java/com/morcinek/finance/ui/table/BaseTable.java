package com.morcinek.finance.ui.table;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class BaseTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2860282823670463361L;

	public BaseTable() {
		super();
	}

	public void init() {
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(getModel()){
			
		};
	}

}
