import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class MainFrame implements ActionListener, MouseListener{
	static JTabbedPane tabbedPane;
	static JFrame frame;
	StudentController sc;
	TeacherController tc;
	StudentTable sTab;
	TeacherTable tTab;
	JScrollPane panel1, panel2, panel3;
	ScheduleDisplay sched;
	StudentDB students;
	TeacherDB teachers;
	Menu menu;
	JPopupMenu rightClickMenu, rightClickMenu2;
	JMenuItem editItem, editItem2;

	public MainFrame() {
		students = new StudentDB();
		teachers = new TeacherDB();
		frame = new JFrame();
		//create the right click menu
		rightClickMenu = new JPopupMenu();
		editItem = new JMenuItem("Edit");
		editItem.addActionListener(this);
		rightClickMenu.add(editItem);
		//this one is for the teacher tab
		rightClickMenu2 = new JPopupMenu();
		editItem2 = new JMenuItem("Edit");
		editItem2.addActionListener(this);
		rightClickMenu2.add(editItem2);
		update();
	}
	
	public void update() {
		frame.setVisible(false); // Hide the old frame, this is probably NOT efficient
		frame = new JFrame();
		frame.validate();
		frame.setState(Frame.NORMAL);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		frame.setSize(dimension);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		tabbedPane = new JTabbedPane();

		sTab = new StudentTable(frame, students);
		panel1 = new JScrollPane(sTab.getStudentTable());
		tabbedPane.addTab("Student Entry", panel1);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		tTab = new TeacherTable(frame, teachers);
		tTab.getTeacherTable().addMouseListener(this);
		panel2 = new JScrollPane(tTab.getTeacherTable());
		tabbedPane.addTab("Teacher Entry", panel2);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		sc = new StudentController(frame, students);
		tc = new TeacherController(frame, teachers);

		menu = new Menu(this, students, frame, sc, teachers, tc);
		frame.setJMenuBar(menu.getMenu());

		sched = new ScheduleDisplay();
		sched.getScheduleTable().addMouseListener(this);
		panel3 = new JScrollPane(sched.getScheduleTable());
		tabbedPane.addTab("Schedule", panel3);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		// Add the tabbed pane to this panel.
		frame.add(tabbedPane);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		frame.setVisible(true);
	}

	//All actions that update the table view need to happen here
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		JFileChooser chooser = new JFileChooser();
		if (obj.equals(Menu.sOpen)) {
			// use chooser.getSelectedFile() to get file
			// Some code here to parse or call a parser
			if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
				sc.readFile(chooser.getSelectedFile());
			sTab.update();
			tabbedPane.setSelectedIndex(0);

			
		} else if (obj.equals(Menu.tOpen)) {
			// use chooser.getSelectedFile() to get file
			// Some code here to parse or call a parser
			if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
				tc.readFile(chooser.getSelectedFile());
			tTab.update();
			tabbedPane.setSelectedIndex(1);

		} else if (obj.equals(Menu.schedulize)) {
			// Code here to call schedule algorithm and display schedules
			Schedulizer.genSchedule(students);
			sched.update();
			tabbedPane.setSelectedIndex(2);
		} else if (obj.equals(Menu.assign)) {
			// TODO: Code here to call schedule algorithm and display schedules
			ScheduleTeachers.assign(teachers);
			sched.update();
			tabbedPane.setSelectedIndex(2);
		} else if (obj.equals(editItem)) {
			showManualMod();
			//sched.update();
		} else if (obj.equals(editItem2)) {
			showTeacherMod();
			//sched.update();
		}
		tabbedPane.revalidate();
		tabbedPane.setVisible(false);
		tabbedPane.repaint();
		tabbedPane.setVisible(true);
		//update();
	}
	
	private void showManualMod() {
		int x, y;
		x = sched.getScheduleTable().getSelectedColumn();
		y = sched.getScheduleTable().getSelectedRow();
		//make sure x and y correspond to a student
		if (x > 0 && y >= 0) {
			Object std = sched.getScheduleTable().getValueAt(y, x);
			if (!std.equals("")) {
				new ManualModFrame((Students)std, sched);
			}
		}
	}
	
	private void showTeacherMod() {
		int x, y;
		x = tTab.getTeacherTable().getSelectedColumn();
		y = tTab.getTeacherTable().getSelectedRow();
		if (x >= 0 && y >= 0) {
			Object teach = tTab.getTeacherTable().getValueAt(y, 0);
			if (!teach.equals("")) {
				new TeacherModFrame((Teachers)teach);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() == 2) {
			if (e.getSource() == sched.getScheduleTable()) {
				int x, y;
				x = sched.getScheduleTable().getSelectedColumn();
				y = sched.getScheduleTable().getSelectedRow();
				if (x > 0 && y >= 0) {
					Object std = sched.getScheduleTable().getValueAt(y, x);
					if (!std.equals("")) {
						new StudentScheduleFrame((Students)std);
					}
				}
			} else if (e.getSource() == tTab.getTeacherTable()) {
				int x, y;
				x = tTab.getTeacherTable().getSelectedColumn();
				y = tTab.getTeacherTable().getSelectedRow();
				if (x == 0) {
					Object teach = tTab.getTeacherTable().getValueAt(y, x);
					if (!teach.equals("")) {
						new TeacherScheduleFrame((Teachers)teach);
					}
				}
			}
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.isPopupTrigger()) {
			if (e.getSource() == sched.getScheduleTable()) {
				rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
			} else if (e.getSource() == tTab.getTeacherTable()) {
				rightClickMenu2.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}
}
