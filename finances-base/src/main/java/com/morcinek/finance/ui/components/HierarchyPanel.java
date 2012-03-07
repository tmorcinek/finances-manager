package com.morcinek.finance.ui.components;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morcinek.finance.database.Category;
import com.morcinek.finance.database.CategoryHelper;
import com.morcinek.finance.database.DBHelper;

@Component
public class HierarchyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -19593576449906955L;

	@Autowired(required = true)
	private DBHelper dbHelper;

	private JTree tree;

	private List<Category> categories;

	@PostConstruct
	public void init() {
		try {
			categories = dbHelper.getCategories();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tree = new JTree();
		tree = new JTree(CategoryHelper.getTreeFromCategories(categories));
		add(new JScrollPane(tree));
		setPreferredSize(new Dimension(100, 600));
	}

	public void setTreeSelectionListener(TreeSelectionListener treeSelectionListener) {
		tree.addTreeSelectionListener(treeSelectionListener);
	}
}
