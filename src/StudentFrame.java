import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;

public class StudentFrame {

	JFrame frame;
	StudentDB students;
	
	String[] validStates = { "", "K", "1", "2", "3", "4", "5", "6", "7", "8" };
	String[] behaviorLevels = { "1", "2", "3" };
	StudentController controller;

	public StudentFrame(JFrame f, StudentDB s) {
		ComboRenderer cr = new ComboRenderer();
		frame = f;
		students = s;
		controller = new StudentController(f, s);

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

		StudentMenu menu = new StudentMenu(students, frame, controller);
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

		// Make Table Scrollable
		JScrollPane pane = new JScrollPane(controller.table);
		pane.setSize(dimension);

		frame.add(pane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Display the window.
		frame.setVisible(true);
	}

}
