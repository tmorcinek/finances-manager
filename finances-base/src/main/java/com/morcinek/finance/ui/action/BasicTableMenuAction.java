package com.morcinek.finance.ui.action;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.morcinek.finance.database.Payment;
import com.morcinek.finance.ui.ComponentsFactory;
import com.morcinek.finance.ui.CopyInterface;
import com.morcinek.finance.ui.components.AddCategoryDialog;
import com.morcinek.finance.ui.components.PaymentDialog;
import com.morcinek.finance.ui.components.bookmark.Bookmarks;
import com.morcinek.finance.ui.table.BaseTable;
import com.morcinek.finance.util.ApplicationContextProvider;

@Component
@Scope(value = "prototype")
public class BasicTableMenuAction implements MenuItemActionListener, ClipboardOwner {

	private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	@Override
	public void actionPerformed(ActionEvent e, Object pObject) {
		String actionCommand = e.getActionCommand();
		Collection<Object> objects = (Collection<Object>) pObject;
		if ("open".equals(actionCommand)) {
			for (Object object : objects) {
				if (object instanceof Payment) {
					PaymentDialog paymentDialog = (PaymentDialog) ApplicationContextProvider.getApplicationContext()
							.getBean("paymentDialog");
					paymentDialog.update((Payment) object);
				}
			}
		} else if ("copy".equals(actionCommand)) {
			StringBuilder stringBuilder = new StringBuilder();
			for (Object object : objects) {
				String copyString;
				try {
					copyString = ((CopyInterface) object).toCopyString();
				} catch (ClassCastException e1) {
					copyString = object.toString();
				}
				stringBuilder.append(copyString + "\n");
			}
			StringSelection contents = new StringSelection(stringBuilder.toString());
			clipboard.setContents(contents, this);
		} else if ("add_categories".equals(actionCommand)) {
			Payment payment = (Payment) objects.toArray()[0];
			AddCategoryDialog dialog = (AddCategoryDialog) ApplicationContextProvider.getApplicationContext().getBean(
					"addCategoryDialog");
			dialog.showDialog(null, payment);

		} else if ("open_bookmark".equals(actionCommand)) {
			Bookmarks bookmarks = (Bookmarks) ApplicationContextProvider.getApplicationContext().getBean(
					Bookmarks.class);
			Payment[] payments = objects.toArray(new Payment[0]);
			BaseTable baseTable = ComponentsFactory.createBaseTable(new ArrayList<List<?>>(Arrays.asList(payments)),
					"mainTable");
			bookmarks.addBaseTable("*", baseTable);
		}
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}

}
