import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MenuController implements ActionListener, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClassFactory clsFac;

	public MenuController(ClassFactory cf) {
		clsFac = cf;
	}

	public void actionPerformed(ActionEvent event) {
		Object obj = event.getSource();
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(chooser.getAcceptAllFileFilter());
		if (obj.equals(Menu.exit1)) {
			// Probably want to do checking here if user wants to save
			System.exit(0);
		} else if (obj.equals(Menu.pref1)) {
			String str = JOptionPane.showInputDialog(null,
					"Enter max number of classes : ",
					"Set Max number of Classes", 1);
			try {
				if (str != null) {
					clsFac.setMaxCls(Integer.parseInt(str));
					JOptionPane.showMessageDialog(null,
							"Max number of classes successfully set to"
									+ clsFac.getMaxCls(), "Success", 1);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please input an integer.",
						"Warning", 1);
			}
		} else if (obj.equals(Menu.copy)) {

		} else if (obj.equals(Menu.paste)) {

		} 
		// end of kai's edit
	}

}
