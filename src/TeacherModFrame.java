import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


public class TeacherModFrame extends JFrame implements ActionListener, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 167L;
	private JPanel contentPane;
	private JComboBox comboBoxRead;
	private JComboBox comboBoxMath;
	private JComboBox comboBoxLa;
	private JComboBox comboBoxHomeroom;
	private JComboBox comboBoxSpecials;
	private Teachers teach;
	private JTextField nameField;
	private JPanel btnPanel;
	private JComboBox comboBoxRoom;
	private JButton btnOk;
	private JButton btnCancel;
	private Classes mathCls, readCls, laCls, hmrmCls, specCls;
	private ClassFactory clsFac;

	/**
	 * Create the frame.
	 */
	public TeacherModFrame(Teachers teacher, ClassFactory cf) {
		clsFac = cf;
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
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(NORMAL);
		setMaximizedBounds(new Rectangle(100,100,300,300));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel teachPanel = new JPanel();
		teachPanel.setBorder(new TitledBorder(null, "Teacher", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		teachPanel.setLayout(new GridLayout(2,2));
		contentPane.add(teachPanel);
		
		teachPanel.add(new JLabel("Name"));
		
		nameField = new JTextField(teach.getName());
		nameField.setEditable(false);
		teachPanel.add(nameField);
		nameField.setColumns(10);
		
		teachPanel.add(new JLabel("Class Room"));
		//TODO: get list of rooms
		Vector<Integer> rooms = new Vector<Integer>();
		rooms.add(teach.getRoom());
		comboBoxRoom = new JComboBox();
		comboBoxRoom.setSelectedItem(teach.getRoom());
		teachPanel.add(comboBoxRoom);
		
		JPanel schedPanel = new JPanel();
		schedPanel.setBorder(new TitledBorder(null, "Schedule", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		schedPanel.setLayout(new GridLayout(5,2));
		contentPane.add(schedPanel);
		
		schedPanel.add(new JLabel("Reading Class"));
		
		//add list of current math classes
		Vector<String> mathClassNames = new Vector<String>();
		for (int i=0; i<clsFac.mathClsLst.size(); i++) {
			mathClassNames.add(clsFac.mathClsLst.get(i).getFormalClassName());
		}
		mathClassNames.add("No Class");
		comboBoxMath = new JComboBox(mathClassNames);
		if (mathCls != null) {
			comboBoxMath.setSelectedItem(mathCls.getFormalClassName());
		} else {
			comboBoxMath.setSelectedItem("No Class");
		}
		
		//add list of current la classes
		Vector<String> laClassNames = new Vector<String>();
		for (int i=0; i<clsFac.laClsLst.size(); i++) {
			laClassNames.add(clsFac.laClsLst.get(i).getFormalClassName());
		}
		laClassNames.add("No Class");
		comboBoxLa = new JComboBox(laClassNames);
		if (laCls != null) {
			comboBoxLa.setSelectedItem(laCls.getFormalClassName());
		} else {
			comboBoxLa.setSelectedItem("No Class");
		}
		
		//add list of current reading classes
		Vector<String> readClassNames = new Vector<String>();
		for (int i=0; i<clsFac.readClsLst.size(); i++) {
			readClassNames.add(clsFac.readClsLst.get(i).getFormalClassName());
		}
		readClassNames.add("No Class");
		comboBoxRead = new JComboBox(readClassNames);
		if (readCls != null) {
			comboBoxRead.setSelectedItem(readCls.getFormalClassName());
		} else {
			comboBoxRead.setSelectedItem("No Class");
		}
		
		//add list of current homeroom classes
		Vector<String> hmrmClassNames = new Vector<String>();
		for (int i=0; i<clsFac.homeroomClsLst.size(); i++) {
			hmrmClassNames.add(clsFac.homeroomClsLst.get(i).getFormalClassName());
		}
		hmrmClassNames.add("No Class");
		comboBoxHomeroom = new JComboBox(hmrmClassNames);
		if (hmrmCls != null) {
			comboBoxHomeroom.setSelectedItem(hmrmCls.getFormalClassName());
		} else {
			comboBoxHomeroom.setSelectedItem("No Class");
		}
		
		//add list of current special classes
		Vector<String> specClassNames = new Vector<String>();
		for (int i=0; i<clsFac.specialClsLst.size(); i++) {
			specClassNames.add(clsFac.specialClsLst.get(i).getFormalClassName());
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
		contentPane.add(btnPanel);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(this);
		btnPanel.add(btnOk);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnPanel.add(btnCancel);
		
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			this.dispose();
		} else if (e.getSource() == btnOk) {
			//update stuff
			this.dispose();
		}
	}

}
