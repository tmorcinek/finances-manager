package com.morcinek.finance.database;

public class Category {

	private final int categoryId;
	private final String categoryName;
	private final Integer parentId;

	public Category(String pCategoryName, int pParentId) {
		this(-1, pCategoryName, pParentId);
	}

	public Category(int pCategoryId, String pCategoryName, Integer pParentId) {
		categoryId = pCategoryId;
		categoryName = pCategoryName;
		parentId = pParentId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public Integer getParentId() {
		return parentId;
	}

	@Override
	public String toString() {
		return categoryName;
	}

}
