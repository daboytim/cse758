import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.Serializable;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RowColoredTable extends JTable implements Serializable{
	private Object data;
	private int numCols;

	public RowColoredTable(Object[][] data) {
		if (data != null) {
			this.data = data;
			numCols = data[0].length;
		}
	}

	public Component prepareRenderer(TableCellRenderer renderer, int rowIndex,
			int vColIndex) {
		Color color = new Color(234, 234, 234);
		Font bold = new Font("bold", Font.BOLD, 10);

		Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
		if (vColIndex == (numCols - 1)) {
			c.setBackground(getBackground());
			return c;
		}

		if (rowIndex != 0 && rowIndex % 10 == 9) {
			c.setBackground(Color.GRAY);
		} else if ((rowIndex % 10 == 0 || rowIndex % 10 == 1)) {
			c.setBackground(Color.GRAY.lightGray);
			c.setFont(bold);
		} else if (rowIndex % 2 == 0 && !isCellSelected(rowIndex, vColIndex)) {
			c.setBackground(color);
		} else {
			// If not shaded, match the table's background
			c.setBackground(getBackground());
		}

		return c;
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
