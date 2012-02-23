package com.morcinek.finance.ui.table.sorting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.morcinek.finance.data.Payment;
import com.morcinek.finance.parse.HistoryParsing;
import com.morcinek.finance.ui.table.model.ListTableModel;
import com.morcinek.finance.ui.table.model.SortableTableModel;

public class MultiColumnTableSorterTest {

//	@Autowired(required = true)
	protected AbstractTableModel tableModel;
	
//	@Autowired(required = true)
	private HistoryParsing historyParsing;
	
	private MultiColumnTableSorter multiColumnTableSorter;

	private File file = new File("d:/repositories/git-finances-manager/finances-base/src/test/resources/historia.csv");

	private ClassPathXmlApplicationContext classPathXmlApplicationContext;

	@Before
	public void setUp() {
		String[] contextPath = new String[] { "context_test.xml" };
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext(contextPath);
		
		historyParsing = (HistoryParsing) classPathXmlApplicationContext.getBean("historyParsing");
		tableModel = (AbstractTableModel)classPathXmlApplicationContext.getBean("sortableTableModel");
		multiColumnTableSorter = (MultiColumnTableSorter)classPathXmlApplicationContext.getBean("multiColumnTableSorter");
		
		try {
			historyParsing.process(file.getAbsolutePath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		List<Payment> payments = historyParsing.getPayments();
		((ListTableModel) tableModel).setData(new ArrayList<List<?>>(payments));
		

//		MultiColumnTableSorter columnTableSorter = new MultiColumnTableSorter();
	}
	
	@After
	public void tearDown(){
		classPathXmlApplicationContext.close();
	}

	@Test
	public void test() {
		int[] indexes = ((SortableTableModel)tableModel).getIndexes();
		
		multiColumnTableSorter.sort(indexes, 0, true);
	}

}
