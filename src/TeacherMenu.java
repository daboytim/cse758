import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class TeacherMenu extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuBar bar;
	JToolBar tb;
	JFrame frame;
	JMenu file, edit, exit, pref;
	JMenuItem open, save, copy, paste, exit1, pref1;
	JButton openb, saveb, exitb, scheduleb;
	JTextArea textArea;
	JFileChooser chooser;
	FileOutputStream fos;
	BufferedWriter bwriter;
	TeacherDB teachers;

	public TeacherMenu(TeacherDB t, JFrame f) {
		frame = f;
		teachers = t;
		bar = new JMenuBar();
		bar.setFont(new Font("Arial", Font.BOLD, 14));
		file = new JMenu(" File ");
		open = new JMenuItem(" Open... ");
		open.addActionListener(this);
		save = new JMenuItem(" Save... ");
		save.addActionListener(this);
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
		exit1.addActionListener(this);

		// *****add by Kai****

		pref = new JMenu(" Preference ");
		pref1 = new JMenuItem("Set Max number of classes");
		pref1.addActionListener(this);
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
		openb.addActionListener(this);
		saveb = new JButton(savei);
		saveb.addActionListener(this);
		exitb = new JButton(exiti);
		exitb.addActionListener(this);
		scheduleb = new JButton();
		scheduleb.setText("<html>Assign<br>Teachers</html>");
		scheduleb.addActionListener(this);
		JPanel flowNorth = new JPanel(); // defaults to centered FlowLayout
		flowNorth.add(scheduleb);

		tb.add(openb);
		tb.add(saveb);
		tb.add(exitb);
		tb.add(flowNorth);
		tb.setAlignmentX(0);

	}

	public void actionPerformed(ActionEvent event) {
		Object obj = event.getSource();
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(chooser.getAcceptAllFileFilter());
		if (obj.equals(open) || obj.equals(openb)) {
			// use chooser.getSelectedFile() to get file
			// Some code here to parse or call a parser
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
				readFile(chooser.getSelectedFile());

		} else if (obj.equals(save) || obj.equals(saveb)) {
			if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
				writeFile(chooser.getSelectedFile());
		} else if (obj.equals(copy)) {

		} else if (obj.equals(paste)) {

		} else if (obj.equals(exit1) || obj.equals(exitb)) {
			// Probably want to do checking here if user wants to save
			System.exit(0);
		} else if (obj.equals(scheduleb)) {
			// TODO: Code here to call schedule algorithm and display schedules
			ScheduleTeachers.assign(teachers);
		}
		// Starting Kai's edit
		else if (obj.equals(pref1)) {
			String str = JOptionPane.showInputDialog(null,
					"Enter max number of classes : ",
					"Set Max number of Classes", 1);
			try {
				if (str != null) {
					ClassFactory.setMaxCls(Integer.parseInt(str));
					JOptionPane.showMessageDialog(null,
							"Max number of classes successfully set to"
									+ ClassFactory.getMaxCls(), "Success", 1);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please input an integer.",
						"Warning", 1);
			}
		}
		// end of kai's edit
	}

	public JMenuBar getMenu() {
		return bar;
	}

	public JToolBar getToolBar() {
		return tb;
	}

	private void readFile(File filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			while (br.ready()) {
				String line = br.readLine();
				String[] params = line.split(",");
				Teachers t;
				if (!Utilities.isBlank(params[0])) {
					t = new Teachers(params[0]);
				} else {
					return;
				}
				if (params.length == 1) {
					teachers.addTeacher(t);
					return;
				}
				String[] mClassList = params[1].split(";");
				ArrayList<Integer> mClasses = new ArrayList<Integer>();
				for (int i = 0; i < mClassList.length; i++) {
					try {
						int tmp = Integer.parseInt(mClassList[i]);
						mClasses.add(tmp);
					} catch (NumberFormatException e) {
						// maybe an error here, but they probably wont be
						// inputting integers so we'll need to do conversions
					}
				}
				t.setPreference(mClasses, Teachers.Type.MATH);
				if (params.length == 2) {
					teachers.addTeacher(t);
					return;
				}

				String[] rClassList = params[2].split(";");
				ArrayList<Integer> rClasses = new ArrayList<Integer>();
				for (int i = 0; i < rClassList.length; i++) {
					try {
						int tmp = Integer.parseInt(rClassList[i]);
						rClasses.add(tmp);
					} catch (NumberFormatException e) {
						// maybe an error here, but they probably wont be
						// inputting integers so we'll need to do conversions
					}
				}
				t.setPreference(rClasses, Teachers.Type.READ);
				if (params.length == 3) {
					teachers.addTeacher(t);
					return;
				}

				String[] lClassList = params[3].split(";");
				ArrayList<Integer> lClasses = new ArrayList<Integer>();
				for (int i = 0; i < lClassList.length; i++) {
					try {
						int tmp = Integer.parseInt(lClassList[i]);
						lClasses.add(tmp);
					} catch (NumberFormatException e) {
						// maybe an error here, but they probably wont be
						// inputting integers so we'll need to do conversions
					}
				}
				t.setPreference(lClasses, Teachers.Type.LA);
				teachers.addTeacher(t);

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeFile(File filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			ArrayList<Teachers> tchrs = teachers.getTeachers();

			for (int i = 0; i < teachers.getSize(); i++) {
				Teachers t = tchrs.get(i);
				System.out.println(t.getName());
				StringBuilder line = new StringBuilder(t.getName() + ",");
				ArrayList<Integer> mClasses = t
						.getPreference(Teachers.Type.MATH);
				for (int j = 0; j < mClasses.size(); j++) {
					line.append(mClasses.get(j) + ";");
				}
				line.append(',');
				ArrayList<Integer> rClasses = t
						.getPreference(Teachers.Type.READ);
				for (int j = 0; j < rClasses.size(); j++) {
					line.append(rClasses.get(j) + ";");
				}
				line.append(',');
				ArrayList<Integer> lClasses = t.getPreference(Teachers.Type.LA);
				for (int j = 0; j < lClasses.size(); j++) {
					line.append(lClasses.get(j) + ";");
				}
				bw.append(line.toString());
			}
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
