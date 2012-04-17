package com.morcinek.finance.ui.components;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CaretListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morcinek.finance.ui.action.TableManipulator;

@Component
public class SearchPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4429309451470690416L;

	private JTextField textField;

	public SearchPanel(String text) {
		super();
		setLayout(new BorderLayout());
		add(new Label(text), BorderLayout.WEST);
		textField = new JTextField();
		add(textField, BorderLayout.CENTER);
	}

	@PostConstruct
	public void init() {
	}

	@Autowired
	public void setActionListener(ActionListener actionListener) {
		textField.addActionListener(actionListener);
	}

	@Autowired
	public void setCaretListener(CaretListener caretListener) {
		textField.addCaretListener(caretListener);
	}

	public void setTable(JTable table) {
		for (ActionListener actionListener : textField.getActionListeners()) {
			((TableManipulator) actionListener).setTable(table);
		}
		for (CaretListener caretListener : textField.getCaretListeners()) {
			((TableManipulator) caretListener).setTable(table);
		}
	}

}
