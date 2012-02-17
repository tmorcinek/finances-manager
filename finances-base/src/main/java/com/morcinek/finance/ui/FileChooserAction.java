package com.morcinek.finance.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.morcinek.finance.data.Payment;
import com.morcinek.finance.parse.HistoryParsing;
import com.morcinek.finance.ui.table.ListTableActionListener;
import com.morcinek.finance.ui.table.model.ListTableModel;

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
public class FileChooserAction extends ListTableActionListener {

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

	private JFileChooser fileChooser = new JFileChooser();

	@Autowired(required = true)
	private HistoryParsing historyParsing;

	public FileChooserAction() {
		fileChooser.setFileFilter(csvFileFilter);
		fileChooser.setCurrentDirectory(new File(
				"c:/workspaces/finance_manager/finance-base/src/main/resources/history/"));
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
			List<Payment> payments = historyParsing.getPayments();
			((ListTableModel) tableModel).setData(new ArrayList<List<?>>(payments));
			tableModel.fireTableStructureChanged();

		}
	}

}
