package com.morcinek.finance.ui.components;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.morcinek.finance.database.Payment;
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

		tabbedPane.add("General", getPaymentFormPanel(payment));
		tabbedPane.add("Tags", getBlankPanel());
		tabbedPane.add("Special", getBlankPanel());

		return tabbedPane;
	}

	protected JPanel getBlankPanel() {
		return new JPanel();
	}

	private JComponent getPaymentFormPanel(Payment payment) {
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
