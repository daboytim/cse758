import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class TeacherTable {

	JScrollPane pane;
	TeacherDB teachers;

	TeacherController controller;

	public TeacherTable(JFrame f, TeacherDB t) {
		teachers = t;
		controller = new TeacherController(f, t);
		controller.populateTable();

		controller.table.setShowGrid(true);
		controller.table.setGridColor(Color.BLACK);
		controller.table.setRowHeight(20);
		controller.table.setCellSelectionEnabled(true);

		// Make Table Scrollable
		pane = new JScrollPane(controller.table);
		
	}

	public TeacherController getTeacherController() {
		return controller;
	}
	
	public JScrollPane getTeacherTable() {
		return pane;
	}

}
