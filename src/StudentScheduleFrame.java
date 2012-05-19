import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;
import javax.swing.JTable;
import javax.swing.JButton;


public class StudentScheduleFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 644L;
	private static final int numRows = 8;
	private static final int numColumns = 2;
	private JPanel contentPane;
	private JButton btnPrint;
	private JButton btnClose;
	private Students std;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public StudentScheduleFrame(Students student) {
		std = student;
		Object[] columnNames = new Object[numColumns];
		for (int i=0; i<numColumns; i++) {
			columnNames[i] = "";
		}
		Object[][] data = new Object[numRows][numColumns];
		initData(data);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 200, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle(std.getFirstName() + " " + std.getLastName() + "'s Schedule");

		table = new JTable(data, columnNames);
		table.setRowSelectionAllowed(false);
		TableColumnModel cm =table.getColumnModel();
		cm.getColumn(0).setPreferredWidth(100);
		cm.getColumn(1).setPreferredWidth(100);
		
		
		
		contentPane.add(table, BorderLayout.CENTER);
		
		JPanel btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.SOUTH);
		
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(this);
		btnPanel.add(btnPrint);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		btnPanel.add(btnClose);
		
		setPreferredSize(new Dimension(200,200));
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
	
	private void print() {
		//print the schedule
	}
	
	private void initData(Object[][] data) {
		data[0][0] = "ID";		data[0][1] = std.getId();
		data[1][0] = "Name";	data[1][1] = std.getFirstName()+" "+std.getLastName();
		data[2][0] = "";		data[2][1] = "";
		data[3][0] = "Homeroom";data[3][1] = std.getHomeroomCls();
		data[4][0] = "Reading";	data[4][1] = std.getReadCls().getClsName();
		data[5][0] = "Lang Arts";data[5][1] = std.getLACls().getClsName();
		data[6][0] = "Math";	data[6][1] = std.getMathCls().getClsName();
		data[7][0] = "Specials";data[7][1] = std.getSpecialCls().getClsName();
	}

}
