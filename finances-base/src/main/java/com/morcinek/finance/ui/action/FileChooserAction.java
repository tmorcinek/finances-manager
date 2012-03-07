package com.morcinek.finance.ui.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.morcinek.finance.parse.HistoryParsing;
import com.morcinek.finance.ui.table.BaseTable;
import com.morcinek.finance.ui.table.model.ListTableModel;
import com.morcinek.finance.util.ApplicationContextProvider;
import com.morcinek.finance.util.PropertiesAdapter;

/**
 * FileChooserAction is an implementation of ActionListener after clicking
 * button. Action invokes File Chooser by which we can choose the right csv
 * file.
 * 
 * @author Tomasz Morcinek
 * @date 17-02-2012
 * @time 02:43:25
 * 
 */
@org.springframework.stereotype.Component
public class FileChooserAction implements ActionListener {

	private FileFilter csvFileFilter = new FileFilter() {

		@Override
		public String getDescription() {
			return "CSV files.";
		}

		@Override
		public boolean accept(File f) {
			return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
		}
	};

	@Autowired
	@Qualifier("mainFrame")
	private Component parent;

	@Autowired(required = true)
	private HistoryParsing historyParsing;
	
	private JFileChooser fileChooser = new JFileChooser();
	
	@Autowired
	public FileChooserAction(PropertiesAdapter propertiesAdapter) {
		fileChooser.setFileFilter(csvFileFilter);
		fileChooser.setCurrentDirectory(new File(
				propertiesAdapter.getProperty("fileChooser_defaultPath")));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int returnVal = fileChooser.showOpenDialog(parent);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();

			try {
				historyParsing.process(file.getAbsolutePath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JDialog dialog = (JDialog) ApplicationContextProvider.getApplicationContext().getBean("mergeDialog");
			BaseTable mergeTable = (BaseTable) ApplicationContextProvider.getApplicationContext().getBean("mergeTable");
			ListTableModel tableModel = mergeTable.getListTableModel();
			tableModel.clear();
			tableModel.setData(historyParsing.getNonPayments());
			tableModel.setData(new ArrayList<List<?>>(historyParsing.getPayments()));
			tableModel.fireTableStructureChanged();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.pack();
			dialog.setVisible(true);
		}
	}

}
