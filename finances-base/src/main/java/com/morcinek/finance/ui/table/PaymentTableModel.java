package com.morcinek.finance.ui.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.stereotype.Component;

import com.morcinek.finance.data.Payment;

@Component
public class PaymentTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Payment> payments = new ArrayList<Payment>();

	public void setItemList(List<Payment> itemList) {
		payments = itemList;
	}

	@Override
	public int getRowCount() {
		if (payments != null) {
			return payments.size();
		}
		return 0;
	}

	@Override
	public int getColumnCount() {
		if (payments != null && payments.size() > 0) {
			return payments.get(0).size();
		}
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Payment payment = payments.get(rowIndex);
		return payment.get(columnIndex);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public String getColumnName(int column) {
		return super.getColumnName(column);
	}

	@Override
	public Class<?> getColumnClass(int column) {
		if (payments != null && payments.size() > 0) {
			return payments.get(0).get(column).getClass();
		}
		return super.getColumnClass(column);
	}
	
	

}
