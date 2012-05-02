import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
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
	private JComboBox combBoxRead;
	private JComboBox combBoxLA;
	
	private Students std;
	private Classes mathClass, readClass, laClass;


	/**
	 * Create the frame.
	 */
	public ManualModFrame(Students student, Classes mathCls, Classes readCls, Classes laCls) {
		std = student;
		mathClass = mathCls;
		readClass = readCls;
		laClass = laCls;
		
		//create the frame
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Edit Student");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel stdPanel = new JPanel();
		stdPanel.setBorder(new TitledBorder(null, "Student", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(stdPanel);
		stdPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblStudentId = new JLabel("Student ID");
		stdPanel.add(lblStudentId, "2, 2, right, default");
		
		txtFieldStudentID = new JTextField(std.getId());
		txtFieldStudentID.setEditable(false);
		stdPanel.add(txtFieldStudentID, "4, 2, fill, default");
		txtFieldStudentID.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name");
		stdPanel.add(lblFirstName, "2, 4, right, default");
		
		txtFieldFirstName = new JTextField(std.getFirstName());
		txtFieldFirstName.setEditable(false);
		stdPanel.add(txtFieldFirstName, "4, 4, fill, default");
		txtFieldFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		stdPanel.add(lblLastName, "2, 6, right, default");
		
		txtFieldLastName = new JTextField(std.getLastName());
		txtFieldLastName.setEditable(false);
		stdPanel.add(txtFieldLastName, "4, 6, fill, default");
		txtFieldLastName.setColumns(10);
		
		JLabel lblBehavioralLavel = new JLabel("Behavioral Lavel");
		stdPanel.add(lblBehavioralLavel, "2, 8, right, default");
		
		combBoxBhLevel = new JComboBox();
		combBoxBhLevel.addItem(new Integer(1));
		combBoxBhLevel.addItem(new Integer(2));
		combBoxBhLevel.addItem(new Integer(3));
		combBoxBhLevel.setSelectedItem(new Integer(std.getBL()));
		stdPanel.add(combBoxBhLevel, "4, 8, fill, default");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Schedule", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_1);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblMathClass = new JLabel("Math Class");
		panel_1.add(lblMathClass, "2, 2, right, default");
		
		//add list of current math classes
		Vector<String> mathClassNames = new Vector<String>();
		for (int i=0; i<ClassFactory.mathClsLst.size(); i++) {
			mathClassNames.add(ClassFactory.mathClsLst.get(i).getClsName());
		}
		combBoxMath = new JComboBox(mathClassNames);
		combBoxMath.setSelectedItem(mathClass.getClsName());
		panel_1.add(combBoxMath, "4, 2, fill, default");
		
		JLabel lblReadingClass = new JLabel("Reading Class");
		panel_1.add(lblReadingClass, "2, 4, right, default");
		
		//add list of current reading classes
		Vector<String> readClassNames = new Vector<String>();
		for (int i=0; i<ClassFactory.readClsLst.size(); i++) {
			readClassNames.add(ClassFactory.readClsLst.get(i).getClsName());
		}
		combBoxRead = new JComboBox(readClassNames);
		combBoxRead.setSelectedItem(readClass.getClsName());
		panel_1.add(combBoxRead, "4, 4, fill, default");
		
		JLabel lblLaClass = new JLabel("LA Class");
		panel_1.add(lblLaClass, "2, 6, right, default");
		
		//add list of current la classes
		Vector<String> laClassNames = new Vector<String>();
		for (int i=0; i<ClassFactory.laClsLst.size(); i++) {
			laClassNames.add(ClassFactory.laClsLst.get(i).getClsName());
		}
		combBoxLA = new JComboBox(laClassNames);
		combBoxLA.setSelectedItem(laClass.getClsName());
		panel_1.add(combBoxLA, "4, 6, fill, default");
		
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			String newMath, newRead, newLA;
			
			newMath = (String) combBoxMath.getSelectedItem();
			newRead = (String) combBoxRead.getSelectedItem();
			newLA = (String) combBoxLA.getSelectedItem();
			
			Classes newMathCls=null, newReadCls=null, newLACls=null;
			//get class that corresponds to the newClass name
			for(int i=0; i<ClassFactory.mathClsLst.size(); i++) {
				if (newMath == ClassFactory.mathClsLst.get(i).getClsName()) {
					newMathCls = ClassFactory.mathClsLst.get(i);
				}
			}
			for(int i=0; i<ClassFactory.readClsLst.size(); i++) {
				if (newRead == ClassFactory.readClsLst.get(i).getClsName()) {
					newReadCls = ClassFactory.readClsLst.get(i);
				}
			}
			for(int i=0; i<ClassFactory.laClsLst.size(); i++) {
				if (newLA == ClassFactory.laClsLst.get(i).getClsName()) {
					newLACls = ClassFactory.laClsLst.get(i);
				}
			}
			if (newMath==null || newRead==null || newLA == null) {
				//error
			}
			//put the student in the new class
			if (mathClass != newMathCls) {
				try {
					ClassFactory.moveStd(mathClass, newMathCls, std);
				} catch (StdClsCompatibleException e1) {
				}
			}
			if (readClass != newReadCls) {
				try {
					ClassFactory.moveStd(readClass, newReadCls, std);
				} catch (StdClsCompatibleException e1) {
				}
			}
			if (laClass != newLACls) {
				try {
					ClassFactory.moveStd(laClass, newLACls, std);
				} catch (StdClsCompatibleException e1) {
				}
			}
			
			this.dispose();
		} else if (e.getSource() == btnCancel) {
			this.dispose();
		}
		
	}

}
