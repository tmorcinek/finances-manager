package com.morcinek.finance.ui.components;

import java.awt.Component;
import java.awt.LayoutManager;
import java.util.List;

import javax.swing.JPanel;

@org.springframework.stereotype.Component
public class BasePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7844406355919317836L;

	/**
	 * We can't use "components" as the property name, because it conflicts with
	 * an existing property on the Component superclass.
	 */
	private List<Component> panelComponents;

	public BasePanel() {
		super();
	}

	public BasePanel(LayoutManager layout) {
		super(layout);
	}

	public void setPanelComponents(List<Component> panelComponents) {
		this.panelComponents = panelComponents;
	}

	public void init() {
		for (Component component : panelComponents) {
			add(component);
		}
		setOpaque(true);
	}
}
