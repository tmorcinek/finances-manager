package com.morcinek.finance.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

public class CategoryHelper {

	public static DefaultMutableTreeNode getTreeFromCategories(List<Category> categories) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Categories");
		Map<Integer, DefaultMutableTreeNode> categoriesTreeNodes = new HashMap<Integer, DefaultMutableTreeNode>();
		for(Category child : categories){
			categoriesTreeNodes.put(child.getCategoryId(), new DefaultMutableTreeNode(child));
		}
		for(Category category : categories){
			DefaultMutableTreeNode categoryMutableTreeNode = categoriesTreeNodes.get(category.getCategoryId());
			if(category.getParentId() == null || category.getParentId() == category.getCategoryId()){
				root.add(categoryMutableTreeNode);
			} else {
				categoriesTreeNodes.get(category.getParentId()).add(categoryMutableTreeNode);
			}
		}
		return root;
	}
	
	
}
