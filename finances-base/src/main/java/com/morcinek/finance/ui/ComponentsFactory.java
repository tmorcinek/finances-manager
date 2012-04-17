package com.morcinek.finance.ui;

import java.util.List;

import com.morcinek.finance.ui.table.BaseTable;
import com.morcinek.finance.ui.table.model.ListTableModel;
import com.morcinek.finance.util.ApplicationContextProvider;

public class ComponentsFactory {

	public static BaseTable createBaseTable(List<List<?>> payments, String beanName){
		BaseTable baseTable = (BaseTable) ApplicationContextProvider.getApplicationContext().getBean(beanName);
		ListTableModel tableModel = baseTable.getListTableModel();
		tableModel.clear();
		tableModel.setData(payments);
		return baseTable;
	}
}
