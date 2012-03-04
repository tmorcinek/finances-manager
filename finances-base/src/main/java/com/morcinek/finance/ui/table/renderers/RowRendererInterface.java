package com.morcinek.finance.ui.table.renderers;

import java.awt.Color;

import com.morcinek.finance.Prioritised;

public interface RowRendererInterface extends Prioritised {

	public abstract boolean applies(Object row, int rowNumber);

	public Color getBackgroundColor();

	public Color getForegroundColor();

}
