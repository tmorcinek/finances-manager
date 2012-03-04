package com.morcinek.finance.ui.table.renderers;

import java.awt.Color;

import org.springframework.stereotype.Component;

@Component
public class SimpleRowRenderer extends AbstractRowRenderer {

	private int rowNumber;

	@Override
	public boolean applies(Object row, int rowNumber) {
		this.rowNumber = rowNumber;
		return true;
	}

	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	public Color getBackgroundColor() {
		return (rowNumber % 2 == 0) ? Color.gray : Color.white;
	}
}
