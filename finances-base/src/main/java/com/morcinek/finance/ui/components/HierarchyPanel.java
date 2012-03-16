package com.morcinek.finance.ui.components;

import java.awt.Dimension;
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
import com.morcinek.finance.database.Payment;
import com.morcinek.finance.exceptions.FinanceException;

@Component
@Scope(value = "prototype")
public class HierarchyPanel extends JScrollPane {

	private static final String CATEGORY_IS_NOT_SELECTED_ERROR = "Category is not selected.";

	/**
	 * 
	 */
	private static final long serialVersionUID = -19593576449906955L;

	@Autowired(required = true)
	private DBHelper dbHelper;

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

	/**
	 * Remove the currently selected node.
	 * 
	 * @throws SQLException
	 * @throws FinanceException
	 */
	public void removeCurrentNode() throws FinanceException, SQLException {
		DefaultMutableTreeNode currentNode = getSelectedNode();
		if (currentNode != null) {
			MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
			if (parent != null && currentNode.isLeaf()) {
				DBReport deleteCategory = dbHelper.deleteCategory((Category) currentNode.getUserObject());
				if (!deleteCategory.hasError()) {
					treeModel.removeNodeFromParent(currentNode);
					return;
				} else {
					throw deleteCategory.getError();
				}
			} else {
				throw new FinanceException("Category is not a leaf.");
			}
		} else {
			throw new FinanceException(CATEGORY_IS_NOT_SELECTED_ERROR);
		}
	}

	/**
	 * Add child to the currently selected node.
	 * 
	 * @throws FinanceException
	 * 
	 * @throws SQLException
	 */
	public DefaultMutableTreeNode addObject(String child) throws FinanceException, SQLException {
		DefaultMutableTreeNode parentNode = getSelectedNode();
		if (parentNode == null) {
			throw new FinanceException("Parent category is not selected.");
		} else if (child.isEmpty()) {
			throw new FinanceException("Child name is empty.");
		} else {
			return addObject(parentNode, child);
		}
	}

	private DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, String child) throws SQLException {
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
		} else {
			throw addCategory.getError();
		}
	}

	public void addCategoryToPayment(Object payment) throws SQLException, FinanceException {
		try {
			Category userObject = (Category) getSelectedNode().getUserObject();
			DBReport addPaymentCategory = dbHelper.addPaymentCategory((Payment) payment, userObject);
			if (addPaymentCategory.hasError()) {
				throw addPaymentCategory.getError();
			}
		} catch (ClassCastException e) {
			throw new FinanceException(CATEGORY_IS_NOT_SELECTED_ERROR);
		}
	}

	protected DefaultMutableTreeNode getSelectedNode() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}
}
