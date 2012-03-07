package com.morcinek.finance.ui.table.renderers;

import java.awt.Color;

import org.springframework.stereotype.Component;

import com.morcinek.finance.database.Payment;
import com.morcinek.finance.database.PaymentHelper;

@Component
public class IncomeRowRenderer extends AbstractRowRenderer {

	@Override
	public int getPriority() {
		return 10;
	}

	@Override
	public boolean applies(Object row, int originalRowNumber) {
		Payment payment = (Payment) row;
		return PaymentHelper.isIncome(payment);
	}

	@Override
	public Color getBackgroundColor() {
		return Color.green;
	}

}
