import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SchedulingTool {
	static MainFrame mf;

	/*
	 * Main
	 */

	public static void main(String[] args) {
		// students = new StudentDB(frame);

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// new StudentFrame(frame, students);
				mf = new MainFrame();
			}
		});

	}

	/**
	 * Load mf object from disk.
	 * 
	 * @param fileName
	 * @return loaded mf object
	 */
	public static void loadObject(String fileName) {
		mf = null;
		try {
			// use buffering
			InputStream file = new FileInputStream(fileName);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				// deserialize
				mf = (MainFrame) input.readObject();
				for (int i = 0; i < mf.students.getSize(); i ++) {
					System.out.println(mf.students.getStudents().get(i));
				}
				mf.update();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			System.err.println("Cannot load file from disk.");
			ex.printStackTrace();
		}
	}
}
