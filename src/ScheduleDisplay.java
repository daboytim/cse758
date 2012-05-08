import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.table.TableColumnModel;

public class ScheduleDisplay implements ActionListener {

	private JFrame frame;
	private JTable table;
	private StudentDB students;
	int maxStudentsPerClass;
	String[][] data;
	JButton button = new JButton("Schedule Teachers");

	// numClasses = #subjects; numRooms = #divisions
	private int numClasses = ClassFactory.getTotalClasses();
	private int numRooms = ClassFactory.getMostClasses();

	/**
	 * Launch the application.
	 */

	/*public void display(JFrame f) {

		// initialize to test
		new ScheduleDisplay();
		//window.frame.setVisible(true);

	}*/

	/**
	 * Create the application.
	 */
	public ScheduleDisplay() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {

		frame = new JFrame("Master Schedule");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		frame.setSize(dimension);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// TODO: constants for subjects and max students
		table = new JTable((3 * 7) + 1, numRooms + 2);
		table.setValueAt("Reading", 1, 0);
		table.setValueAt("LA", 8, 0);
		table.setValueAt("Math", 15, 0);

		// TODO: add rows for specials and homeroom (same as math)
		// TODO: add formatting- times of classes

		// label room numbers
		for (int i = 0; i < numRooms; ++i) {
			table.setValueAt("Room " + (i + 1), 0, i + 1);
		}
		table.setValueAt("Unable to place", 0, numRooms + 1);

		// format table
		table.setShowGrid(true);
		table.setGridColor(Color.BLACK);
		table.setRowHeight(20);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		table.setBorder(border);

		// get student names and place classes in table:

		// fill in students for Reading
		for (int i = 0; i < ClassFactory.readClsLst.size(); ++i) {
			Classes cls = ClassFactory.readClsLst.get(i);
			List<Students> students = cls.getStudents();
			for (int j = 0; j < students.size(); ++j) {
				Students std = students.get(j);
				String stdNameStr = std.getFirstName();
				stdNameStr += " " + std.getLastName();
				table.setValueAt(stdNameStr, j + 1, i + 1);
			}
		}

		// fill in students for LA
		for (int i = 0; i < ClassFactory.getTotalLA(); ++i) {
			Classes cls = ClassFactory.mathClsLst.get(i);
			List<Students> students = cls.getStudents();
			for (int j = 0; j < students.size(); ++j) {
				Students std = students.get(j);
				String stdNameStr = std.getFirstName();
				stdNameStr += " " + std.getLastName();
				table.setValueAt(stdNameStr, j + 8, i + 1);
			}
		}

		// fill in students for Math
		for (int i = 0; i < ClassFactory.getTotalMath(); ++i) {
			Classes cls = ClassFactory.mathClsLst.get(i);
			List<Students> students = cls.getStudents();
			for (int j = 0; j < students.size(); ++j) {
				Students std = students.get(j);
				String stdNameStr = std.getFirstName();
				stdNameStr += " " + std.getLastName();
				table.setValueAt(stdNameStr, j + 15, i + 1);
			}
		}

		BorderLayout blayout;
		blayout = new BorderLayout(20, 20);
		GridLayout glayout;
		glayout = new GridLayout(2, 1, 10, 10);

		// add menu bar to frame
		ScheduleMenu scheduleMenu = new ScheduleMenu(students, frame);
		JMenuBar menuBar = scheduleMenu.getMenu();
		frame.setJMenuBar(menuBar);
		menuBar.setPreferredSize(new Dimension(200, 20));

		// add toolbar to panel
		JToolBar tb = scheduleMenu.getToolBar();
		JPanel panel = new JPanel();
		panel.add(tb);
		frame.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.setAlignmentX(0);
		panel.setVisible(true);

		JPanel bottomPanel = new JPanel();
		//bottomPanel.setVisible(true);

		// Make table scrollable
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel cm = table.getColumnModel();
		cm.getColumn(0).setMinWidth(75);
		for (int i = 1; i < cm.getColumnCount(); i++) {
			cm.getColumn(i).setMinWidth(150);
		}
		JScrollPane sp = new JScrollPane();
		sp.setPreferredSize(dimension);
		sp.getViewport().add(table);

		// add top panel with toolbar, table, bottom panel (blank) to frame
		frame.getContentPane().setLayout(blayout);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		frame.getContentPane().add(sp, BorderLayout.CENTER);
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
