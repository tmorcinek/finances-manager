package com.morcinek.finance.ui.table.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.morcinek.finance.database.Payment;

@Component
@Scope("prototype")
public class PaymentsTableModel extends ListTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<Integer, Payment> mapData = new HashMap<Integer, Payment>();

	private int[] indexes;

	@Override
	public void clear() {
		super.clear();
		mapData.clear();
	}

	@Override
	public void setData(List<List<?>> pData) {
		super.setData(pData);
		for (List<?> list : pData) {
			Payment payment = (Payment) list;
			mapData.put(payment.getPaymentId(), payment);
		}
	}

	public void setIndexes(int[] indexes) {
		this.indexes = indexes;
		fireTableStructureChanged();
		fireTableDataChanged();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (indexes != null) {
			List<?> rowData = getRowAt(rowIndex);
			if (rowData.size() > columnIndex) {
				return rowData.get(columnIndex);
			}
		}
		return super.getValueAt(rowIndex, columnIndex);
	}

	@Override
	public int getRowCount() {
		if (indexes != null) {
			return indexes.length;
		}
		return super.getRowCount();
	}

	public List<?> getRowAt(int rowIndex) {
		if (indexes != null) {
			return mapData.get(indexes[rowIndex]);
		}
		return super.getRowAt(rowIndex);
	}
}
