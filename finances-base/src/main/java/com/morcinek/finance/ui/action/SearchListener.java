package com.morcinek.finance.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morcinek.finance.data.PaymentAdapter;


@Component
public class SearchListener extends ListTableActionListener implements CaretListener {

	@Autowired(required=true)
	private PaymentAdapter paymentAdapter;
	
	@Override
	public void caretUpdate(CaretEvent e) {
		System.out.println("SearchListener.caretUpdate()");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("SearchListener.actionPerformed()");
	}

}
