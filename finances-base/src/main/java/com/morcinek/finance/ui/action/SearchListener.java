package com.morcinek.finance.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.regex.PatternSyntaxException;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchListener implements ActionListener, CaretListener {

	private JTable table;

	@Autowired
	public void setTable(JTable table) {
		this.table = table;
	}

	@SuppressWarnings("unchecked")
	protected TableRowSorter<TableModel> getTableRowSorter() {
		return (TableRowSorter<TableModel>) table.getRowSorter();
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		String text = getText(e);
		if (!text.isEmpty()) {
			filter(text);
		} else {
			getTableRowSorter().setRowFilter(null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String text = getText(e);
		if (!text.isEmpty()) {
			filter(text);
		} else {
			getTableRowSorter().setRowFilter(null);
		}
	}

	private static String getText(EventObject e) {
		return ((JTextField) e.getSource()).getText();
	}

	/**
	 * Update the row filter regular expression from the expression in the text
	 * box.
	 */
	private void filter(String mask) {
		RowFilter<TableModel, Object> rf = null;
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mask.length(); i++) {
				char c = mask.charAt(i);
				sb.append('[').append(Character.toLowerCase(c)).append(Character.toUpperCase(c)).append(']');
			}
			rf = RowFilter.regexFilter(sb.toString());
		} catch (PatternSyntaxException e) {
			return;
		}
		getTableRowSorter().setRowFilter(rf);
	}
}
