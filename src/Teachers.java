import java.util.ArrayList;


public class Teachers implements Comparable<Teachers> {

	private String name;
	// tracks the classes a teacher can teach. K-8:1-9
	// ordering most preferred in position 0. changes while scheduling.
	private ArrayList<Integer> preference;
	// order does not matter. does not change. 
	private ArrayList<Integer> capable;
	private int cls;
	private int room;
	
	public Teachers(String name, ArrayList<Integer> classes){
		this.name = name;
		this.preference = classes;
		this.capable = new ArrayList<Integer>(classes);
	}
	
	public Teachers(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String n){
		name = n;
	}
	
	public int getCls(){
		return this.cls;
	}

	public void setCls(Integer cls){
		this.cls = cls;
	}
	
	public int firstPref(){
		return this.preference.get(0);
	}
	
	public void changePref(Integer cls){
		this.preference.remove(cls);
	}
	
	// number of classes available to teach
	public int availability(){
		return this.preference.size();
	}
	
	public boolean canTeach(Integer cls){
		return this.capable.contains(cls);
	}
	
	public void setRoom(int num){
		this.room = num;
	}
	
	public int getRoom(){
		return room;
	}
	
	public ArrayList<Integer> getPreference() {
		return preference;
	}
	
	public void setPreference(ArrayList<Integer> p) {
		preference = p;
	}
	
	// returns n > 0, if this.teacher has more available classes compare to teacher
	@Override
	public int compareTo(Teachers teacher) {
		return this.availability()-teacher.availability();
	}

}
