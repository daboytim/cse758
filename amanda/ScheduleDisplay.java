import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;

public class ScheduleDisplay {

	private JFrame frame;
	private JTable table;
	public static List<Classes> testClasses;

	private int numSubjects = 3;
	private int numClasses = 12;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// initialize to test

		testClasses = new ArrayList<Classes>();
		for (int i = 0; i < 12; i++) {
			Classes cls = new Classes("math", 3);
			List<Students> stdTestList = new ArrayList<Students>();
			for (int j = 0; j < 6; j++) {
				Students testStd = new Students(45, "Fluffy", "Doggie", null,
						3, 4, 6, 1);
				stdTestList.add(testStd);
			}
			cls.students = stdTestList;
			testClasses.add(cls);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScheduleDisplay window = new ScheduleDisplay();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		frame.setBounds(100, 100, 1500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		table = new JTable((numSubjects * 7) + 1, numClasses + 2);
		table.setValueAt("Reading", 1, 0);
		table.setValueAt("LA", 8, 0);
		table.setValueAt("Math", 15, 0);

		for (int i = 1; i <= numClasses; ++i) {
			table.setValueAt("Room " + i, 0, i);
		}
		table.setValueAt("Unable to place", 0, (numClasses + 1));

		// get student names and place classes in table:

		// fill in students for Reading
		for (int i = 0; i < testClasses.size(); ++i) {
			Classes cls = testClasses.get(i);
			List<Students> students = cls.getStudents();
			for (int j = 0; j < students.size(); ++j) {
				Students std = students.get(j);
				String stdNameStr = std.getFirstName();
				stdNameStr += " " + std.getLastName();
				table.setValueAt(stdNameStr, j + 1, i + 1);
			}
		}

		// fill in students for LA
		for (int i = 0; i < testClasses.size(); ++i) {
			Classes cls = testClasses.get(i);
			List<Students> students = cls.getStudents();
			for (int j = 0; j < students.size(); ++j) {
				Students std = students.get(j);
				String stdNameStr = std.getFirstName();
				stdNameStr += " " + std.getLastName();
				table.setValueAt(stdNameStr, j + 8, i + 1);
			}
		}

		// fill in students for Math
		for (int i = 0; i < testClasses.size(); ++i) {
			Classes cls = testClasses.get(i);
			List<Students> students = cls.getStudents();
			for (int j = 0; j < students.size(); ++j) {
				Students std = students.get(j);
				String stdNameStr = std.getFirstName();
				stdNameStr += " " + std.getLastName();
				table.setValueAt(stdNameStr, j + 15, i + 1);
			}
		}

		frame.getContentPane().add(table, BorderLayout.NORTH);
	}
}
