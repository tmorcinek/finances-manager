package com.morcinek.finance.ui.components;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.morcinek.finance.database.DBHelper;
import com.morcinek.finance.ui.table.model.PaymentsTableModel;
import com.morcinek.finance.ui.util.AbstractDialog;
import com.morcinek.finance.util.Alerts;

@Component
public class TagsDialog extends AbstractDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2998229927405208958L;
	
	private PaymentsTableModel paymentsTableModel;
	
	@Autowired
	private DBHelper dbHelper;

	protected CheckedTagsPanel checkedTagsPanel;

	public TagsDialog() {
		super();
	}

	public TagsDialog(Window owner) {
		super(owner);
	}
	
	public TagsDialog(PaymentsTableModel paymentsTableModel) {
		this();
		this.paymentsTableModel = paymentsTableModel;
	}

	public void showDialog(Callable<?> pCallable, Object... objects) {
		super.showDialog(pCallable, objects);
	}

	@Override
	public void init() {
		super.init();
		checkedTagsPanel = (CheckedTagsPanel) borderPanel.getCenterComponent();
		checkedTagsPanel.clearSelected();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if ("confirm".equals(actionCommand)) {
			try {
				if (!checkedTagsPanel.getSelectedCategories().isEmpty()) {
					Set<Integer> paymentsIds = dbHelper.getPaymentsIdsByCategory(checkedTagsPanel
							.getSelectedCategories());
					int[] indexes = new int[paymentsIds.size()];
					int i = 0;
					for (Integer integer : paymentsIds) {
						indexes[i++] = integer;
					}
					paymentsTableModel.setIndexes(indexes);
				} else {
					paymentsTableModel.setIndexes(null);
				}
				dispose();
			} catch (SQLException e1) {
				Alerts.showErrorDialog(TagsDialog.this, e1);
			}
		} else {
			super.actionPerformed(e);
		}
	}

}
