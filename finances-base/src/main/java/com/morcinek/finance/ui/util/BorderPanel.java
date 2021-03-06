package com.morcinek.finance.ui.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

@org.springframework.stereotype.Component
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

	public void setMouseListener(MouseAdapter mouseListener) {
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	}
}
