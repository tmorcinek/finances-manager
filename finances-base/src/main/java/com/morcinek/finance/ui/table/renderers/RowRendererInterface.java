package com.morcinek.finance.ui.table.renderers;

import java.awt.Color;

import com.morcinek.finance.util.Prioritised;

public interface RowRendererInterface extends Prioritised {

	public abstract boolean applies(Object row, int originalRowNumber);

	public Color getBackgroundColor();

	public Color getForegroundColor();

	public void refresh();
}
