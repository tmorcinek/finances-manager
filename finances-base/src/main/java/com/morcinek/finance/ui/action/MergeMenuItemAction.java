package com.morcinek.finance.ui.action;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.morcinek.finance.database.DBHelper;
import com.morcinek.finance.database.DBReport;
import com.morcinek.finance.database.Payment;
import com.morcinek.finance.parse.HistoryParsing;
import com.morcinek.finance.ui.table.BaseTable;
import com.morcinek.finance.util.Alerts;

@Component
@Scope(value = "prototype")
public class MergeMenuItemAction implements MenuItemActionListener {

	@Autowired(required = true)
	private DBHelper dbHelper;

	@Autowired(required = true)
	@Qualifier(value = "mergeTable")
	private BaseTable baseTable;

	@Autowired(required = true)
	private HistoryParsing historyParsing;

	@Override
	public void actionPerformed(ActionEvent e, Object pObject) {
		String source = e.getActionCommand();
		if ("update_selected".equals(source)) {
			try {
				List<Payment> payments = new ArrayList<Payment>((Collection<Payment>) pObject);
				DBReport dbReport = dbHelper.addPayments(payments);
				System.out.println(dbReport);
				baseTable.refreshRenderers();
				baseTable.getListTableModel().fireTableDataChanged();
				baseTable.getListTableModel().fireTableStructureChanged();
			} catch (SQLException e1) {
				Alerts.showErrorDialog(null, e1);
			} catch (ClassCastException e1) {
				Alerts.showErrorDialog(null, e1);
			}

		}
	}

}
