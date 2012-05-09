import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

public class MainFrame {

	public MainFrame() {
		JFrame frame = new JFrame();
		frame.setState(Frame.NORMAL);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		frame.setSize(dimension);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		StudentDB students = new StudentDB();
		TeacherDB teachers = new TeacherDB();

		JTabbedPane tabbedPane = new JTabbedPane();

		StudentTable sTab = new StudentTable(frame, students);
		JScrollPane panel1 = sTab.getStudentTable();
		tabbedPane.addTab("Student Entry", panel1);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		TeacherTable tTab = new TeacherTable(frame, teachers);
		JScrollPane panel2 = tTab.getTeacherTable();
		tabbedPane.addTab("Teacher Entry", panel2);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		StudentController sc = sTab.getStudentController();
		TeacherController tc = tTab.getTeacherController();
		
		Menu menu = new Menu(students, frame, sc,  teachers,  tc);
		frame.setJMenuBar(menu.getMenu());
		
		JToolBar tb = menu.getToolBar();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(tb);
		frame.add(panel);
		/*JComponent panel3 = makeTextPanel("Panel #3");
		tabbedPane.addTab("Tab 3", icon, panel3, "Still does nothing");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);*/

		

		// Add the tabbed pane to this panel.
		frame.add(tabbedPane);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		frame.setVisible(true);
	}

}
