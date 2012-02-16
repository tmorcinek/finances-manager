package com.morcinek.finance.ui.action;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BaseButton extends JButton {

	public BaseButton() {
		super();
	}

	public BaseButton(String text) {
		super(text);
	}

	public BaseButton(String text, ActionListener actionListener) {
		super(text);
		addActionListener(actionListener);
	}

	public void setActionListener(ActionListener l) {
		super.addActionListener(l);
	}

}
