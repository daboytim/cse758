import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
	JMenu student, teacher, schedule, edit, exit, pref;
	static JMenuItem sOpen, sSave, tOpen, tSave, schedulize, assign, copy,paste,exit1,pref1;
	JTextArea textArea;
	static JFileChooser chooser;
	FileOutputStream fos;
	BufferedWriter bwriter;
	StudentDB students;
	TeacherDB teachers;

	public Menu(MainFrame mf, StudentDB s, JFrame f, StudentController sc, TeacherDB t, TeacherController tc) {
		MenuController mc = new MenuController();
		
		frame = f;
		students = s;
		teachers = t;
		bar = new JMenuBar();
		bar.setFont(new Font("Arial", Font.BOLD, 14));
		student = new JMenu("Student");
		sOpen = new JMenuItem(" Open... ");
		sOpen.addActionListener(mf);
		sSave = new JMenuItem(" Save... ");
		sSave.addActionListener(sc);
		student.add(sOpen);
		student.add(sSave);
		bar.add(student);
		
		teacher = new JMenu("Teacher");
		tOpen = new JMenuItem(" Open... ");
		tOpen.addActionListener(mf);
		tSave = new JMenuItem(" Save... ");
		tSave.addActionListener(tc);
		teacher.add(tOpen);
		teacher.add(tSave);
		bar.add(teacher);

		
		schedule = new JMenu("Schedule");
		schedulize = new JMenuItem("Generate Schedule");
		schedulize.addActionListener(mf);
		assign = new JMenuItem("Assign Teachers");
		assign.addActionListener(mf);
		schedule.add(schedulize);
		schedule.add(assign);
		bar.add(schedule);
		
		edit = new JMenu(" Edit ");
		copy = new JMenuItem(" Copy ");
		paste = new JMenuItem(" Paste ");
		edit.add(copy);
		edit.add(paste);
		bar.add(edit);
		exit = new JMenu(" Exit ");
		exit1 = new JMenuItem("Exit Application");
		
		exit1.addActionListener(mc);

		// *****add by Kai****

		pref = new JMenu(" Preference ");
		pref1 = new JMenuItem("Set Max number of classes");
		//TODO: create a main controller
		pref1.addActionListener(mc);
		pref.add(pref1);
		bar.add(pref);
		// end of Kai's edit

		exit.add(exit1);
		bar.add(exit);

	}

	
	public JMenuBar getMenu() {
		return bar;
	}

	
	
}
