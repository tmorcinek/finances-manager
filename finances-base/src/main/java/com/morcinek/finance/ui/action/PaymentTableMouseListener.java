package com.morcinek.finance.ui.action;

import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morcinek.finance.database.Payment;
import com.morcinek.finance.ui.components.PaymentDialog;
import com.morcinek.finance.ui.table.BaseTable;
import com.morcinek.finance.ui.util.BasePopupMenu;
import com.morcinek.finance.util.ApplicationContextProvider;

@Component
public class PaymentTableMouseListener extends MouseAdapter {

	private Window window;

	private BasePopupMenu basePopupMenu;

	@Autowired
	public void setWindow(Window window) {
		this.window = window;
	}
	
	@Autowired(required=true)
	public void setBasePopupMenu(BasePopupMenu basePopupMenu) {
		this.basePopupMenu = basePopupMenu;
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			BaseTable baseTable = (BaseTable) e.getSource();
			int convertRowIndexToModel = baseTable.getSelectedRow();
			if (baseTable.getRowSorter() != null) {
				convertRowIndexToModel = baseTable.getRowSorter().convertRowIndexToModel(baseTable.getSelectedRow());
			}
			try {
				Payment payment = (Payment) baseTable.getListTableModel().getRowAt(convertRowIndexToModel);
				PaymentDialog paymentDialog = (PaymentDialog) ApplicationContextProvider.getApplicationContext()
						.getBean("paymentDialog", window);
				paymentDialog.update(payment);
			} catch (ClassCastException e1) {
				// This object is not of a class Payment
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			BaseTable baseTable = (BaseTable) e.getSource();
			int rowNumber = baseTable.rowAtPoint(e.getPoint());
			int[] selectedRows = baseTable.getSelectedRows();
			if (Arrays.binarySearch(selectedRows, rowNumber) < 0) {
				baseTable.getSelectionModel().setSelectionInterval(rowNumber, rowNumber);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			BaseTable baseTable = (BaseTable) e.getSource();
			int[] selectedRows = baseTable.getSelectedRows();
			if (baseTable.getRowSorter() != null) {
				int[] sortedRows = new int[selectedRows.length];
				for (int i = 0; i < selectedRows.length; i++) {
					sortedRows[i] = baseTable.getRowSorter().convertRowIndexToModel(selectedRows[i]);
				}
				selectedRows = sortedRows;
			}
			List<Payment> payments = new ArrayList<Payment>();
			for (int i : selectedRows) {
				Payment payment = (Payment) baseTable.getListTableModel().getRowAt(i);
				payments.add(payment);
			}
			basePopupMenu.update(payments);
			basePopupMenu.show(baseTable, e.getX(), e.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		BaseTable baseTable = (BaseTable) e.getSource();
		int rowNumber = baseTable.rowAtPoint(e.getPoint());
		baseTable.setHighlightedRow(rowNumber);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		BaseTable baseTable = (BaseTable) e.getSource();
		baseTable.setHighlightedRow(-1);
	}

}
