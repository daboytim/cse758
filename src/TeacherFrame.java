import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;

public class TeacherFrame {

	JFrame frame;
	TeacherDB teachers;

	public TeacherFrame(JFrame f, TeacherDB t) {
		frame = f;
		teachers = t;
		TeacherController controller = new TeacherController(f, t);
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

		TeacherMenu menu = new TeacherMenu(teachers, frame, controller);
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

	
