package com.morcinek.finance.ui.table.renderers;

import java.awt.Color;

import org.springframework.stereotype.Component;

import com.morcinek.finance.data.Payment;
import com.morcinek.finance.data.PaymentHelper;

@Component
public class IncomeRowRenderer extends AbstractRowRenderer {

	@Override
	public int getPriority() {
		return 10;
	}

	@Override
	public boolean applies(Object row, int rowNumber) {
		Payment payment = (Payment) row;
		return PaymentHelper.isIncome(payment);
	}

	@Override
	public Color getBackgroundColor() {
		return Color.green;
	}

}
