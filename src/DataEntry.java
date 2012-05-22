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

public class DataEntry {
	/*
	 * static JFrame frame; static StudentDB students;
	 */

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
				MainFrame mf = new MainFrame();
			}
		});

	}

	/**
	 * Save object on disk.
	 * 
	 * @param mf
	 * @param fileName
	 */
	public void saveObject(MainFrame mf, String fileName) {
		try {
			// use buffering
			OutputStream file = new FileOutputStream(fileName);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(mf);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			System.err.println("Unable to write file to disk");
		}
	}

	/**
	 * Load mf object from disk.
	 * 
	 * @param fileName
	 * @return loaded mf object
	 */
	public MainFrame loadObject(String fileName) {
		MainFrame mf=null;
		try {
			// use buffering
			InputStream file = new FileInputStream(fileName);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				// deserialize
				mf = (MainFrame) input.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			System.err.println("Cannot load file from disk.");
		}
		return mf;
	}
}
