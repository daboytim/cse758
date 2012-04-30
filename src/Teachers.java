import java.util.ArrayList;


public class Teachers implements Comparable<Teachers> {

	private int id;
	private String name;
	// tracks the classes a teacher can teach. ordering most preferred in position 0.
	private ArrayList<Integer> preference;
	private int cls;
	
	public Teachers(int id, String name, ArrayList<Integer> preference){
		this.id = id;
		this.name = name;
		this.preference = preference;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getCls(){
		return this.cls;
	}
	// feels unnecessary along with getCls() when can just use firstPref()
	public void setCls(){
		this.cls = this.preference.get(0);
		for(int i = 1; this.preference.size() > 1; this.preference.remove(i));
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
	
	// returns n > 0, if this.teacher has more available classes compare to teacher
	@Override
	public int compareTo(Teachers teacher) {
		return this.availability()-teacher.availability();
	}

}
