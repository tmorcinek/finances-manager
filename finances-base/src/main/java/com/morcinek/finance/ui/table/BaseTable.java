package com.morcinek.finance.ui.table;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;

import com.morcinek.finance.parse.FieldParser;
import com.morcinek.finance.parse.objects.ObjectParser;
import com.morcinek.finance.ui.table.model.ListTableModel;
import com.morcinek.finance.ui.table.renderers.RowRendererInterface;
import com.morcinek.finance.util.PrioritisedSet;

@org.springframework.stereotype.Component
public class BaseTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2860282823670463361L;

	@Autowired(required = true)
	private FieldParser fieldParser;

	private Set<Class<?>> classes = new HashSet<Class<?>>();

	private SortedSet<RowRendererInterface> priorityQueue = new PrioritisedSet<RowRendererInterface>();

	private int highlightedRow = -1;

	@PostConstruct
	public void init() {
		getSelectionModel().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		for (ObjectParser objectParser : fieldParser.getObjectParsers()) {
			classes.add(objectParser.getValueClass());
		}
		// setDefaultRenderer(String.class, new
		// MatchRenderer("[A-Z][a-z]+ [A-Z][a-z]+"));
		//
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

	public void setMouseListener(MouseAdapter mouseListener) {
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		JComponent component = (JComponent) super.prepareRenderer(renderer,
				row, column);
		int convertRowIndexToModel = row;
		if (getRowSorter() != null) {
			convertRowIndexToModel = getRowSorter().convertRowIndexToModel(row);
		}
		List<?> payment = ((ListTableModel) getModel())
				.getRowAt(convertRowIndexToModel);
		if (row == highlightedRow) {
			component.setBorder(createRowBorder(row, column));
		}
		if (Arrays.binarySearch(getSelectedRows(), row) < 0) {
			for (RowRendererInterface rowRenderer : priorityQueue) {
				if (rowRenderer.applies(payment, row)) {
					if (rowRenderer.getBackgroundColor() != null) {
						component.setBackground(rowRenderer
								.getBackgroundColor());
					}
					if (rowRenderer.getForegroundColor() != null) {
						component.setForeground(rowRenderer
								.getForegroundColor());
					}
				}
			}
		}
		return component;
	}

	private MatteBorder createRowBorder(int row, int column) {
		int top = (row > 0 && isRowSelected(row - 1)) ? 1 : 2;
		int left = column == 0 ? 2 : 0;
		int bottom = (row < getRowCount() - 1 && isRowSelected(row + 1)) ? 1
				: 2;
		int right = column == getColumnCount() - 1 ? 2 : 0;
		MatteBorder createMatteBorder = BorderFactory.createMatteBorder(top,
				left, bottom, right, selectionBackground);
		return createMatteBorder;
	}

	public void refreshRenderers() {
		for (RowRendererInterface rowRenderer : priorityQueue) {
			rowRenderer.refresh();
		}
	}

	public void setHighlightedRow(int row) {
		if (row != -1) {
			getListTableModel().fireTableRowsUpdated(row, row);
		}
		if (highlightedRow != -1) {
			getListTableModel().fireTableRowsUpdated(highlightedRow,
					highlightedRow);
		}
		highlightedRow = row;
	}

}
