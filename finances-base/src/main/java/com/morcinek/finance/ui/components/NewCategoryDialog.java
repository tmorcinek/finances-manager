package com.morcinek.finance.ui.components;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.morcinek.finance.exceptions.FinanceException;
import com.morcinek.finance.util.Alerts;

@Component
@Scope(value = "prototype")
public class NewCategoryDialog extends AddCategoryDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3228200153554105655L;

	private JTextField textField;

	public NewCategoryDialog() {
		super();
	}

	public NewCategoryDialog(Window owner) {
		super(owner);
	}

	@Override
	public void init() {
		super.init();
		textField = (JTextField) borderPanel.getNorthComponent();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if ("add_category".equals(actionCommand)) {
			if (Alerts.getConfirmDialog(this) == JOptionPane.YES_OPTION) {
				try {
					hierarchyPanel.addObject(textField.getText());
					textField.setText("");
				} catch (FinanceException e1) {
					Alerts.showInformationDialog(NewCategoryDialog.this, e1);
				} catch (SQLException e1) {
					Alerts.showErrorDialog(NewCategoryDialog.this, e1);
				}
			}
		} else if ("delete_category".equals(actionCommand)) {
			if (Alerts.getConfirmDialog(this) == JOptionPane.YES_OPTION) {
				try {
					hierarchyPanel.removeCurrentNode();
				} catch (FinanceException e1) {
					Alerts.showInformationDialog(NewCategoryDialog.this, e1);
				} catch (SQLException e1) {
					Alerts.showErrorDialog(NewCategoryDialog.this, e1);
				}
			}
		} else {
			super.actionPerformed(e);
		}
	}

}
