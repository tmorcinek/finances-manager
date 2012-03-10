package com.morcinek.finance.util;

import java.util.Comparator;
import java.util.TreeSet;

public class PrioritisedSet<E extends Prioritised> extends TreeSet<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9034407468546975245L;

	public PrioritisedSet() {
		super(new Comparator<E>() {
			@Override
			public int compare(E o1, E o2) {
				return new Integer(o2.getPriority()).compareTo(o1.getPriority());
			}
		});
	}

}
