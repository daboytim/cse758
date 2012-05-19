import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TeacherModFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 167L;
	private JPanel contentPane;
	private JComboBox comboBoxReading;
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

	/**
	 * Create the frame.
	 */
	public TeacherModFrame(Teachers teacher) {
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Move Teacher");
		teach = teacher;
		
		buildFrame();
	}
	
	private void buildFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 230, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel teachPanel = new JPanel();
		teachPanel.setBorder(new TitledBorder(null, "Teacher", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		teachPanel.setLayout(new GridLayout(2,2));
		contentPane.add(teachPanel);
		
		teachPanel.add(new JLabel("Name"));
		
		nameField = new JTextField();
		nameField.setEditable(false);
		teachPanel.add(nameField);
		nameField.setColumns(10);
		
		teachPanel.add(new JLabel("Class Room"));
		
		comboBoxRoom = new JComboBox();
		teachPanel.add(comboBoxRoom);
		
		JPanel schedPanel = new JPanel();
		schedPanel.setBorder(new TitledBorder(null, "Schedule", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		schedPanel.setLayout(new GridLayout(5,2));
		contentPane.add(schedPanel);
		
		schedPanel.add(new JLabel("Reading Class"));
		
		comboBoxReading = new JComboBox();
		comboBoxMath = new JComboBox();
		comboBoxLa = new JComboBox();
		comboBoxHomeroom = new JComboBox();
		comboBoxSpecials = new JComboBox();
		
		schedPanel.add(comboBoxReading);
		schedPanel.add(new JLabel("Math Class"));		
		schedPanel.add(comboBoxMath);		
		schedPanel.add(new JLabel("LA Class"));		
		schedPanel.add(comboBoxLa);	
		schedPanel.add(new JLabel("Homeroom"));		
		schedPanel.add(comboBoxHomeroom);	
		schedPanel.add(new JLabel("Specials"));		
		schedPanel.add(comboBoxSpecials);
		
		btnPanel = new JPanel();
		btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
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
