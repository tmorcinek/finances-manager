package com.morcinek.finance.ui.components.bookmark;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.springframework.stereotype.Component;

import com.morcinek.finance.ui.table.BaseTable;
import com.morcinek.finance.util.ApplicationContextProvider;

@Component
public class Bookmarks extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8264586556612567550L;

	public Bookmarks() {
		removeAll();
		setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
	}

	public void addBaseTable(String title, BaseTable baseTable) {
		BaseTableTab baseTableTab = (BaseTableTab) ApplicationContextProvider.getApplicationContext().getBean("baseTableTab",
				baseTable);
		add(title, baseTableTab);
		int componentCount = getTabCount();
		setTabComponentAt(componentCount - 1, new ButtonTabComponent(this));
	}

	public BaseTable getSelectedTable() {
		return ((BaseTableTab) getSelectedComponent()).getBaseTable();
	}
}
