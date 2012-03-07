package com.morcinek.finance.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.morcinek.finance.database.DBHelper;
import com.morcinek.finance.database.DBReport;
import com.morcinek.finance.database.Payment;
import com.morcinek.finance.parse.HistoryParsing;
import com.morcinek.finance.ui.table.BaseTable;
import com.morcinek.finance.ui.table.model.ListTableModel;

@Component
public class MergeActionListener implements ActionListener {

	@Autowired(required = true)
	private DBHelper dbHelper;

	@Autowired(required = true)
	@Qualifier(value = "mergeTable")
	private BaseTable baseTable;

	@Autowired(required = true)
	@Qualifier(value = "mergeDialog")
	private JDialog dialog;

	@Autowired(required = true)
	private HistoryParsing historyParsing;

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if (source.getName().equals("update_all")) {
			try {
				DBReport dbReport = dbHelper.addPayments(historyParsing.getPayments());
				System.out.println(dbReport);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (source.getName().equals("update_selected")) {
			int[] selectedRows = baseTable.getSelectedRows();
			ListTableModel listTableModel = baseTable.getListTableModel();
			List<Payment> payments = new ArrayList<Payment>();
			for (int i : selectedRows) {
				try {
					Payment payment = (Payment) listTableModel.getRowAt(i);
					payments.add(payment);
				} catch (ClassCastException e1) {
					e1.printStackTrace();
				}
			}
			try {
				DBReport dbReport = dbHelper.addPayments(payments);
				System.out.println(dbReport);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			dialog.dispose();
		}
		baseTable.refreshRenderers();
		baseTable.getListTableModel().fireTableDataChanged();
		baseTable.getListTableModel().fireTableStructureChanged();
	}

}
