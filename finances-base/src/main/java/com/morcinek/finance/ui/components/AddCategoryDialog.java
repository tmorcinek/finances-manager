package com.morcinek.finance.ui.components;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.concurrent.Callable;

import javax.swing.JOptionPane;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.morcinek.finance.exceptions.FinanceException;
import com.morcinek.finance.util.Alerts;

@Component
@Scope(value = "prototype")
public class AddCategoryDialog extends AbstractDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2998229927405208958L;

	protected HierarchyPanel hierarchyPanel;

	public AddCategoryDialog() {
		super();
	}

	public AddCategoryDialog(Window owner) {
		super(owner);
	}

	public void showDialog(Callable<?> pCallable, Object... objects) {
		super.showDialog(pCallable, objects);
	}

	@Override
	public void init() {
		super.init();
		hierarchyPanel = (HierarchyPanel) borderPanel.getCenterComponent();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if ("confirm".equals(actionCommand)) {
			if (Alerts.getConfirmDialog(AddCategoryDialog.this) == JOptionPane.YES_OPTION) {
				try {
					hierarchyPanel.addCategoryToPayment(objects[0]);
					if (callable != null) {
						callable.call();
					}
					dispose();
				} catch (FinanceException e1) {
					Alerts.showInformationDialog(this, e1);
				} catch (SQLException e1) {
					Alerts.showErrorDialog(this, e1);
				} catch (Exception e1) {
					Alerts.showErrorDialog(this, e1);
				}
			}
		} else {
			super.actionPerformed(e);
		}
	}

}
