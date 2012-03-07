package com.morcinek.finance.ui.table.renderers;

import java.awt.Color;

public abstract class AbstractRowRenderer implements RowRendererInterface {

	@Override
	public Color getBackgroundColor() {
		return null;
	}

	@Override
	public Color getForegroundColor() {
		return null;
	}

	@Override
	public void refresh() {
	}

}