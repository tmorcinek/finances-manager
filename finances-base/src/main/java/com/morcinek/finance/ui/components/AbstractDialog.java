package com.morcinek.finance.ui.components;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;
import javax.swing.JDialog;

import com.morcinek.finance.database.Payment;

public abstract class AbstractDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5004670152182283616L;

	protected Toolkit toolkit = Toolkit.getDefaultToolkit();

	protected Object[] objects;

	protected Callable<?> callable;

	public AbstractDialog() {
		super();
	}

	public AbstractDialog(Window owner) {
		super(owner);
	}

	public void show(Callable<?> callable, Object... objects) {
		this.callable = callable;
		this.objects = objects;
	}

	@PostConstruct
	public void init() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("back".equals(e.getActionCommand())) {
			dispose();
		}
	}

}
