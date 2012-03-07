package com.morcinek.finance.data;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.morcinek.finance.database.Category;
import com.morcinek.finance.ui.MainFrame;
import com.morcinek.finance.ui.components.HierarchyPanel;

public class CategoryHelperTest {

	private ClassPathXmlApplicationContext classPathXmlApplicationContext;
	private MainFrame layoutPanel;

	public static void main(String[] args) {
		CategoryHelperTest categoryHelperTest = new CategoryHelperTest();
		categoryHelperTest.setUp();
	}
	
	public void setUp() {
		String[] contextPath = new String[] { "context_test.xml" };
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext(contextPath);
		layoutPanel = (MainFrame) classPathXmlApplicationContext.getBean("mainFrame");
		test();
	}

	public void test() {
		List<Category> categories = new ArrayList<Category>();
		categories.add(new Category(1, "Sport", null));
		categories.add(new Category(2, "nozna", 1));
		categories.add(new Category(3, "Koszykowka", 1));
		categories.add(new Category(4, "halowka", 2));
		categories.add(new Category(5, "na dwoze", 2));
		categories.add(new Category(6, "pierdolenie", 6));
		categories.add(new Category(7, "spanie", null));
		categories.add(new Category(8, "na dwoze", 6));
	}

}
