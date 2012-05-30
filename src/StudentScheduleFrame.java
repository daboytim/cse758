import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTable.PrintMode;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

public class StudentScheduleFrame extends JFrame implements ActionListener,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 644L;
	private static final int numRows = 8;
	private static final int numColumns = 2;
	private JPanel contentPane;
	private JButton btnPrint;
	private JButton btnClose;
	private Students std;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public StudentScheduleFrame(Students student) {
		std = student;
		Object[] columnNames = new Object[numColumns];
		for (int i = 0; i < numColumns; i++) {
			columnNames[i] = "";
		}
		Object[][] data = new Object[numRows][numColumns];
		initData(data);

		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(NORMAL);
		setMaximizedBounds(new Rectangle(100, 100, 400, 400));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle(std.getFirstName() + " " + std.getLastName() + "'s Schedule");

		table = new JTable(data, columnNames);
		table.setRowSelectionAllowed(false);
		TableColumnModel cm = table.getColumnModel();
		cm.getColumn(0).setPreferredWidth(100);
		cm.getColumn(1).setPreferredWidth(100);
		contentPane.add(table, BorderLayout.CENTER);

		JPanel btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.SOUTH);

		btnPrint = new JButton("Print");
		btnPrint.addActionListener(this);
		btnPanel.add(btnPrint);

		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		btnPanel.add(btnClose);

		setPreferredSize(new Dimension(400, 400));
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnPrint) {
			print();
		} else if (e.getSource() == btnClose) {
			this.dispose();
		}
	}

	private void print() {
		// print the schedule
		try {
			// JTable p = sched.getScheduleTable();
			// int h = p.getRowHeight();
			// Font f = p.getFont();
			// JTableHeader hh = p.getTableHeader();
			// p.setTableHeader(null);
			// p.setRowHeight(9);
			// p.setFont(new Font("Arial", Font.PLAIN, 8));
			boolean complete = table.print(PrintMode.NORMAL);
			// p.setTableHeader(hh);
			// p.setFont(f);
			// p.setRowHeight(h);
			if (complete) {
				/* show a success message */

			} else {
				/* show a message indicating that printing was cancelled */
				JOptionPane.showMessageDialog(this, "Print Job was Cancelled");
			}
		} catch (PrinterException pe) {
			/* Printing failed, report to the user */
			JOptionPane.showMessageDialog(this, "Print Job Failed");

		}
	}

	private void initData(Object[][] data) {
		data[0][0] = "ID";
		data[0][1] = std.getId();
		data[1][0] = "Name";
		data[1][1] = std.getFirstName() + " " + std.getLastName();
		data[2][0] = "";
		data[2][1] = "";
		data[3][0] = "Homeroom";
		if (std.getHomeroomCls() == null)
			data[3][1] = "No Homeroom Class";
		else if (std.getHomeroomCls().getTeacher() == null)
			data[3][1] = "No Room Assigned";
		else
			data[3][1] = "Room " + std.getHomeroomCls().getTeacher().getRoom();

		data[4][0] = "Reading";
		if (std.getReadCls() == null)
			data[4][1] = "No Reading Class";
		else if (std.getReadCls().getTeacher() == null)
			data[4][1] = "Level " + std.getReadCls().getLvl()
					+ ": No Room Assigned";
		else
			data[4][1] = "Level " + std.getReadCls().getLvl() + ": Room"
					+ std.getReadCls().getTeacher().getRoom();

		data[5][0] = "Lang Arts";
		if (std.getLACls() == null)
			data[5][1] = "No Lang Arts Class";
		else if (std.getLACls().getTeacher() == null)
			data[5][1] = "Level " + std.getLACls().getLvl()
					+ ": No Room Assigned";
		else
			data[5][1] = "Level " + std.getLACls().getLvl() + ": Room"
					+ std.getLACls().getTeacher().getRoom();

		data[6][0] = "Math";
		if (std.getMathCls() == null)
			data[6][1] = "No Math Class";
		else if (std.getMathCls().getTeacher() == null)
			data[6][1] = "Level " + std.getMathCls().getLvl()
					+ ": No Room Assigned";
		else
			data[6][1] = "Level " + std.getMathCls().getLvl() + ": Room"
					+ std.getMathCls().getTeacher().getRoom();

		data[7][0] = "Specials";
		if (std.getSpecialCls() == null)
			data[7][1] = "No Specials Class";
		else if (std.getSpecialCls().getTeacher() == null)
			data[7][1] = "No Room Assigned";
		else
			data[7][1] = "Room " + std.getSpecialCls().getTeacher().getRoom();
	}

}
