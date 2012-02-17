package com.morcinek.finance.ui.table;

import java.awt.event.ActionListener;

import javax.swing.table.AbstractTableModel;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * ListTableActionListener is a simple ActionListener implementing class which
 * can manipulate on date in the table.
 * 
 * @author Tomasz Morcinek
 * @date 17-02-2012
 * @time 02:42:08
 * 
 */
public abstract class ListTableActionListener implements ActionListener {
	protected AbstractTableModel tableModel;

	@Autowired(required = true)
	public void setTableModel(AbstractTableModel tableModel) {
		this.tableModel = tableModel;
	}

}