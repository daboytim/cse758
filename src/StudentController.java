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
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StudentController implements ActionListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	StudentDB students;
	AddStudentFrame addStd;
	private ClassFactory clsFac;

	public StudentController(JFrame f, StudentDB s, AddStudentFrame a,
			ClassFactory cf) {
		frame = f;
		students = s;
		addStd = a;
		clsFac = cf;
	}

	public void actionPerformed(ActionEvent event) {
		Object obj = event.getSource();
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(chooser.getAcceptAllFileFilter());
		if (obj.equals(Menu.sSave)) {
			if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
				writeFile(chooser.getSelectedFile());
		} else if (obj.equals(Menu.sAdd)) {
			addStd.buildFrame();
		}
	}

	void readFile(File filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			// Clean StudentDB so that it doesnt stack data
			students.removeAll();

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
				Date bDate = null;

				try {
					bDate = df.parse(params[3]);
				} catch (ParseException p) {
					try {
						df = new SimpleDateFormat("MM/dd/yyyy");
						bDate = df.parse(params[3]);
					} catch (ParseException p2) {
						JOptionPane
								.showMessageDialog(frame,
										"Invalid Birth Date in input file. Please check report and reload data");
					}

				}

				Students s = new Students(Integer.parseInt(params[0]),
						params[1], params[2], bDate,
						Integer.parseInt(params[4]),
						Integer.parseInt(params[5]),
						Integer.parseInt(params[6]),
						Integer.parseInt(params[7]), clsFac);
				students.addStudent(s);
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
			ArrayList<Students> stds = students.getStudents();

			for (int i = 0; i < students.getSize(); i++) {
				Students s = stds.get(i);
				String line;
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				line = s.getId() + "," + s.getFirstName() + ","
						+ s.getLastName() + "," + df.format(s.getBirthDate())
						+ "," + s.getMath() + "," + s.getRead() + ","
						+ s.getLA() + "," + s.getBL() + "\n";
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
