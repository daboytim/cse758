import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class StudentMenu extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuBar bar;
	JToolBar tb;
	JFrame frame;
	JMenu file, edit, exit, pref;
	static JMenuItem open, save, copy,paste,exit1,pref1;
	static JButton openb, saveb, exitb, scheduleb;
	JTextArea textArea;
	static JFileChooser chooser;
	FileOutputStream fos;
	BufferedWriter bwriter;
	StudentDB students;

	public StudentMenu(StudentDB s, JFrame f, StudentController controller) {
		frame = f;
		students = s;
		bar = new JMenuBar();
		bar.setFont(new Font("Arial", Font.BOLD, 14));
		file = new JMenu(" File ");
		open = new JMenuItem(" Open... ");
		open.addActionListener(controller);
		save = new JMenuItem(" Save... ");
		save.addActionListener(controller);
		file.add(open);
		file.add(save);
		bar.add(file);
		edit = new JMenu(" Edit ");
		copy = new JMenuItem(" Copy ");
		paste = new JMenuItem(" Paste ");
		edit.add(copy);
		edit.add(paste);
		bar.add(edit);
		exit = new JMenu(" Exit ");
		exit1 = new JMenuItem("Exit Application");
		exit1.addActionListener(controller);

		// *****add by Kai****

		pref = new JMenu(" Preference ");
		pref1 = new JMenuItem("Set Max number of classes");
		pref1.addActionListener(controller);
		pref.add(pref1);
		bar.add(pref);
		// end of Kai's edit

		exit.add(exit1);
		bar.add(exit);

		// Create Toolbar
		tb = new JToolBar();
		ImageIcon openi = new ImageIcon(getClass().getResource("open.png"));
		ImageIcon savei = new ImageIcon(getClass().getResource("save.png"));
		ImageIcon exiti = new ImageIcon(getClass().getResource("exit.png"));

		openb = new JButton(openi);
		openb.addActionListener(controller);
		saveb = new JButton(savei);
		saveb.addActionListener(controller);
		exitb = new JButton(exiti);
		exitb.addActionListener(controller);
		scheduleb = new JButton();
		scheduleb.setText("<html>Generate<br>Schedule</html>");
		scheduleb.addActionListener(controller);
		JPanel flowNorth = new JPanel(); // defaults to centered FlowLayout
		flowNorth.add(scheduleb);

		tb.add(openb);
		tb.add(saveb);
		tb.add(exitb);
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
