package com.morcinek.finance.ui.components;

import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuListener;

public abstract class AbstractPopupMenu extends JPopupMenu implements PopupMenuListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4398679976052318884L;

	public AbstractPopupMenu() {
		super();
		addPopupMenuListener(this);
	}

}
