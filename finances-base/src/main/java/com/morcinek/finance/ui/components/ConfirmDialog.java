package com.morcinek.finance.ui.components;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.concurrent.Callable;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.morcinek.finance.database.Category;
import com.morcinek.finance.database.DBHelper;
import com.morcinek.finance.database.DBReport;
import com.morcinek.finance.database.Payment;
import com.morcinek.finance.util.Alerts;
import com.morcinek.finance.util.ApplicationContextProvider;
import com.morcinek.properties.Features;

@org.springframework.stereotype.Component
public class ConfirmDialog extends AbstractDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2998229927405208958L;

	@Autowired(required = true)
	private DBHelper dbHelper;

	private HierarchyPanel hierarchyPanel;

	public ConfirmDialog() {
		super();
	}

	public ConfirmDialog(Window owner) {
		super(owner);
	}

	public void show(Callable<?> pCallable, Object... objects) {
		setTitle("Confirm Dialog");
		super.show(pCallable, objects);
		// creating bean from applicationContext
		BorderPanel borderPanel = (BorderPanel) ApplicationContextProvider.getApplicationContext().getBean(
				"confirmPanel");
		getContentPane().add(borderPanel);

		hierarchyPanel = (HierarchyPanel) borderPanel.getCenterComponent();
		JPanel southPanel = (JPanel) borderPanel.getSouthComponent();
		for (Component component : southPanel.getComponents()) {
			((JButton) component).addActionListener(this);
		}
		pack();
		setVisible(true);
		setResizable(Features.paymentDialog_resizable);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if ("confirm".equals(actionCommand)) {
			if (Alerts.getConfirmDialog(ConfirmDialog.this) == JOptionPane.YES_OPTION) {
				try {
					Category userObject = (Category) hierarchyPanel.getSelectedNode().getUserObject();
					DBReport addPaymentCategory = dbHelper.addPaymentCategory((Payment) objects[0], userObject);
					if (!addPaymentCategory.hasError()) {
						callable.call();
						dispose();
					} else {
						toolkit.beep();
					}
				} catch (Exception e2) {
					toolkit.beep();
				}
			}
		} else {
			super.actionPerformed(e);
		}
	}

}
