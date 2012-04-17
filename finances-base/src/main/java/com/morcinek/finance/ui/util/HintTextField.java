package com.morcinek.finance.ui.util;

import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.annotation.PostConstruct;
import javax.swing.JTextField;

import org.springframework.stereotype.Component;

@Component
public class HintTextField extends JTextField implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649660236069221960L;

	private String hint;

	public HintTextField() {
		super();
	}

	public HintTextField(int arg0) {
		super(arg0);
	}

	@PostConstruct
	public void init() {
		super.addFocusListener(this);
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	@Override
	public void focusGained(FocusEvent e) {
		repaint();
	}

	@Override
	public void focusLost(FocusEvent e) {
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (!hasFocus() && getText().isEmpty()) {
			int padding = (getHeight() - getFont().getSize()) / 2;
			g.drawString(hint, 2, getHeight() - padding - 1);
		}
	}
}