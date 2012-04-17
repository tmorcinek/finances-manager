package com.morcinek.finance.ui.util;

import java.awt.Component;
import java.util.List;

import javax.swing.JPopupMenu;


@org.springframework.stereotype.Component
public class BasePopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4398679976052318884L;

	private Object object;

	public BasePopupMenu(List<Object> menuItems){
		setMenuItems(menuItems);
	}
	
	public BasePopupMenu() {
		super();
	}

	public void update(Object pObject) {
		object = pObject;
		int size;
		try {
			size = ((List<Object>) object).size();
		} catch (ClassCastException e) {
			size = 1;
		}
		for (Component component : getComponents()) {
			if (component instanceof BaseMenuItem) {
				BaseMenuItem baseMenuItem = (BaseMenuItem) component;
				baseMenuItem.update(size);
			}
		}
	}

	public Object getObject() {
		return object;
	}

	public void setMenuItems(List<Object> menuItems) {
		for (Object menuItem : menuItems) {
			if (menuItem instanceof BaseMenuItem) {
				BaseMenuItem baseMenuItem = (BaseMenuItem) menuItem;
				baseMenuItem.setBasePopupMenu(this);
				add(baseMenuItem);
			} else if (menuItem.equals("separator")) {
				addSeparator();
			}
		}
	}

}
