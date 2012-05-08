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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class StudentController implements TableModelListener, ActionListener {

	JTable table;
	JFrame frame;
	StudentFrame sFrame;
	StudentDB students;
	Object[][] data;
	Object[][] backup;
	String[] validStates = { "", "K", "1", "2", "3", "4", "5", "6", "7", "8" };
	String[] columnNames = { "Student ID", "First Name", "Last Name",
			"Birth Date", "Math Level", "Reading Level", "Language Arts Level",
			"Behavioral Level" };

	public StudentController(StudentFrame sf, JFrame f, StudentDB s) {
		sFrame = sf;
		frame = f;
		students = s;
	}


	public void populateTable() {
		int i = 0;
		data = new Object[300][8];
		if (students.getSize() > 0) {
			ArrayList<Students> stdList = students.getStudents();
			Iterator<Students> it = stdList.iterator();
			while (it.hasNext()) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Students s = it.next();
				data[i][0] = s.getId();
				data[i][1] = s.getFirstName();
				data[i][2] = s.getLastName();
				data[i][3] = df.format(s.getBirthDate());
				int mLevel = s.getMath();
				String math = (mLevel == 0) ? "K" : Integer.toString(mLevel);
				data[i][4] = math;

				int rLevel = s.getRead();
				String reading = (rLevel == 0) ? "K" : Integer.toString(rLevel);
				data[i][5] = reading;

				int lLevel = s.getLA();
				String la = (lLevel == 0) ? "K" : Integer.toString(lLevel);
				data[i][6] = la;
				data[i][7] = Integer.toString(s.getBL());
				i++;

			}

		}

		// Add an empty row

		while (i < 300) {
			data[i][0] = "";
			data[i][1] = "";
			data[i][2] = "";
			data[i][3] = "";
			data[i][4] = "";
			data[i][5] = "";
			data[i][6] = "";
			data[i][7] = "";

			i++;
		}
		backup = data;
		table = new JTable(data, columnNames);
		table.getModel().addTableModelListener(this);
	}

	public boolean isValidValue(Object value) {
		if (value instanceof String) {
			String sValue = (String) value;

			for (int i = 0; i < validStates.length; i++) {
				if (sValue.equals(validStates[i])) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		Object d = model.getValueAt(row, column);
		boolean isBlank = Utilities.isBlank(d.toString());

		if (row > 0 && Utilities.isBlank(data[row - 1][0].toString())
				&& !isBlank) {
			JOptionPane.showMessageDialog(frame,
					"Please do not leave open rows within the table");
			table.setValueAt("", row, column);
			return;
		}

		Object oldIDObj = backup[row][0];
		backup = data;
		int oldId;
		try {
			oldId = Integer.parseInt(oldIDObj.toString());
		} catch (NumberFormatException ne) {
			oldId = -1;
		}

		Students s;
		boolean newStudent = false;
		if (students.hasStudent(oldId)) {
			s = students.getStudent(oldId);
		} else {
			s = new Students();
			newStudent = true;
		}

		int x;
		switch (column) {
		case 0:
			int id;

			if (isBlank) {
				cleanStudentDB();
			} else {
				try {
					id = Integer.parseInt(d.toString());
					if (students.hasStudent(id)) {
						JOptionPane
								.showMessageDialog(frame,
										"A student with that ID already exists in the scheduling system.");
						table.setValueAt("", row, column);
					} else {
						if (id > 0)
							s.setId(id);
					}
				} catch (NumberFormatException ne) {

					JOptionPane.showMessageDialog(frame,
							"Student ID should be an integer value");

				}
			}
			break;
		case 1:
			if (data[row][0].toString().isEmpty()) {
				if (!isBlank) {
					JOptionPane.showMessageDialog(frame,
							"Please provide a Student ID first.\n");
					table.setValueAt("", row, column);
				}
			} else {
				s.setFirstName(d.toString());
			}
			break;
		case 2:
			if (data[row][0].toString().isEmpty()) {
				if (!isBlank) {
					JOptionPane.showMessageDialog(frame,
							"Please provide a Student ID first.\n");
					table.setValueAt("", row, column);
				}
			} else {
				s.setLastName(d.toString());
			}
			break;
		case 3:
			try {
				if (data[row][0].toString().isEmpty()) {
					if (!isBlank) {
						JOptionPane.showMessageDialog(frame,
								"Please provide a Student ID first.\n");
						table.setValueAt("", row, column);
					}
				} else {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date bDate = df.parse(d.toString());
					System.out.println(bDate);
					s.setBirthDate(bDate);
				}
			} catch (ParseException n) {
				if (!isBlank) {
					JOptionPane.showMessageDialog(frame,
							"Expected Birth Date in the form yyyy-mm-dd");
					table.setValueAt("", row, column);
				}
			}
			break;
		case 4:
			if (data[row][0].toString().isEmpty()) {
				if (!isBlank) {
					JOptionPane.showMessageDialog(frame,
							"Please provide a Student ID first.\n");
					table.setValueAt("", row, column);
				}

			} else {
				if (!isBlank) {
					x = (d.toString().equals("K")) ? 0 : Integer.parseInt(d
							.toString());
					s.setMath(x);
				}
			}
			break;
		case 5:
			if (data[row][0].toString().isEmpty()) {
				if (!isBlank) {
					JOptionPane.showMessageDialog(frame,
							"Please provide a Student ID first.\n");
					table.setValueAt("", row, column);
				}

			} else {
				if (!isBlank) {
					x = (d.toString().equals("K")) ? 0 : Integer.parseInt(d
							.toString());
					s.setRead(x);
				}
			}
			break;
		case 6:
			if (data[row][0].toString().isEmpty()) {
				if (!isBlank) {
					JOptionPane.showMessageDialog(frame,
							"Please provide a Student ID first.\n");
					table.setValueAt("", row, column);
				}

			} else {
				if (!isBlank) {
					x = (d.toString().equals("K")) ? 0 : Integer.parseInt(d
							.toString());
					s.setLA(x);
				}
			}
			break;

		case 7:
			if (data[row][0].toString().isEmpty()) {
				if (!isBlank) {
					JOptionPane.showMessageDialog(frame,
							"Please provide a Student ID first.\n");
					table.setValueAt("", row, column);
				}

			} else {
				if (!isBlank) {
					x = Integer.parseInt(d.toString());
					s.setBL(x);
				}
			}
			break;
		}

		if (!isBlank) {
			if (newStudent) {
				students.addStudent(s);
			} else {
				students.modifyStudent(oldId, s);
			}
		}
	}

	/*
	 * I apologize for the inefficiency of this, but given the small data set,
	 * it shouldn't be awful
	 */
	private void cleanStudentDB() {
		List<Students> stds = students.getStudents();
		for (int i = 0; i < stds.size(); i++) {
			Students std = stds.get(i);
			int id = std.getId();
			for (int j = 0; j < students.getSize(); j++) {
				try {
					if (id == Integer.parseInt(data[j][0].toString())) {
						return;
					}
				} catch (NumberFormatException n) {
					// do nothing
				}
			}
			// if we got here, the student is not in the data array anymore
			students.removeStudent(id);
		}

		backup = data;

	}
	
	public void actionPerformed(ActionEvent event) {
		Object obj = event.getSource();
		StudentMenu.chooser = new JFileChooser();
		StudentMenu.chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		StudentMenu.chooser.setFileFilter(StudentMenu.chooser.getAcceptAllFileFilter());
		if (obj.equals(StudentMenu.open) || obj.equals(StudentMenu.openb)) {
			// use chooser.getSelectedFile() to get file
			// Some code here to parse or call a parser
			if (StudentMenu.chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
				readFile(StudentMenu.chooser.getSelectedFile());

		} else if (obj.equals(StudentMenu.save) || obj.equals(StudentMenu.saveb)) {
			if (StudentMenu.chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
				writeFile(StudentMenu.chooser.getSelectedFile());
		} else if (obj.equals(StudentMenu.copy)) {

		} else if (obj.equals(StudentMenu.paste)) {

		} else if (obj.equals(StudentMenu.exit1) || obj.equals(StudentMenu.exitb)) {
			// Probably want to do checking here if user wants to save
			System.exit(0);
		} else if (obj.equals(StudentMenu.scheduleb)) {
			// Code here to call schedule algorithm and display schedules
			Schedulizer.genSchedule(students);
			new ScheduleDisplay();

		}
		// Starting Kai's edit
		else if (obj.equals(StudentMenu.pref1)) {
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

	private void readFile(File filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			while (br.ready()) {
				String line = br.readLine();
				String[] params = line.split(",");
				while (params.length < 8) {
					String[] tmp = new String[params.length + 1];
					for (int i = 0; i < params.length; i++) {
						tmp[i] = params[i];
					}
					tmp[params.length] = "0";
					params = tmp;
				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Students s = new Students(Integer.parseInt(params[0]),
						params[1], params[2], df.parse(params[3]),
						Integer.parseInt(params[4]),
						Integer.parseInt(params[5]),
						Integer.parseInt(params[6]),
						Integer.parseInt(params[7]));
				students.addStudent(s);
			}
			//new StudentFrame(frame, students);
			sFrame.update();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeFile(File filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			ArrayList<Students> stds = students.getStudents();

			for (int i = 0; i < students.getSize(); i++) {
				Students s = stds.get(i);
				String line;
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				line = s.getId() + "," + s.getFirstName() + ","
						+ s.getLastName() + ","
						+ df.format(s.getBirthDate()) + ","
						+ s.getMath() + "," + s.getRead() + "," + s.getLA()
						+ "," + s.getBL() + "\n";
				bw.append(line);

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
