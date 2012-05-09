import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class Menu extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuBar bar;
	JToolBar tb;
	JFrame frame;
	JMenu student, teacher, edit, exit, pref;
	static JMenuItem sOpen, sSave, tOpen, tSave, copy,paste,exit1,pref1;
	static JButton scheduleb, assignb;
	JTextArea textArea;
	static JFileChooser chooser;
	FileOutputStream fos;
	BufferedWriter bwriter;
	StudentDB students;
	TeacherDB teachers;

	public Menu(StudentDB s, JFrame f, StudentController sc, TeacherDB t, TeacherController tc) {
		frame = f;
		students = s;
		teachers = t;
		bar = new JMenuBar();
		bar.setFont(new Font("Arial", Font.BOLD, 14));
		student = new JMenu("Student");
		sOpen = new JMenuItem(" Open... ");
		sOpen.addActionListener(sc);
		sSave = new JMenuItem(" Save... ");
		sSave.addActionListener(sc);
		student.add(sOpen);
		student.add(sSave);
		
		teacher = new JMenu("Teacher");
		tOpen = new JMenuItem(" Open... ");
		tOpen.addActionListener(tc);
		tSave = new JMenuItem(" Save... ");
		tSave.addActionListener(tc);
		teacher.add(tOpen);
		teacher.add(tSave);
		
		bar.add(teacher);
		edit = new JMenu(" Edit ");
		copy = new JMenuItem(" Copy ");
		paste = new JMenuItem(" Paste ");
		edit.add(copy);
		edit.add(paste);
		bar.add(edit);
		exit = new JMenu(" Exit ");
		exit1 = new JMenuItem("Exit Application");
		
		//TODO: create a main controller
		//exit1.addActionListener(controller);

		// *****add by Kai****

		pref = new JMenu(" Preference ");
		pref1 = new JMenuItem("Set Max number of classes");
		//TODO: create a main controller
		//pref1.addActionListener(controller);
		pref.add(pref1);
		bar.add(pref);
		// end of Kai's edit

		exit.add(exit1);
		bar.add(exit);

		// Create Toolbar
		tb = new JToolBar();
		
		scheduleb = new JButton();
		scheduleb.setText("<html>Generate<br>Schedule</html>");
		scheduleb.addActionListener(sc);
		
		assignb = new JButton();
		scheduleb.setText("<html>Assign<br>Teachers</html>");
		scheduleb.addActionListener(tc);
		
		JPanel flowNorth = new JPanel(); // defaults to centered FlowLayout
		flowNorth.add(scheduleb);

		
		tb.add(flowNorth);
		tb.setAlignmentX(0);

	}

	
	public JMenuBar getMenu() {
		return bar;
	}

	public JToolBar getToolBar() {
		return tb;
	}

	
}
