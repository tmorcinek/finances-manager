package com.morcinek.finance.ui.components;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.morcinek.finance.database.Category;
import com.morcinek.finance.database.CategoryHelper;
import com.morcinek.finance.database.DBHelper;
import com.morcinek.finance.database.DBReport;

@Component
@Scope(value = "prototype")
public class HierarchyPanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -19593576449906955L;

	@Autowired(required = true)
	private DBHelper dbHelper;

	private static Toolkit toolkit = Toolkit.getDefaultToolkit();

	private JTree tree;

	private List<Category> categories;

	private DefaultTreeModel treeModel;

	@PostConstruct
	public void init() {
		setPreferredSize(new Dimension(300, 150));
		try {
			categories = dbHelper.getCategories();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		treeModel = new DefaultTreeModel(CategoryHelper.getTreeFromCategories(categories));
		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		getViewport().add(tree);
		setOpaque(true);
	}

	/** Remove the currently selected node. */
	public void removeCurrentNode() {
		TreePath currentSelection = tree.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
			MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
			if (parent != null && currentNode.isLeaf()) {
				DBReport deleteCategory = dbHelper.deleteCategory((Category) currentNode.getUserObject());
				if (!deleteCategory.hasError()) {
					treeModel.removeNodeFromParent(currentNode);
					return;
				}
			}
		}
		toolkit.beep();
	}

	/** Add child to the currently selected node. */
	public DefaultMutableTreeNode addObject(String child) {
		TreePath parentPath = tree.getSelectionPath();
		if (parentPath != null && !child.isEmpty()) {
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
			return addObject(parentNode, child);
		}
		toolkit.beep();
		return null;
	}

	private DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, String child) {
		Category newCategory;
		try {
			newCategory = new Category(child, ((Category) parent.getUserObject()).getCategoryId());
		} catch (ClassCastException e1) {
			newCategory = new Category(child, null);
		}
		DBReport addCategory = dbHelper.addCategory(newCategory);
		if (!addCategory.hasError()) {
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newCategory);
			treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
			return childNode;
		}

		toolkit.beep();
		return null;
	}

	public DefaultMutableTreeNode getSelectedNode() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}
}
