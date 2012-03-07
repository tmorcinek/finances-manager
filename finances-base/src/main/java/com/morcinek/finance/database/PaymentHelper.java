package com.morcinek.finance.database;


public class PaymentHelper {

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
	public static boolean findMatch(Payment payment, String match) {
		for (Object object : payment) {
			if (object.toString().contains(match)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if <code>amount</code> field is positive.
	 * 
	 * @param payment
	 * @return <b>true</b> if <code>amount</code> value is positive,
	 *         <b>false</b> otherwise.
	 */
	public static boolean isIncome(Payment payment) {
		return payment.getAmount() > 0;
	}
}
