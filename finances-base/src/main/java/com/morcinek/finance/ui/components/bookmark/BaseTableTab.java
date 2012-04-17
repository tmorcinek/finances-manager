package com.morcinek.finance.ui.components.bookmark;

import javax.annotation.PostConstruct;
import javax.swing.JScrollPane;

import org.springframework.stereotype.Component;

import com.morcinek.finance.ui.components.SearchPanel;
import com.morcinek.finance.ui.table.BaseTable;
import com.morcinek.finance.ui.util.BorderPanel;

@Component
public class BaseTableTab extends BorderPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseTableTab(BaseTable baseTable) {
		super();
		setCenterComponent(new JScrollPane(baseTable));
	}

	@PostConstruct
	public void init() {
		((SearchPanel) getNorthComponent()).setTable(getBaseTable());
	}
	
	public BaseTable getBaseTable() {
		return (BaseTable) ((JScrollPane) getCenterComponent()).getViewport().getComponent(0);
	}

}
