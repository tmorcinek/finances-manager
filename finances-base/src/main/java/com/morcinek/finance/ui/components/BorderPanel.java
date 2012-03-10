package com.morcinek.finance.ui.components;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

public class BorderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5255387823617470192L;

	private Component centerComponent;
	private Component northComponent;
	private Component southComponent;

	public BorderPanel() {
		super(new BorderLayout());
	}

	public void setCenterComponent(Component component) {
		if (centerComponent != null) {
			remove(centerComponent);
		}
		centerComponent = component;
		add(component, BorderLayout.CENTER);
	}

	public void setNorthComponent(Component component) {
		if (northComponent != null) {
			remove(northComponent);
		}
		northComponent = component;
		add(component, BorderLayout.NORTH);
	}

	public void setSouthComponent(Component component) {
		if (southComponent != null) {
			remove(southComponent);
		}
		southComponent = component;
		add(component, BorderLayout.SOUTH);
	}

	public Component getCenterComponent() {
		return centerComponent;
	}

	public Component getNorthComponent() {
		return northComponent;
	}

	public Component getSouthComponent() {
		return southComponent;
	}

}
