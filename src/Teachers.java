import java.util.ArrayList;


public class Teachers {

	public enum Type {MATH, READ, LA}
	private String name;
	// tracks the classes a teacher can teach. K-8:1-9
	// preference: ordering most preferred in position 0. changes while scheduling.
	private ArrayList<Integer> mathPreference;
	private ArrayList<Integer> readPreference;
	private ArrayList<Integer> laPreference;
	// capable: order does not matter. does not change. 
	public ArrayList<Integer> capableM;
	public ArrayList<Integer> capableR;
	public ArrayList<Integer> capableL;
	private int clsIDM;
	private int clsIDR;
	private int clsIDL;
	private int clsLvlM;
	private int clsLvlR;
	private int clsLvlL;
	private int roomM;
	private int roomL;
	private int roomR;
	
	public Teachers(String name, ArrayList<Integer> math, ArrayList<Integer> read, ArrayList<Integer> la){
		this.name = name;
		this.mathPreference = math;
		this.capableM = new ArrayList<Integer>(math);
		this.readPreference = read;
		this.capableR = new ArrayList<Integer>(read);
		this.laPreference = la;
		this.capableL = new ArrayList<Integer>(la);
	}
	
	public Teachers(String name) {
		this.name = name;
	}
	
	public Teachers() {
		
	};
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String n){
		name = n;
	}
	
	public int getClsID(Type t){
		switch(t) {
		case MATH:
			return this.clsIDM;

		case READ:
			return this.clsIDR;
			
		case LA:
			return this.clsIDL;	
			
		default:
			return -1;
		}
	}

	public int getClsLvl(Type t){
		switch(t) {
		case MATH:
			return this.clsLvlM;

		case READ:
			return this.clsLvlR;
			
		case LA:
			return this.clsLvlL;
			
		default:
			return -1;			
		}	
	}

	public void setCls(Integer clsLvl, Integer clsID, Type t){
		switch(t) {
		case MATH:
		{
			this.clsLvlM = clsLvl;
			this.clsIDM = clsID;
		}
		case READ:
		{
			this.clsLvlR = clsLvl;
			this.clsIDR = clsID;
		}	
		case LA:
		{
			this.clsLvlL = clsLvl;
			this.clsIDL = clsID;
		}
		}
	}
	
	public int firstPref(Type t){
		switch(t) {
		case MATH:
		{
			if(this.mathPreference.size() <= 0)
				return -1;
			return this.mathPreference.get(0);
		}
		case READ:
		{
			if(this.readPreference.size() <=0)
				return -1;
			return this.readPreference.get(0);
		}
		case LA:
		{
			if(this.laPreference.size() <=0)
				return -1;
			return this.laPreference.get(0);
		}
		default:
			return -1;
		}
	}
	
	// changes/removes preference;
	public void changePref(Integer cls, Type t){
		switch(t) {
		case MATH:
			this.mathPreference.remove(cls);
		case READ:
			this.readPreference.remove(cls);
		case LA:
			this.laPreference.remove(cls);
		}
	}
	
	// number of classes available to teach
	public int availability(Type t){
		switch(t) {
		case MATH:
			return this.mathPreference.size();
		case READ:
			return this.readPreference.size();
		case LA:
			return this.laPreference.size();
		default:
			return -1;
		}
	}
	
	public boolean canTeach(Integer cls, Type t){
		switch(t) {
		case MATH:
			return this.capableM.contains(cls);
		case READ:
			return this.capableR.contains(cls);
		case LA:
			return this.capableL.contains(cls);
		default:
			return false;
		}
	}
	
	public void setRoom(int num, Type t){
		switch(t) {
		case MATH:
			this.roomM = num;
		case READ:
			this.roomR = num;
		case LA:
			this.roomL = num;
		}
	}
	
	public int getRoom(Type t){
		switch(t) {
		case MATH:
			return this.roomM;
		case READ:
			return this.roomR;
		case LA:
			return this.roomL;
		default:
			return -1;
		}
	}
	
	public ArrayList<Integer> getPreference(Type t) {
		switch(t) {
		case MATH:
			return this.mathPreference;
		case READ:
			return this.readPreference;
		case LA:
			return this.laPreference;
		default:
			return null;
		}
	}
	
	public void setPreference(ArrayList<Integer> p, Type t) {
		switch(t) {
		case MATH:
		{
			this.mathPreference = p;
			this.capableM = new ArrayList<Integer>(p);
		}
		case READ:
		{
			this.readPreference = p;
			this.capableR = new ArrayList<Integer>(p);
		}
		case LA:
		{
			this.laPreference = p;
			this.capableR = new ArrayList<Integer>(p);
		}
		}
	}
	
	// returns n > 0, if this.teacher has more available classes compare to teacher
	public int compareTo(Teachers teacher, Type t) {
		return this.availability(t)-teacher.availability(t);
	}
	
	@Override
	public String toString() {
		return name + "\n\tMath: lv " + clsLvlM + ", ClassID: " + clsIDM + ", Room: " + roomM +
		"\n\tRead: lv " + clsLvlR + ", ClassID: " + clsIDR + ", Room: " + roomR +
		"\n\tLA  : lv " + clsLvlL + ", ClsssID: " + clsIDL + ", Room: " + roomL;
	}

}
