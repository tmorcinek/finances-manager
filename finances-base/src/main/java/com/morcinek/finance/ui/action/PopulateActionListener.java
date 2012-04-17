package com.morcinek.finance.ui.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import com.morcinek.finance.database.DBHelper;
import com.morcinek.finance.ui.ComponentsFactory;
import com.morcinek.finance.ui.components.TagsDialog;
import com.morcinek.finance.ui.components.bookmark.Bookmarks;
import com.morcinek.finance.ui.table.BaseTable;
import com.morcinek.finance.util.Alerts;
import com.morcinek.finance.util.ApplicationContextProvider;

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
	private Bookmarks bookmarks;

	@Autowired(required = true)
	private DBHelper dbHelper;

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if ("populateTable".equals(actionCommand)) {
			try {
				ArrayList<List<?>> pData = new ArrayList<List<?>>(dbHelper.getPayments());
				BaseTable baseTable2 = ComponentsFactory.createBaseTable(pData, "mainTable");
				bookmarks.addBaseTable("Main Table", baseTable2);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			TagsDialog dialog = null;
			try {
				dialog = (TagsDialog) ApplicationContextProvider.getApplicationContext().getBean("tagsDialog",
						bookmarks.getSelectedTable().getListTableModel());
				dialog.setLocationRelativeTo((Component) e.getSource());
				dialog.showDialog(null);
			} catch (Exception e1) {
				Alerts.showErrorDialog(dialog, "You cannot show categories dialog now.");
			}
		}
	}

}
