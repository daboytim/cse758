import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingConstants;


public class TeacherScheduleFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 644L;
	private static final int numRows = 13;
	private static final int numColumns = 5;
	private JPanel contentPane;
	private JButton btnPrint;
	private JButton btnClose;
	private Teachers teach;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public TeacherScheduleFrame(Teachers teacher) {
		teach = teacher;
		Object[] columnNames = new Object[numColumns];
		Object[][] data = new Object[numRows][numColumns];
		initData(data);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle(teach.getName() + "'s Schedule");
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		contentPane.add(table, BorderLayout.CENTER);
		
		JPanel btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.SOUTH);
		
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(this);
		btnPanel.add(btnPrint);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		btnPanel.add(btnClose);
		
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnPrint) {
			print();
			this.dispose();
		} else if(e.getSource() == btnClose) {
			this.dispose();
		}
	}
	
	public void print() {
		//print the schedule
	}
	

	private void initData(Object[][] data) {
		int readId, laId, mathId, hrId, specId;
		Classes read=null, la=null, math=null, homeroom=null, specials=null;
		
		//figure out which classes this teacher teaches
		//there has got to be a better way to do this...
		//keep class objs in teacher obj? No, thats too easy.
		//well, here goes 5 for loops to determine which 
		//clsID corresponds to which class...efficient!
		for (Classes c:ClassFactory.readClsLst) {
			if (c.getClsID() == teach.getClsID(Teachers.Type.READ)) {
				read = c;
				break;
			}
		}
		for (Classes c:ClassFactory.laClsLst) {
			if (c.getClsID() == teach.getClsID(Teachers.Type.LA)) {
				la = c;
				break;
			}
		}
		for (Classes c:ClassFactory.mathClsLst) {
			if (c.getClsID() == teach.getClsID(Teachers.Type.MATH)) {
				math = c;
				break;
			}
		}
		for (Classes c:ClassFactory.homeroomClsLst) {
			if (c.getClsID() == teach.getClsID(Teachers.Type.HR)) {
				homeroom = c;
				break;
			}
		}
		for (Classes c:ClassFactory.specialClsLst) {
			if (c.getClsID() == teach.getClsID(Teachers.Type.SP)) {
				specials = c;
				break;
			}
		}
		
		List<Students> roster;
		//now put all the data we want into a table
		//I hope they like the format because i'm 
		//gonna hardcode all these values
		data[0][0] = "Name";	data[0][1] = teach.getName();
		data[0][3] = "Room";	//data[0][4] = teach.getRoom();
		
		data[2][0] = read.getClsName();
		roster = read.getStudents();
		for (int i=0; i<roster.size(); i++)
			data[3+i][0] = roster.get(i);
			
		data[2][1] = la.getClsName();
		roster = la.getStudents();
		for (int i=0; i<roster.size(); i++)
			data[3+i][1] = roster.get(i);
		
		data[2][2] = math.getClsName();
		roster = math.getStudents();
		for (int i=0; i<roster.size(); i++)
			data[3+i][2] = roster.get(i);
		
		data[2][3] = homeroom.getClsName();
		roster = homeroom.getStudents();
		for (int i=0; i<roster.size(); i++)
			data[3+i][3] = roster.get(i);
		
		data[2][4] = specials.getClsName();
		roster = specials.getStudents();
		for (int i=0; i<roster.size(); i++)
			data[3+i][4] = roster.get(i);
	}
}
