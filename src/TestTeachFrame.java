	import javax.swing.JFrame;
	
public class TestTeachFrame {
	static JFrame frame;
    static TeacherDB teachers;

	     /* 
	      * Main
	      */   
	 
	    public static void main(String[] args) {
	    	teachers = new TeacherDB();

	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new TeacherFrame(frame, teachers);
	            }
	        });
	    }
}
