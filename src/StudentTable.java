import java.awt.Color;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;

public class StudentTable {

	JScrollPane pane;
	StudentDB students;

	String[] validStates = { "", "K", "1", "2", "3", "4", "5", "6", "7", "8" };
	String[] behaviorLevels = { "1", "2", "3" };
	StudentController controller;

	public StudentTable(JFrame f, StudentDB s) {

		students = s;
		controller = new StudentController(f, s);
		ComboRenderer cr = new ComboRenderer();
		controller.populateTable();

		controller.table.setShowGrid(true);
		controller.table.setGridColor(Color.BLACK);
		controller.table.setRowHeight(20);
		controller.table.setCellSelectionEnabled(true);

		// Create the combo box editor
		JComboBox comboBox = new JComboBox(validStates);
		JComboBox behaviorBox = new JComboBox(behaviorLevels);
		// comboBox.setEditable(true);
		DefaultCellEditor editor = new DefaultCellEditor(comboBox);
		DefaultCellEditor bEditor = new DefaultCellEditor(behaviorBox);

		// Create the textfield editor
		JTextField text = new JTextField();
		text.setEditable(true);
		DefaultCellEditor teditor = new DefaultCellEditor(text);

		// Render comboboxes properly
		TableColumnModel tcm = controller.table.getColumnModel();
		tcm.getColumn(0).setCellEditor(teditor);
		tcm.getColumn(1).setCellEditor(teditor);
		tcm.getColumn(2).setCellEditor(teditor);
		tcm.getColumn(3).setCellEditor(teditor);
		tcm.getColumn(4).setCellEditor(editor);
		tcm.getColumn(4).setCellRenderer(cr);
		tcm.getColumn(5).setCellEditor(editor);
		tcm.getColumn(5).setCellRenderer(cr);
		tcm.getColumn(6).setCellEditor(editor);
		tcm.getColumn(6).setCellRenderer(cr);
		tcm.getColumn(7).setCellEditor(bEditor);
		tcm.getColumn(7).setCellRenderer(cr);

		pane = new JScrollPane(controller.table);
	}
	
	public StudentController getStudentController() {
		return controller;
	}
	
	public JScrollPane getStudentTable() {
		return pane;
	}

}
