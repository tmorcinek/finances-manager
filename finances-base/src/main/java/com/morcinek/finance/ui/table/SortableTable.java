package com.morcinek.finance.ui.table;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.morcinek.finance.ui.table.model.SortableTableModel;

public class SortableTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2860282823670463361L;
	private ButtonHeaderRenderer renderer;

	public SortableTable() {
		super();
		renderer = new ButtonHeaderRenderer();
		getTableHeader().setUpdateTableInRealTime(true);
		getTableHeader().addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int col = getTableHeader().columnAtPoint(e.getPoint());
				renderer.setPressedColumn(col);
				getTableHeader().repaint();

				boolean isAscent = true;
				// if (SortButtonRenderer.DOWN == renderer.getState(col)) {
				// isAscent = true;
				// } else {
				// isAscent = false;
				// }

				((SortableTableModel) getModel()).sortByColumn(col, isAscent);
				// ((AbstractTableModel) getModel()).fireTableDataChanged();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				renderer.setPressedColumn(-1);
				getTableHeader().repaint();
			}
		});
	}

	@Override
	public void columnAdded(TableColumnModelEvent e) {
		super.columnAdded(e);
		TableColumn tableColumn = getColumnModel().getColumn(e.getToIndex());
		tableColumn.setHeaderRenderer(renderer);
	}

	private class ButtonHeaderRenderer extends JButton implements TableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6254938252318046001L;

		int pushedColumn;

		public ButtonHeaderRenderer() {
			pushedColumn = -1;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			setText((value == null) ? "" : value.toString());

			boolean isPressed = (column == pushedColumn);
			getModel().setPressed(isPressed);
			getModel().setArmed(isPressed);
			return this;
		}

		public void setPressedColumn(int col) {
			pushedColumn = col;
		}
	}

}
