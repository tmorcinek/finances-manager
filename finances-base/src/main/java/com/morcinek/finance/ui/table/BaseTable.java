package com.morcinek.finance.ui.table;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;

import com.morcinek.finance.PrioritisedSet;
import com.morcinek.finance.data.Payment;
import com.morcinek.finance.parse.FieldParser;
import com.morcinek.finance.parse.objects.ObjectParser;
import com.morcinek.finance.ui.components.PaymentDialog;
import com.morcinek.finance.ui.table.model.ListTableModel;
import com.morcinek.finance.ui.table.renderers.MatchRenderer;
import com.morcinek.finance.ui.table.renderers.RowRendererInterface;
import com.morcinek.finance.util.ApplicationContextProvider;

public class BaseTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2860282823670463361L;

	@Autowired(required = true)
	private FieldParser fieldParser;

	private Set<Class<?>> classes = new HashSet<Class<?>>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private SortedSet<RowRendererInterface> priorityQueue = new PrioritisedSet();

	public BaseTable() {
		super();
		getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	public void init() {
		for (ObjectParser objectParser : fieldParser.getObjectParsers()) {
			classes.add(objectParser.getValueClass());
		}
		setDefaultRenderer(String.class, new MatchRenderer("[A-Z][a-z]+ [A-Z][a-z]+"));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int convertRowIndexToModel = getRowSorter().convertRowIndexToModel(target.getSelectedRow());
					PaymentDialog paymentDialog = ApplicationContextProvider.getApplicationContext().getBean(
							PaymentDialog.class);
					paymentDialog.update((Payment) getListTableModel().getRowAt(convertRowIndexToModel));
				}
				super.mouseClicked(e);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public TableRowSorter<TableModel> getRowSorter() {
		return (TableRowSorter<TableModel>) super.getRowSorter();
	}

	public ListTableModel getListTableModel() {
		return (ListTableModel) super.getModel();
	}

	public void setDefaultRenderer(TableCellRenderer renderer) {
		for (Class<?> class1 : classes) {
			setDefaultRenderer(class1, renderer);
		}
	}

	public void setRowRenderer(RowRendererInterface rowRenderer) {
		priorityQueue.add(rowRenderer);
	}

	public void setRowRenderers(List<RowRendererInterface> rowRenderers) {
		priorityQueue.clear();
		priorityQueue.addAll(rowRenderers);
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component component = super.prepareRenderer(renderer, row, column);
		int convertRowIndexToModel = getRowSorter().convertRowIndexToModel(row);
		Payment payment = (Payment) ((ListTableModel) getModel()).getRowAt(convertRowIndexToModel);
		if (Arrays.binarySearch(getSelectedRows(), row) < 0) {
			for (RowRendererInterface rowRenderer : priorityQueue) {
				if (rowRenderer.applies(payment, row)) {
					if (rowRenderer.getBackgroundColor() != null) {
						component.setBackground(rowRenderer.getBackgroundColor());
					}
					if (rowRenderer.getForegroundColor() != null) {
						component.setBackground(rowRenderer.getForegroundColor());
					}
				}
			}
		}
		return component;
	}
}
