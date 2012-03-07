package com.morcinek.finance.ui.components;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretListener;

import org.springframework.beans.factory.annotation.Autowired;

public class SearchPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4429309451470690416L;

	private JTextField textField;

	public SearchPanel(String text) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(new Label(text));
		textField = new JTextField();
		add(textField);

		Dimension preferredSize = getPreferredSize();
		preferredSize.width = getMaximumSize().width;
		setMinimumSize(preferredSize);
		setMaximumSize(preferredSize);
	}

	@Autowired
	public void setActionListener(ActionListener actionListener) {
		textField.addActionListener(actionListener);
	}

	@Autowired
	public void setCaretListener(CaretListener caretListener) {
		textField.addCaretListener(caretListener);
	}

}
