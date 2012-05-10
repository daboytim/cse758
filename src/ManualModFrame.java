import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class ManualModFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;
	private JPanel contentPane;
	private JTextField txtFieldFirstName;
	private JTextField txtFieldLastName;
	private JTextField txtFieldStudentID;
	private JButton btnOk;
	private JButton btnCancel;
	private JComboBox combBoxBhLevel;
	private JComboBox combBoxMath;
	private JComboBox combBoxLA;
	private JComboBox combBoxRead;
	private ScheduleDisplay sched;
	private Students std;
	private Classes mathClass, laClass, readClass;


	/**
	 * Create the frame.
	 */
	public ManualModFrame(Students student, ScheduleDisplay sched) {
		std = student;
		this.sched = sched;
		Classes[] cls = ClassFactory.getStdClasses(student);
		mathClass = cls[0];
		laClass = cls[1];
		readClass = cls[2];
		
		//create the frame
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Move Student");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel stdPanel = new JPanel();
		stdPanel.setBorder(new TitledBorder(null, "Student", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		stdPanel.setLayout(new GridLayout(4,2));
		contentPane.add(stdPanel);
		
		JLabel lblStudentId = new JLabel("Student ID");
		stdPanel.add(lblStudentId);
		
		txtFieldStudentID = new JTextField(std.getId()+"");
		txtFieldStudentID.setEditable(false);
		stdPanel.add(txtFieldStudentID);
		
		JLabel lblFirstName = new JLabel("First Name");
		stdPanel.add(lblFirstName);
		
		txtFieldFirstName = new JTextField(std.getFirstName());
		txtFieldFirstName.setEditable(false);
		stdPanel.add(txtFieldFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		stdPanel.add(lblLastName);
		
		txtFieldLastName = new JTextField(std.getLastName());
		txtFieldLastName.setEditable(false);
		stdPanel.add(txtFieldLastName);
		
		JLabel lblBehavioralLavel = new JLabel("Behavioral Lavel");
		stdPanel.add(lblBehavioralLavel);
		
		combBoxBhLevel = new JComboBox();
		combBoxBhLevel.addItem(new Integer(1));
		combBoxBhLevel.addItem(new Integer(2));
		combBoxBhLevel.addItem(new Integer(3));
		combBoxBhLevel.setSelectedItem(new Integer(std.getBL()));
		stdPanel.add(combBoxBhLevel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Schedule", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(new GridLayout(3,2));
		contentPane.add(panel_1);
		
		JLabel lblMathClass = new JLabel("Math Class");
		panel_1.add(lblMathClass);
		
		//add list of current math classes
		Vector<String> mathClassNames = new Vector<String>();
		for (int i=0; i<ClassFactory.mathClsLst.size(); i++) {
			mathClassNames.add(ClassFactory.mathClsLst.get(i).getClsName());
		}
		combBoxMath = new JComboBox(mathClassNames);
		if (mathClass != null) {
			combBoxMath.setSelectedItem(mathClass.getClsName());
		} else {
			combBoxMath.setSelectedItem("No Class");
		}
		panel_1.add(combBoxMath);
		
		JLabel lblLaClass = new JLabel("LA Class");
		panel_1.add(lblLaClass);
		
		//add list of current la classes
		Vector<String> laClassNames = new Vector<String>();
		for (int i=0; i<ClassFactory.laClsLst.size(); i++) {
			laClassNames.add(ClassFactory.laClsLst.get(i).getClsName());
		}
		combBoxLA = new JComboBox(laClassNames);
		if (laClass != null) {
			combBoxLA.setSelectedItem(laClass.getClsName());
		} else {
			combBoxLA.setSelectedItem("No Class");
		}
		panel_1.add(combBoxLA);
		
		JLabel lblReadingClass = new JLabel("Reading Class");
		panel_1.add(lblReadingClass);
		
		//add list of current reading classes
		Vector<String> readClassNames = new Vector<String>();
		for (int i=0; i<ClassFactory.readClsLst.size(); i++) {
			readClassNames.add(ClassFactory.readClsLst.get(i).getClsName());
		}
		combBoxRead = new JComboBox(readClassNames);
		if (readClass != null) {
			combBoxRead.setSelectedItem(readClass.getClsName());
		} else {
			combBoxRead.setSelectedItem("No Class");
		}
		panel_1.add(combBoxRead);
		
		JPanel panel_2 = new JPanel();
		panel_2.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_2.setBorder(null);
		contentPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnOk = new JButton("Ok");
		panel_2.add(btnOk);
		btnOk.addActionListener(this);
		
		btnCancel = new JButton("Cancel");
		panel_2.add(btnCancel);
		btnCancel.addActionListener(this);
		
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			moveStudent();
			this.dispose();
		} else if (e.getSource() == btnCancel) {
			this.dispose();
		}
		
	}
	
	private void moveStudent() {
		String newMath, newRead, newLA;
		
		newMath = (String) combBoxMath.getSelectedItem();
		newRead = (String) combBoxRead.getSelectedItem();
		newLA = (String) combBoxLA.getSelectedItem();
		
		Classes newMathCls=null, newReadCls=null, newLACls=null;
		//get class that corresponds to the newClass name
		for(int i=0; i<ClassFactory.mathClsLst.size(); i++) {
			if (newMath == ClassFactory.mathClsLst.get(i).getClsName()) {
				newMathCls = ClassFactory.mathClsLst.get(i);
				break;
			}
		}
		for(int i=0; i<ClassFactory.readClsLst.size(); i++) {
			if (newRead == ClassFactory.readClsLst.get(i).getClsName()) {
				newReadCls = ClassFactory.readClsLst.get(i);
				break;
			}
		}
		for(int i=0; i<ClassFactory.laClsLst.size(); i++) {
			if (newLA == ClassFactory.laClsLst.get(i).getClsName()) {
				newLACls = ClassFactory.laClsLst.get(i);
				break;
			}
		}
		if (newMath==null || newRead==null || newLA == null) {
			System.out.println("Error: new class does not exist");
			return;
			//error
		}
		//put the student in the new class
		if (mathClass != newMathCls) {
			if (mathClass == null) {
				//enroll student
			} else {
				try {
					ClassFactory.moveStd(mathClass, newMathCls, std);
					System.out.println("student moved from "+mathClass.getClsName()+" to "+newMathCls.getClsName());
				} catch (StdClsCompatibleException e1) {
				}
			}
		}
		if (readClass != newReadCls) {
			if (readClass == null) {
				//enroll student
			} else {
				try {
					ClassFactory.moveStd(readClass, newReadCls, std);
					System.out.println("student moved from "+readClass.getClsName()+" to "+newReadCls.getClsName());
				} catch (StdClsCompatibleException e1) {
				}
			}
		}
		if (laClass != newLACls) {
			if (laClass == null) {
				//enroll student
			} else {
				try {
					ClassFactory.moveStd(laClass, newLACls, std);
					System.out.println("student moved from "+laClass.getClsName()+" to "+newLACls.getClsName());
				} catch (StdClsCompatibleException e1) {
				}
			}
		}
		sched.update();
	}

}
