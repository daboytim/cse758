import java.util.ArrayList;


public class Teachers implements Comparable<Teachers> {

	private String name;
	// tracks the classes a teacher can teach. K-8:1-9
	// preference: ordering most preferred in position 0. changes while scheduling.
	private ArrayList<Integer> preference; //TODO: math/read/la are most likely 3 separate sets
	// capable: order does not matter. does not change. 
	private ArrayList<Integer> capable;
	private int clsIDM;
	private int clsIDR;
	private int clsIDL;
	private int clsLvlM;
	private int clsLvlR;
	private int clsLvlL;
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
	
	public int getMathClsID(){
		return this.clsIDM;
	}
	
	public int getReadClsID(){
		return this.clsIDR;
	}
	
	public int getLAClsID(){
		return this.clsIDL;
	}

	public int getMathClsLvl(){
		return this.clsLvlM;
	}
	
	public int getReadClsLvl(){
		return this.clsLvlR;
	}
	
	public int getLAClsLvl(){
		return this.clsLvlL;
	}

	public void setReadCls(Integer clsLvl, Integer clsID){
		this.clsLvlR = clsLvl;
		this.clsIDR = clsID;
	}

	public void setLACls(Integer clsLvl, Integer clsID){
		this.clsLvlL = clsLvl;
		this.clsIDL = clsID;
	}
	
	public void setMathCls(Integer clsLvl, Integer clsID){
		this.clsLvlM = clsLvl;
		this.clsIDM = clsID;
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
	
	// take this out if math/reading/la have different preferences
	public void prefReset() {
		preference = new ArrayList<Integer>(capable);
	}
	
	public ArrayList<Integer> getPreference() {
		return preference;
	}
	
	public void setPreference(ArrayList<Integer> p) {
		preference = p;
		capable = new ArrayList<Integer>(p);
	}
	
	// returns n > 0, if this.teacher has more available classes compare to teacher
	@Override
	public int compareTo(Teachers teacher) {
		return this.availability()-teacher.availability();
	}
	
	@Override
	public String toString() {
		return name + " :: Room: " +room+ "\n\tMath: lv " + clsLvlM + ", ClassID: " + clsIDM +
		"\n\tRead: lv " + clsLvlR + ", ClassID: " + clsIDR +
		"\n\tLA  : lv " + clsLvlL + ", ClsssID: " + clsIDL;
	}

}
