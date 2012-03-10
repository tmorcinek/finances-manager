package com.morcinek.finance;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher {

	public void launch() {
		String[] contextPath = new String[] {"context.xml", "dialogs.xml"};
		new ClassPathXmlApplicationContext(contextPath);
	}	
}
