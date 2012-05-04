import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TeacherFrame implements TableModelListener {

	JTable table;
	JFrame frame;
	TeacherDB teachers;
	static Object[][] data;
	Object[][] backup;
	String[] columnNames = { "Name", "Math Class Levels",
			"Reading Class Levels", "Language Arts Class Levels" };

	public TeacherFrame(JFrame f, TeacherDB t) {
		frame = f;
		teachers = t;
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception evt) {
		}

		// Create and set up the window.
		frame = new JFrame("Scheduling");
		frame.setState(Frame.NORMAL);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		frame.setSize(dimension);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		TeacherMenu menu = new TeacherMenu(teachers, frame);
		JMenuBar menuBar = menu.getMenu();
		frame.setJMenuBar(menuBar);
		menuBar.setPreferredSize(new Dimension(200, 20));
		// Set the menu bar and add the label to the content pane.
		frame.setJMenuBar(menuBar);

		JToolBar tb = menu.getToolBar();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(tb);
		frame.add(panel, BorderLayout.NORTH);

		populateTable();

		table = new JTable(data, columnNames);
		table.getModel().addTableModelListener(this);
		table.setShowGrid(true);
		table.setGridColor(Color.BLACK);
		table.setRowHeight(20);
		table.setCellSelectionEnabled(true);

		// Make Table Scrollable
		JScrollPane pane = new JScrollPane(table);
		pane.setSize(dimension);

		frame.add(pane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Display the window.
		frame.setVisible(true);
	}

	// Make the object [][] representation of students to be added into the
	// JTable
	private void populateTable() {
		int i = 0;
		data = new Object[300][4];
		if (teachers.getSize() > 0) {
			ArrayList<Teachers> tList = teachers.getTeachers();
			Iterator<Teachers> it = tList.iterator();
			while (it.hasNext()) {
				Teachers t = it.next();
				data[i][0] = t.getName();
				StringBuilder line = new StringBuilder();
				ArrayList<Integer> mClasses = t
						.getPreference(Teachers.Type.MATH);
				for (int j = 0; j < mClasses.size(); j++) {
					line.append(mClasses.get(j) + ";");
				}
				data[i][1] = line.toString();
				line = new StringBuilder();
				ArrayList<Integer> rClasses = t
						.getPreference(Teachers.Type.READ);
				for (int j = 0; j < rClasses.size(); j++) {
					line.append(rClasses.get(j) + ";");
				}
				data[i][2] = line.toString();
				line = new StringBuilder();
				ArrayList<Integer> lClasses = t.getPreference(Teachers.Type.LA);
				for (int j = 0; j < lClasses.size(); j++) {
					line.append(lClasses.get(j) + ";");
				}
				data[i][3] = line.toString();
				i++;

			}
		}

		// Add an empty row

		while (i < 300) {
			data[i][0] = "";
			data[i][1] = "";
			i++;
		}

		backup = data;

	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		Object d = model.getValueAt(row, column);
		boolean isBlank = Utilities.isBlank(d.toString());

		if (row > 0 && Utilities.isBlank(data[row - 1][0].toString())
				&& !isBlank) {
			JOptionPane.showMessageDialog(frame,
					"Please do not leave open rows within the table");
			table.setValueAt("", row, column);
			return;
		}

		String currName = data[row][0].toString();

		Teachers t;
		boolean newTeacher = false;
		if (teachers.hasTeacher(currName)) {
			t = teachers.getTeacher(currName);
		} else {
			t = new Teachers(currName);
			newTeacher = true;
		}

		switch (column) {
		case 0:
			if (isBlank) {
				cleanTeacherDB();
			} else {
				if (teachers.hasTeacher(d.toString())) {
					JOptionPane
							.showMessageDialog(frame,
									"A teacher with that name already exists in the scheduling system.");
					table.setValueAt("", row, column);
				} else {
					t.setName(d.toString());
				}
			}
			break;
		case 1:
			if (data[row][0].toString().isEmpty()) {
				if (!isBlank) {
					JOptionPane.showMessageDialog(frame,
							"Please provide a name first.\n");
					table.setValueAt("", row, column);
				}
			} else {
				String[] classList = d.toString().split(";");
				ArrayList<Integer> classes = new ArrayList<Integer>();
				for (int i = 0; i < classList.length; i++) {
					try {
						int tmp = Integer.parseInt(classList[i]);
						classes.add(tmp);

					} catch (NumberFormatException ne) {
						// maybe an error here, but they probably wont be
						// inputting integers so we'll need to do conversions
					}
				}
				t.setPreference(classes, Teachers.Type.MATH);
			}
			break;
		case 2:
			if (data[row][0].toString().isEmpty()) {
				if (!isBlank) {
					JOptionPane.showMessageDialog(frame,
							"Please provide a name first.\n");
					table.setValueAt("", row, column);
				}
			} else {
				String[] classList = d.toString().split(";");
				ArrayList<Integer> classes = new ArrayList<Integer>();
				for (int i = 0; i < classList.length; i++) {
					try {
						int tmp = Integer.parseInt(classList[i]);
						classes.add(tmp);

					} catch (NumberFormatException ne) {
						// maybe an error here, but they probably wont be
						// inputting integers so we'll need to do conversions
					}
				}
				t.setPreference(classes, Teachers.Type.READ);
			}
			break;
		case 3:
			if (data[row][0].toString().isEmpty()) {
				if (!isBlank) {
					JOptionPane.showMessageDialog(frame,
							"Please provide a name first.\n");
					table.setValueAt("", row, column);
				}
			} else {
				String[] classList = d.toString().split(";");
				ArrayList<Integer> classes = new ArrayList<Integer>();
				for (int i = 0; i < classList.length; i++) {
					try {
						int tmp = Integer.parseInt(classList[i]);
						classes.add(tmp);

					} catch (NumberFormatException ne) {
						// maybe an error here, but they probably wont be
						// inputting integers so we'll need to do conversions
					}
				}
				t.setPreference(classes, Teachers.Type.LA);
			}
			break;
		}

		if (!isBlank) {
			if (newTeacher) {
				teachers.addTeacher(t);
			} else {
				teachers.modifyTeacher(currName, t);
			}
		}
	}

	/*
	 * I apologize for the inefficiency of this, but given the small data set,
	 * it shouldn't be awful
	 */
	private void cleanTeacherDB() {
		List<Teachers> tchrs = teachers.getTeachers();
		for (int i = 0; i < tchrs.size(); i++) {
			Teachers t = tchrs.get(i);
			String name = t.getName();
			for (int j = 0; j < teachers.getSize(); j++) {
				if (name.equals(data[j][0].toString())) {
					return;
				}
			}
			// if we got here, the student is not in the data array anymore
			teachers.removeTeacher(name);
		}
	}

}
