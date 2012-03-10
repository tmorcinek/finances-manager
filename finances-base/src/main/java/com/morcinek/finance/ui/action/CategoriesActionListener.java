package com.morcinek.finance.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.morcinek.finance.ui.components.HierarchyPanel;
import com.morcinek.finance.util.Alerts;

@Component
@Scope(value = "prototype")
public class CategoriesActionListener implements ActionListener {

	@Autowired(required = true)
	private HierarchyPanel hierarchyPanel;

	@Autowired(required = true)
	@Qualifier(value = "categoryTextField")
	private JTextField textField;

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if ("add_category".equals(actionCommand)) {
			if (Alerts.getConfirmDialog() == JOptionPane.YES_OPTION) {
				hierarchyPanel.addObject(textField.getText());
				textField.setText("");
			}
		} else if ("delete_category".equals(actionCommand)) {
			if (Alerts.getConfirmDialog() == JOptionPane.YES_OPTION) {
				hierarchyPanel.removeCurrentNode();
			}
		}
	}

}
