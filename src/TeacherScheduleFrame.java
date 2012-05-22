import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.Serializable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTable.PrintMode;
import javax.swing.border.EmptyBorder;


public class TeacherScheduleFrame extends JFrame implements ActionListener, Serializable {

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
		for (int i=0; i<numColumns; i++) {
			columnNames[i] = "";
		}
		Object[][] data = new Object[numRows][numColumns];
		initData(data);
		
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(NORMAL);
		setMaximizedBounds(new Rectangle(100,100,600,280));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle(teach.getName() + "'s Schedule");
		
		table = new JTable(data, columnNames);
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
		} else if(e.getSource() == btnClose) {
			this.dispose();
		}
	}
	
	public void print() {
		//print the schedule
		try {
//			JTable p = sched.getScheduleTable();
//			int h = p.getRowHeight();
//			Font f = p.getFont();
//			JTableHeader hh = p.getTableHeader();
//			p.setTableHeader(null);
//			p.setRowHeight(9);
//			p.setFont(new Font("Arial", Font.PLAIN, 8));
		    boolean complete = table.print(PrintMode.NORMAL);
//			p.setTableHeader(hh);
//		    p.setFont(f);
//		    p.setRowHeight(h);
		    if (complete) {
		        /* show a success message  */
		        
		    } else {
		        /*show a message indicating that printing was cancelled */
		    	JOptionPane.showMessageDialog(this,
						"Print Job was Cancelled");
		    }
		} catch (PrinterException pe) {
		    /* Printing failed, report to the user */
			JOptionPane.showMessageDialog(this,
					"Print Job Failed");
		    
		}
	}
	

	private void initData(Object[][] data) {
		Classes read, la, math, homeroom, specials;
		
		//figure out which classes this teacher teaches
		read = teach.getCls(Teachers.Type.READ);
		la = teach.getCls(Teachers.Type.LA);
		math = teach.getCls(Teachers.Type.MATH);
		homeroom = teach.getCls(Teachers.Type.HR);
		specials = teach.getCls(Teachers.Type.SP);
		
		
		List<Students> roster;
		//now put all the data we want into a table
		//I hope they like the format because i'm 
		//gonna hardcode all these values
		data[0][0] = "Name";	data[0][1] = teach.getName();
		data[0][3] = "Room";	data[0][4] = teach.getRoom();
		
		if (read != null) {
			data[2][0] = read.getFormalClassName();
			roster = read.getStudents();
			for (int i=0; i<roster.size(); i++)
				data[3+i][0] = roster.get(i);
		} else {
			data[2][0] = "No Reading Class";
		}
		if (la != null) {
			data[2][1] = la.getFormalClassName();
			roster = la.getStudents();
			for (int i=0; i<roster.size(); i++)
				data[3+i][1] = roster.get(i);
		} else {
			data[2][1] = "No Lang Arts Class";
		}
		if (math != null) {
			data[2][2] = math.getFormalClassName();
			roster = math.getStudents();
			for (int i=0; i<roster.size(); i++)
				data[3+i][2] = roster.get(i);
		} else {
			data[2][2] = "No Math Class";
		}
		if (homeroom != null) {
			data[2][3] = homeroom.getFormalClassName();
			roster = homeroom.getStudents();
			for (int i=0; i<roster.size(); i++)
				data[3+i][3] = roster.get(i);
		} else {
			data[2][3] = "No Homeroom Class";
		}
		if (specials != null) {
			data[2][4] = specials.getFormalClassName();
			roster = specials.getStudents();
			for (int i=0; i<roster.size(); i++)
				data[3+i][4] = roster.get(i);
		} else {
			data[2][4] = "No Specials Class";
		}
	}
}
