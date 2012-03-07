package com.morcinek.finance.ui.action;

import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morcinek.finance.database.Payment;
import com.morcinek.finance.ui.components.PaymentDialog;
import com.morcinek.finance.ui.table.BaseTable;
import com.morcinek.finance.util.ApplicationContextProvider;

@Component
public class PaymentDialogMouseListener extends MouseAdapter {

	private Window window;

	@Autowired
	public void setWindow(Window window) {
		this.window = window;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			BaseTable target = (BaseTable) e.getSource();
			int convertRowIndexToModel = target.getSelectedRow();
			if (target.getRowSorter() != null) {
				convertRowIndexToModel = target.getRowSorter().convertRowIndexToModel(target.getSelectedRow());
			}
			try {
				Payment payment = (Payment) target.getListTableModel().getRowAt(convertRowIndexToModel);
				PaymentDialog paymentDialog = (PaymentDialog) ApplicationContextProvider.getApplicationContext().getBean(
						"paymentDialog", window);
				paymentDialog.update(payment);
			} catch (ClassCastException e1) {
				// This object is not of a class Payment
			}
		}
		super.mouseClicked(e);
	}

}
