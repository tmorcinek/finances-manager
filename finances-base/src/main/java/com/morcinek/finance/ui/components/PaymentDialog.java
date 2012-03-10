package com.morcinek.finance.ui.components;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.morcinek.finance.database.Category;
import com.morcinek.finance.database.DBHelper;
import com.morcinek.finance.database.Payment;
import com.morcinek.finance.util.ApplicationContextProvider;
import com.morcinek.finance.util.PropertiesAdapter;
import com.morcinek.properties.Features;

@Component
@Scope(value = "prototype")
public class PaymentDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4855262784708308746L;

	@Autowired(required = true)
	private PropertiesAdapter propertiesAdapter;

	@Autowired(required = true)
	private DBHelper dbHelper;

	private Payment payment;

	public PaymentDialog(Window owner) {
		super(owner);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void update(Payment pPayment) {
		payment = pPayment;
		getContentPane().add(getTabbedPane());
		pack();
		setVisible(true);
		setResizable(Features.paymentDialog_resizable);
	}

	private JTabbedPane getTabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.putClientProperty("jgoodies.noContentBorder", Boolean.TRUE);

		tabbedPane.add(propertiesAdapter.getProperty("paymentsDialog_generalTab"), getPaymentFormPanel());
		tabbedPane.add(propertiesAdapter.getProperty("paymentsDialog_categoryTab"), getCategoriesPanel());
		tabbedPane.add(propertiesAdapter.getProperty("paymentsDialog_commentTab"), getBlankPanel());

		return tabbedPane;
	}

	protected JPanel getBlankPanel() {
		return new JPanel();
	}

	private JComponent getPaymentFormPanel() {
		FormLayout formLayout = new FormLayout(getColSpec(), getRowSpec(payment.size()));
		PanelBuilder builder = new PanelBuilder(formLayout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		List<String> headers = new ArrayList<String>(Arrays.asList(propertiesAdapter.getProperty("headers_list").split(
				",")));
		for (int index = 0; index < payment.size(); index++) {
			builder.addLabel(headers.get(index), cc.xy(1, index * 2 + 1));
			builder.add(getDisabledTextField(payment.get(index).toString()), cc.xy(3, index * 2 + 1));
		}
		return builder.getPanel();
	}

	private JPanel getCategoriesPanel() {
		List<JLabel> labels = new ArrayList<JLabel>();
		try {
			for (Category category : dbHelper.getPaymentCategory(payment)) {
				labels.add(new JLabel(category.toString()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JPanel panel = new JPanel();
		JButton button = new JButton("Show categories");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = (JDialog) ApplicationContextProvider.getApplicationContext().getBean(
						"categoriesDialog");
				dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dialog.pack();
				dialog.setVisible(true);
			}
		});
		panel.add(button);
		button = new JButton("Add Category");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfirmDialog dialog = (ConfirmDialog) ApplicationContextProvider.getApplicationContext().getBean(
						"confirmDialog");
				dialog.show(new Callable<String>() {
					@Override
					public String call() throws Exception {

						return null;
					}
				}, payment);
			}
		});
		panel.add(button);
		BoxLayoutPanel boxLayoutPanel = new BoxLayoutPanel();
		boxLayoutPanel.setAxis(1);
		boxLayoutPanel.setPanelComponents(labels);
		boxLayoutPanel.init();
		panel.add(boxLayoutPanel);
		return panel;
	}

	public static JTextField getDisabledTextField(String text) {
		JTextField textField = new JTextField(text);
		textField.setEditable(false);
		return textField;
	}

	private String getColSpec() {
		return "left:pref, 10dlu, pref";
	}

	private String getRowSpec(int rows) {
		String rowSpec = "p";
		for (int i = 0; i < rows; i++) {
			rowSpec = rowSpec.concat(", 3dlu, p");
		}
		return rowSpec;
	}

}
