package com.morcinek.finance.ui.action;

import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.morcinek.finance.ui.table.model.ListTableModel;

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
	@Autowired(required = true)
	protected ListTableModel tableModel;

	public void setTableModel(ListTableModel tableModel) {
		this.tableModel = tableModel;
	}

}