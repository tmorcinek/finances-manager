package com.morcinek.finance.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.JTableHeader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.morcinek.finance.data.Payment;
import com.morcinek.finance.parse.HistoryParsing;
import com.morcinek.finance.ui.table.ListTableActionListener;
import com.morcinek.finance.ui.table.PaymentTableModel;

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
	
	@Autowired(required=true)
	private HistoryParsing historyParsing;

	public FileChooserAction() {
		fileChooser.setFileFilter(csvFileFilter);
		fileChooser.setCurrentDirectory(new File("c:/workspaces/finance_manager/finance-base/src/main/resources/history/"));
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
			PaymentTableModel tableModel = (PaymentTableModel) table.getModel();
			tableModel.setItemList(payments);
			tableModel.fireTableStructureChanged();
			
			JTableHeader tableHeader = table.getTableHeader();
			tableHeader.setUpdateTableInRealTime(true);
			tableHeader.setReorderingAllowed(true);
			tableHeader.addMouseListener( new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println(e);
					super.mouseClicked(e);
				}
				
			});

		}
	}

}
