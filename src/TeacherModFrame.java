import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class TeacherModFrame extends JFrame implements ActionListener,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 167L;
	private JPanel teachContentPane, classContentPane;
	private JComboBox comboBoxRead;
	private JComboBox comboBoxMath;
	private JComboBox comboBoxLa;
	private JComboBox comboBoxHomeroom;
	private JComboBox comboBoxSpecials;
	private JComboBox level;
	private Teachers teach;
	private JTextField nameField;
	private JTextField roomField;
	private JPanel btnPanel, clsBtnPanel;
	private JButton btnOk, clsBtnOk;
	private JButton btnCancel, clsBtnCancel;
	private Classes mathCls, readCls, laCls, hmrmCls, specCls, selectedCls;
	private ClassFactory clsFac;
	private StudentDB students;
	private StudentTable sTab;
	private ScheduleDisplay sched;

	private String[] validStates = { "", "K", "1", "2", "3", "4", "5", "6",
			"7", "8" };

	/**
	 * Create the frame.
	 */
	public TeacherModFrame(Teachers teacher, ClassFactory cf, Classes cls,
			StudentDB s, StudentTable sT, ScheduleDisplay sD) {
		students = s;
		clsFac = cf;
		selectedCls = cls;
		sched = sD;
		sTab = sT;
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Move Teacher");
		teach = teacher;
		mathCls = teach.getCls(Teachers.Type.MATH);
		readCls = teach.getCls(Teachers.Type.READ);
		laCls = teach.getCls(Teachers.Type.LA);
		hmrmCls = teach.getCls(Teachers.Type.HR);
		specCls = teach.getCls(Teachers.Type.SP);

		buildFrame();
	}

	private void buildFrame() {
		JTabbedPane tabbedPane = new JTabbedPane();

		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(NORMAL);
		setMaximizedBounds(new Rectangle(100, 100, 300, 300));
		teachContentPane = new JPanel();
		teachContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(tabbedPane);
		teachContentPane.setLayout(new BoxLayout(teachContentPane,
				BoxLayout.Y_AXIS));

		classContentPane = new JPanel();
		classContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		classContentPane.setLayout(new BorderLayout());

		// Create Teacher Panel
		JPanel teachPanel = new JPanel();
		teachPanel.setBorder(new TitledBorder(null, "Teacher",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		teachPanel.setLayout(new GridLayout(2, 2));
		teachContentPane.add(teachPanel);

		teachPanel.add(new JLabel("Name"));

		nameField = new JTextField(teach.getName());
		nameField.setEditable(false);
		teachPanel.add(nameField);
		nameField.setColumns(10);

		teachPanel.add(new JLabel("Class Room"));
		
		roomField = new JTextField(""+teach.getRoom());
		roomField.setColumns(10);
		teachPanel.add(roomField);

		JPanel schedPanel = new JPanel();
		schedPanel.setBorder(new TitledBorder(null, "Schedule",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		schedPanel.setLayout(new GridLayout(5, 2));
		teachContentPane.add(schedPanel);

		schedPanel.add(new JLabel("Reading Class"));

		// add list of current math classes
		Vector<String> mathClassNames = new Vector<String>();
		for (int i = 0; i < clsFac.mathClsLst.size(); i++) {
			mathClassNames.add(clsFac.mathClsLst.get(i).getFormalClassName());
		}
		mathClassNames.add("No Class");
		comboBoxMath = new JComboBox(mathClassNames);
		if (mathCls != null) {
			comboBoxMath.setSelectedItem(mathCls.getFormalClassName());
		} else {
			comboBoxMath.setSelectedItem("No Class");
		}

		// add list of current la classes
		Vector<String> laClassNames = new Vector<String>();
		for (int i = 0; i < clsFac.laClsLst.size(); i++) {
			laClassNames.add(clsFac.laClsLst.get(i).getFormalClassName());
		}
		laClassNames.add("No Class");
		comboBoxLa = new JComboBox(laClassNames);
		if (laCls != null) {
			comboBoxLa.setSelectedItem(laCls.getFormalClassName());
		} else {
			comboBoxLa.setSelectedItem("No Class");
		}

		// add list of current reading classes
		Vector<String> readClassNames = new Vector<String>();
		for (int i = 0; i < clsFac.readClsLst.size(); i++) {
			readClassNames.add(clsFac.readClsLst.get(i).getFormalClassName());
		}
		readClassNames.add("No Class");
		comboBoxRead = new JComboBox(readClassNames);
		if (readCls != null) {
			comboBoxRead.setSelectedItem(readCls.getFormalClassName());
		} else {
			comboBoxRead.setSelectedItem("No Class");
		}

		// add list of current homeroom classes
		Vector<String> hmrmClassNames = new Vector<String>();
		for (int i = 0; i < clsFac.homeroomClsLst.size(); i++) {
			hmrmClassNames.add(clsFac.homeroomClsLst.get(i)
					.getFormalClassName());
		}
		hmrmClassNames.add("No Class");
		comboBoxHomeroom = new JComboBox(hmrmClassNames);
		if (hmrmCls != null) {
			comboBoxHomeroom.setSelectedItem(hmrmCls.getFormalClassName());
		} else {
			comboBoxHomeroom.setSelectedItem("No Class");
		}

		// add list of current special classes
		Vector<String> specClassNames = new Vector<String>();
		for (int i = 0; i < clsFac.specialClsLst.size(); i++) {
			specClassNames
					.add(clsFac.specialClsLst.get(i).getFormalClassName());
		}
		specClassNames.add("No Class");
		comboBoxSpecials = new JComboBox(specClassNames);
		if (specCls != null) {
			comboBoxSpecials.setSelectedItem(specCls.getFormalClassName());
		} else {
			comboBoxSpecials.setSelectedItem("No Class");
		}

		schedPanel.add(comboBoxRead);
		schedPanel.add(new JLabel("Math Class"));
		schedPanel.add(comboBoxMath);
		schedPanel.add(new JLabel("LA Class"));
		schedPanel.add(comboBoxLa);
		schedPanel.add(new JLabel("Homeroom"));
		schedPanel.add(comboBoxHomeroom);
		schedPanel.add(new JLabel("Specials"));
		schedPanel.add(comboBoxSpecials);

		btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		teachContentPane.add(btnPanel);

		btnOk = new JButton("Ok");
		btnOk.addActionListener(this);
		btnPanel.add(btnOk);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnPanel.add(btnCancel);

		// Create Class Tab

		JPanel classPanel = new JPanel();
		classPanel.setBorder(new TitledBorder(null, "Class",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		classPanel.setLayout(new GridLayout(1, 2));
		classContentPane.add(classPanel, BorderLayout.NORTH);
		classPanel.add(new JLabel("Class Level"));

		level = new JComboBox(validStates);
		String lvl = (selectedCls.getLvl() == 0) ? "K" : String
				.valueOf(selectedCls.getLvl());
		level.setSelectedItem(lvl);
		classPanel.add(level);
		
		clsBtnPanel = new JPanel();
		clsBtnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		clsBtnOk = new JButton("Ok");
		clsBtnOk.addActionListener(this);
		clsBtnPanel.add(clsBtnOk);

		clsBtnCancel = new JButton("Cancel");
		clsBtnCancel.addActionListener(this);
		clsBtnPanel.add(clsBtnCancel);

		classContentPane.add(clsBtnPanel, BorderLayout.SOUTH);

		tabbedPane.add("Teacher", teachContentPane);
		tabbedPane.add("Class", classContentPane);
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel || e.getSource() == clsBtnCancel) {
			this.dispose();
		} else if (e.getSource() == btnOk) {
			// update stuff
			try {
				int newRoom = Integer.parseInt(roomField.getText());
				if (newRoom != teach.getRoom())
					teach.setRoom(newRoom);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this,
						"'"+roomField.getText()+"' is not a valid room number.\n"+
								"Please enter an integer.",
						"Error",
						JOptionPane.WARNING_MESSAGE);
				return;
			} catch (NullPointerException e2) {
				JOptionPane.showMessageDialog(this,
						"Please enter a room number.",
						"Error",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			swithcClass();
			sched.update();
			MainFrame.tabbedPane.revalidate();
			MainFrame.tabbedPane.setVisible(false);
			MainFrame.tabbedPane.repaint();
			MainFrame.tabbedPane.setVisible(true);
			this.dispose();
		} else if (e.getSource() == clsBtnOk) {
			int l;
			l = (level.getSelectedItem().toString().equals("K")) ? 0 : Integer
					.parseInt(level.getSelectedItem().toString());
			selectedCls.setClsLvl(l);

			int rtn = JOptionPane
					.showConfirmDialog(
							this,
							"Would you like to also change the level of each student in the class?\n",
							"Notice!", JOptionPane.YES_NO_OPTION);
			String type = selectedCls.getClsName();
			if (rtn == JOptionPane.YES_OPTION) {
				ArrayList<Students> s = (ArrayList<Students>) selectedCls
						.getStudents();
				for (int i = 0; i < s.size(); i++) {
					if (type.equals("math")) {
						s.get(i).setMath(l);
						students.modifyStudent(s.get(i).getId(), s.get(i));
					} else if (type.equals("read")) {
						s.get(i).setRead(l);
						students.modifyStudent(s.get(i).getId(), s.get(i));

					} else if (type.equals("la")) {
						s.get(i).setLA(l);
						students.modifyStudent(s.get(i).getId(), s.get(i));

					}
				}
			}
			sTab.update();
			sched.update();
			MainFrame.tabbedPane.revalidate();
			MainFrame.tabbedPane.setVisible(false);
			MainFrame.tabbedPane.repaint();
			MainFrame.tabbedPane.setVisible(true);
			this.dispose();
		}
	}
	
	private void swithcClass() {
		String newMath, newRead, newLA, newHmrm, newSpec;
		
		newMath = (String) comboBoxMath.getSelectedItem();
		newRead = (String) comboBoxRead.getSelectedItem();
		newLA = (String) comboBoxLa.getSelectedItem();
		newHmrm = (String) comboBoxHomeroom.getSelectedItem();
		newSpec = (String) comboBoxSpecials.getSelectedItem();
		
		Classes newMathCls=null, newReadCls=null, newLACls=null, newHmrmCls=null, newSpecCls=null;
		//get class that corresponds to the newClass name
		for(int i=0; i<clsFac.mathClsLst.size(); i++) {
			if (newMath == clsFac.mathClsLst.get(i).getFormalClassName()) {
				newMathCls = clsFac.mathClsLst.get(i);
				break;
			}
		}
		for(int i=0; i<clsFac.readClsLst.size(); i++) {
			if (newRead == clsFac.readClsLst.get(i).getFormalClassName()) {
				newReadCls = clsFac.readClsLst.get(i);
				break;
			}
		}
		for(int i=0; i<clsFac.laClsLst.size(); i++) {
			if (newLA == clsFac.laClsLst.get(i).getFormalClassName()) {
				newLACls = clsFac.laClsLst.get(i);
				break;
			}
		}
		for(int i=0; i<clsFac.homeroomClsLst.size(); i++) {
			if (newHmrm == clsFac.homeroomClsLst.get(i).getFormalClassName()) {
				newHmrmCls = clsFac.homeroomClsLst.get(i);
				break;
			}
		}
		for(int i=0; i<clsFac.specialClsLst.size(); i++) {
			if (newSpec == clsFac.specialClsLst.get(i).getFormalClassName()) {
				newSpecCls = clsFac.specialClsLst.get(i);
				break;
			}
		}
		if (newMath==null || newRead==null || newLA==null || newHmrm==null || newSpec==null) {
			//System.out.println("Error: new class does not exist");
			//return;
			//error
		}
		//put the student in the new class
		if (mathCls != newMathCls) {
			if (newMath.equals("No Class")) {
				teach.unassignFromClass(Teachers.Type.MATH);
			} else if (teach.canTeach(newMathCls.getLvl(), Teachers.Type.MATH)) {
				teach.assignToClass(newMathCls, Teachers.Type.MATH);
				System.out.println("teacher moved from "+mathCls.getFormalClassName()+" to "+newMathCls.getFormalClassName());
			}
		}
		if (readCls != newReadCls) {
			if (newRead.equals("No Class")) {
				teach.unassignFromClass(Teachers.Type.READ);
			} else if (teach.canTeach(newReadCls.getLvl(), Teachers.Type.READ)) {
				teach.assignToClass(newReadCls, Teachers.Type.READ);
				System.out.println("teacher moved from "+readCls.getFormalClassName()+" to "+newReadCls.getFormalClassName());
			}
		}
		if (laCls != newLACls) {
			if (newLA.equals("No Class")) {
				teach.unassignFromClass(Teachers.Type.LA);
			} else if (teach.canTeach(newLACls.getLvl(), Teachers.Type.LA)) {
				teach.assignToClass(newLACls, Teachers.Type.LA);
				System.out.println("teacher moved from "+laCls.getFormalClassName()+" to "+newLACls.getFormalClassName());
			}
		}
		if (hmrmCls != newHmrmCls) {
			if (newHmrm.equals("No Class")) {
				teach.unassignFromClass(Teachers.Type.HR);
			} else {
				teach.assignToClass(newHmrmCls, Teachers.Type.HR);
				System.out.println("teacher moved from "+hmrmCls.getFormalClassName()+" to "+newHmrmCls.getFormalClassName());
			}
		}
		if (specCls != newSpecCls) {
			if (newSpec.equals("No Class")) {
				teach.unassignFromClass(Teachers.Type.SP);
			} else {
				teach.assignToClass(newSpecCls, Teachers.Type.SP);
				System.out.println("teacher moved from "+specCls.getFormalClassName()+" to "+newSpecCls.getFormalClassName());
			}
		}
	}
	
}
