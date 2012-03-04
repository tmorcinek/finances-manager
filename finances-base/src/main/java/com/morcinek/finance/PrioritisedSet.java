package com.morcinek.finance;

import java.util.Comparator;
import java.util.TreeSet;

@SuppressWarnings("hiding")
public class PrioritisedSet<Object extends Prioritised> extends TreeSet<Prioritised> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9034407468546975245L;

	public PrioritisedSet() {
		super(new Comparator<Prioritised>() {
			@Override
			public int compare(Prioritised o1, Prioritised o2) {
				return new Integer(o2.getPriority()).compareTo(o1.getPriority());
			}
		});
	}

}
