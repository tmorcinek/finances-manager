package com.morcinek.finance.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.morcinek.finance.database.DBHelper;
import com.morcinek.finance.ui.table.BaseTable;
import com.morcinek.finance.ui.table.model.ListTableModel;

/**
 * FileChooserAction is an implementation of ActionListener after clicking
 * button. Action invokes File Chooser by which we can choose the right csv
 * file.
 * 
 * @author Tomasz Morcinek
 * @date 17-02-2012
 * @time 02:43:25
 * 
 */
@org.springframework.stereotype.Component
public class PopulateActionListener implements ActionListener {

	@Autowired(required = true)
	private DBHelper dbHelper;

	@Autowired
	@Qualifier(value = "mainTable")
	private BaseTable baseTable;

	@Override
	public void actionPerformed(ActionEvent e) {
		ListTableModel tableModel = baseTable.getListTableModel();
		tableModel.clear();
		try {
			tableModel.setData(new ArrayList<List<?>>(dbHelper.getPayments()));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		tableModel.fireTableStructureChanged();
	}

}
