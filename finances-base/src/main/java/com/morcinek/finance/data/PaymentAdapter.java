package com.morcinek.finance.data;

import org.springframework.stereotype.Component;

@Component
public class PaymentAdapter {

	/**
	 * Checks if any field of <code>Payment</code> object contains given
	 * <code>match</code> text.
	 * 
	 * @param payment
	 *            (Payment) object in which we look for a match to given
	 *            <code>match</code>.
	 * @param match
	 *            (String) text which we want to find in given
	 *            <code>Payment</code> object.
	 * @return <b>true</b> if match is found in Payment object in any of its
	 *         field, <b>false</b> if there is no match found in
	 *         <code>Payment</code> object.
	 */
	public boolean findMatch(Payment payment, String match) {
		for (Object object : payment) {
			if (object.toString().contains(match)) {
				return true;
			}
		}
		return false;
	}

}
