package com.morcinek.finance.ui.components;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.morcinek.properties.Features;

public abstract class AbstractDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5004670152182283616L;

	protected Toolkit toolkit = Toolkit.getDefaultToolkit();

	protected Object[] objects;

	protected Callable<?> callable;

	protected BorderPanel borderPanel;

	public AbstractDialog() {
		super();
	}

	public AbstractDialog(Window owner) {
		super(owner);
		setLocationRelativeTo(owner);
	}

	public void showDialog(Callable<?> callable, Object... objects) {
		this.callable = callable;
		this.objects = objects;
		setVisible(true);
	}

	@PostConstruct
	public void init() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		borderPanel = (BorderPanel) getContentPane();
		JPanel southPanel = (JPanel) borderPanel.getSouthComponent();
		for (Component component : southPanel.getComponents()) {
			((JButton) component).addActionListener(this);
		}
		pack();
		setResizable(Features.paymentDialog_resizable);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("back".equals(e.getActionCommand())) {
			dispose();
		}
	}

}
