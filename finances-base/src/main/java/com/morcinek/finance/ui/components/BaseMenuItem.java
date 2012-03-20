package com.morcinek.finance.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morcinek.finance.ui.action.MenuItemActionListener;
import com.morcinek.finance.util.ResourcesAdapter;

@Component
public class BaseMenuItem extends JMenuItem implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6976423903405834916L;

	@Autowired
	private ResourcesAdapter resourcesAdapter;

	private MenuItemActionListener menuItemActionListener;

	private BasePopupMenu basePopupMenu;

	private int maxObjects = -1;

	public BaseMenuItem() {
		super();
	}

	@PostConstruct
	protected void init() {
		addActionListener(this);
	}
	
	public void update(int objectsCount){
		if(maxObjects != -1 && maxObjects < objectsCount){
			setVisible(false);
		} else if (!isVisible()){
			setVisible(true);			
		}
	}

	public void setIcon(String imageName) {
		Icon icon = resourcesAdapter.getIcon(imageName);
		if (icon != null) {
			setIcon(icon);
		}
	}
	
	public void setMaxObjects(int maxObjects){
		this.maxObjects = maxObjects;
	}

	public void setBasePopupMenu(BasePopupMenu object) {
		basePopupMenu = object;
	}

	public void setMenuItemActionListener(MenuItemActionListener menuItemActionListener) {
		this.menuItemActionListener = menuItemActionListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		menuItemActionListener.actionPerformed(e, basePopupMenu.getObject());
	}

}
