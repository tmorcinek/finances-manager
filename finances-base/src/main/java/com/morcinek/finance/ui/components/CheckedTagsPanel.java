package com.morcinek.finance.ui.components;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morcinek.finance.database.Category;
import com.morcinek.finance.database.DBHelper;

@Component
public class CheckedTagsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4287308995130746808L;

	@Autowired(required = true)
	private DBHelper dbHelper;

	private Map<String, Category> categoriesMap = new HashMap<String, Category>();

	public CheckedTagsPanel() throws SQLException {
		super();
	}

	@PostConstruct
	public void init() throws SQLException {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		ButtonGroup group = new ButtonGroup();
		List<Category> categories = dbHelper.getCategories();
		for (Category category : categories) {
			categoriesMap.put(category.getCategoryName(), category);
			AbstractButton chk = new JCheckBox(category.getCategoryName());
//			group.add(chk);
			add(chk);
		}
		setOpaque(true);
	}

	public List<Category> getSelectedCategories() {
		List<Category> categories = new ArrayList<Category>();
		for (java.awt.Component component : getComponents()) {
			AbstractButton abstractButton = (AbstractButton) component;
			if (abstractButton.isSelected()) {
				categories.add(categoriesMap.get(abstractButton.getText()));
			}
		}
		return categories;
	}
	
	public void clearSelected(){
		for (java.awt.Component component : getComponents()) {
			((AbstractButton) component).setSelected(false);
		}
	}
}
