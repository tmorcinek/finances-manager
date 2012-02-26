package com.morcinek.finance.ui.components;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BaseButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7029294063000294733L;

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
