package com.morcinek.finance.ui.table.renderers;

import java.awt.Color;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morcinek.finance.database.DBHelper;
import com.morcinek.finance.database.Payment;
import com.morcinek.finance.util.PropertiesAdapter;

@Component
public class ValidationRowRenderer extends AbstractRowRenderer {

	@Autowired
	private DBHelper dbHelper;

	@Autowired
	private PropertiesAdapter propertiesAdapter;

	private Color color;

	private List<BigInteger> paymentsTransactionNumbers;

	@PostConstruct
	public void init() {
		refresh();
	}

	@Override
	public boolean applies(Object row, int originalRowNumber) {
		if (row instanceof Payment) {
			Payment payment = (Payment) row;
			if (paymentsTransactionNumbers.contains(payment.getTransactionNumber())) {
				color = propertiesAdapter.getColor("rowRenderer_colorExists");
			} else {
				color = propertiesAdapter.getColor("rowRenderer_colorValid");
			}
		} else {
			color = propertiesAdapter.getColor("rowRenderer_colorInvalid");
		}
		return true;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public Color getBackgroundColor() {
		return color;
	}

	@Override
	public Color getForegroundColor() {
		return propertiesAdapter.getColor("rowRenderer_validationForeground");
	}

	@Override
	public void refresh() {
		try {
			paymentsTransactionNumbers = dbHelper.getPaymentsTransactionNumbers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
