package com.morcinek.finance.util;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Alerts {

	private static final String confirmationText = "Are you sure, you want to\n commit this operation?";

	public static int getConfirmDialog(Component component, String textMessage) {
		int result = JOptionPane.showConfirmDialog(component, textMessage, "alert", JOptionPane.OK_CANCEL_OPTION);
		return result;
	}

	public static int getConfirmDialog(String textMessage) {
		return getConfirmDialog(null, textMessage);
	}

	public static int getConfirmDialog(Component component) {
		return getConfirmDialog(component, confirmationText);
	}

	public static int getConfirmDialog() {
		return getConfirmDialog(confirmationText);
	}

}
