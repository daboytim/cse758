import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.Serializable;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RowColoredTable extends JTable implements Serializable {
	private Object data;
	private int numCols;
	private ClassFactory cf;
	private int addStdBuf;
	private int rowMod;

	public RowColoredTable(Object[][] data, ClassFactory cf, int addStdBuf) {
		if (data != null) {
			this.data = data;
			this.numCols = data[0].length;
			this.cf = cf;
			this.addStdBuf = addStdBuf; // allows room for extra students to be
										// added later
		}
	}

	// num cols should be two more than maxClasses (one for beginning of table,
	// one for waitlist)
	public void updateNumCols(int numCols) {
		this.numCols = numCols;
		System.out
				.println("after updateNumCols in row colored table, numCols: "
						+ numCols);
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

		this.rowMod = cf.getMaxStdPerCls() + addStdBuf + 2 + 1; // see comments
																// at the bottom
		if (rowIndex != 0
				&& rowIndex % rowMod == (cf.getMaxStdPerCls() + addStdBuf + 2)) {
			c.setBackground(Color.GRAY);
		} else if ((rowIndex % rowMod == 0 || rowIndex % rowMod == 1)) {
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

// rowMod- the rows repeat with one dark gray between class types, two
// medium gray for heading and ages, then alternate very light gray.
// so, in addition to the max students per class, plus the additional
// buffer to allow extra students, plus two for medium gray and one
// for dark gray. For example, if max students per class is 5, and
// additional student buffer is 2, then the pattern would repeat every
// 5 + 2 + 2 + 1 = 10 rows. Therefore, mod 10 to make the pattern repeat.
// No variable for alternating the lightest gray- mod 2 - and this is the
// final case, if no other rows were chosen.
