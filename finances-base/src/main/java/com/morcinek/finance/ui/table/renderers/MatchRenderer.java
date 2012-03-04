package com.morcinek.finance.ui.table.renderers;

import java.awt.Color;
import java.awt.Component;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MatchRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6531303021039453222L;

	private Pattern pattern;

	public MatchRenderer(String regex) {
		super();
		pattern = Pattern.compile(regex);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (pattern.matcher(value.toString()).find()) {
			cell.setForeground(Color.red);
		} else {
			cell.setForeground(null);			
		}
		return cell;
	}

}
