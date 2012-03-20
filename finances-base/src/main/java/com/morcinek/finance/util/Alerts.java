package com.morcinek.finance.util;

import java.awt.Component;
import java.awt.Window;
import java.sql.SQLException;

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

	@Deprecated
	public static int getConfirmDialog() {
		return getConfirmDialog(confirmationText);
	}
	
	public static void showErrorDialog(Window window, SQLException exception) {
		showErrorDialog(window, exception.getMessage());
	}
	
	public static void showErrorDialog(Window window, Exception exception) {
		showErrorDialog(window, exception.getMessage());
	}
	
	public static void showErrorDialog(Window window, String errorMessage) {
		JOptionPane.showMessageDialog(window, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void showInformationDialog(Window window, Exception exception) {
		showInformationDialog(window, exception.getMessage());
	}
	
	public static void showInformationDialog(Window window, String informationMessage) {
		JOptionPane.showMessageDialog(window, informationMessage, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
}
