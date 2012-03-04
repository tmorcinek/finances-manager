package com.morcinek.finance.data;

public class Category {

	private final int categoryId;
	private final String categoryName;

	
	public Category(String pCategoryName) {
		this(-1, pCategoryName);
	}
	
	public Category(int pCategoryId, String pCategoryName) {
		categoryId = pCategoryId;
		categoryName = pCategoryName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

}
