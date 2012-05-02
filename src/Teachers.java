import java.util.ArrayList;


public class Teachers implements Comparable<Teachers> {

	private String name;
	// tracks the classes a teacher can teach. K-8:1-9
	// ordering most preferred in position 0. changes while scheduling.
	private ArrayList<Integer> preference; //TODO: math/read/la are most likely 3 separate sets
	// order does not matter. does not change. 
	private ArrayList<Integer> capable;
	private int clsID;
	private int clsLvl;
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
	
	public int getClsID(){
		return this.clsID;
	}

	public int getClsLvl(){
		return this.clsLvl;
	}
	
	public void setCls(Integer clsLvl, Integer clsID){
		this.clsLvl = clsLvl;
		this.clsID = clsID;
	}
	
	public int firstPref(){
		if(this.preference.size() <= 0)
			return -1;
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
	
	@Override
	public String toString() {
		return name + " :: Class: " + clsLvl + ", ClassID: " + clsID + ", Room: " + room;
	}

}
