import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Students implements Comparable<Students> {
	
	private int id;
	private String fName;
	private String lName;
	private Date birthDate;
	private double numBDate;
	private int math;
	private int la;
	private int read;
	private static int totalStudents=0;
	private int bl;
	private int mathClsID;
	private int laClsID;
	private int readClsID;
	private int homeroomClsID;
	private int specialClsID;
	
	//TODO: Replace Age with BDate and calculate Age.
	//Largest age gap 3yrs 11 months
	
	public Students() {
		totalStudents++;
	}
	public Students(Integer id, String fName, String lName, Date bDate,int math,int la, int read, int bl){
		totalStudents+=1;
		this.id = id;
		this.fName=fName;
		this.lName = lName;
		this.birthDate = bDate;
		Calendar c = new GregorianCalendar();
		c.setTime(bDate);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		
		this.numBDate = year + (month/12.0);
		this.math=math;
		this.la=la;
		this.read=read;
		this.bl = bl;		
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getFirstName(){
		return this.fName;
	}
	
	public String getLastName(){
		return this.lName;
	}
	
	public Date getBirthDate() {
		return this.birthDate;
	}
	
	public double getAge(){
		Calendar c = new GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		double numDate = year + (month/12.0);
		return numDate - numBDate;
	}
	
	public int getMath(){
		return this.math;
	}
	
	public int getLA(){
		return this.la;
	}
	
	public int getRead(){
		return this.read;
	}
	
	
	public void setFirstName(String name){
		this.fName=name;
	}
	
	public void setLastName(String name){
		this.lName=name;
	}
	
	public void setBirthDate(Date bDate){
		this.birthDate = bDate;
		
		Calendar c = new GregorianCalendar();
		c.setTime(bDate);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		
		this.numBDate = year + (month/12.0);
		
	}
	
	public void setMath(int lvl){
		this.math=lvl;
	}
	
	public void setLA(int lvl){
		this.la=lvl;
	}
	
	public void setRead(int lvl){
		this.read=lvl;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public static int getTotal(){
		return totalStudents;
	}

	public double compareAge(Students std) {
		return this.getAge()-std.getAge();
	}
	
	@Override
	public int compareTo(Students std) {
		
		return (int)(this.getAge()*10000-std.getAge()*10000);
	}

	@Override
	public String toString(){
		return fName + " " + lName;
		//return "Student name:"+fName+" "+lName+"; age: "+Math.floor(getAge())+"; Math level: "+math+"; LA level: "+la+"; Reading level: "+read+"; Behavior level: "+this.getBL()+".";
		
	}
	
	/**
	 * Set behavior lvl
	 * @param i
	 * 		Std's behavior lvl
	 */
	public void setBL(int i){
		this.bl=i;
	}
	
	/**
	 * Get behavior lvl
	 * @return
	 * 		Std's behavior lvl
	 */
	public int getBL(){
		return this.bl;
	}
	
	/**
	 * Store which class this std is assigned to.
	 * 
	 * @param clsName subject name
	 * @param id class id
	 */
	public void assginCls(String clsName, int id) {
		if(clsName.equals("math")){
			this.mathClsID = id;
		}else if(clsName.equals("la")){
			this.laClsID=id;
		}else if(clsName.equals("read")){
			this.readClsID=id;
		}else if(clsName.equals("homeroom")){
			this.homeroomClsID = id;
		}else if(clsName.equals("special")){
			this.specialClsID = id;
		}else{
			System.err.println("This should never happen.");
		}
	}
	
	public Classes getMathCls(){
		for(Classes cls:ClassFactory.mathClsLst){
			if(cls.getClsID()==this.mathClsID)	return cls;
		}
		System.err.println("This should not happen.");
		return null;
	}
	public Classes getLACls(){
		for(Classes cls:ClassFactory.laClsLst){
			if(cls.getClsID()==this.laClsID)	return cls;
		}
		System.err.println("This should not happen.");
		return null;
	}
	public Classes getReadCls(){
		for(Classes cls:ClassFactory.readClsLst){
			if(cls.getClsID()==this.readClsID)	return cls;
		}
		System.err.println("This should not happen.");
		return null;
	}
	public Classes getHomeroomCls(){
		for(Classes cls:ClassFactory.homeroomClsLst){
			if(cls.getClsID()==this.homeroomClsID)	return cls;
		}
		System.err.println("This should not happen.");
		return null;
	}
	public Classes getSpecialCls(){
		for(Classes cls:ClassFactory.specialClsLst){
			if(cls.getClsID()==this.specialClsID)	return cls;
		}
		System.err.println("This should not happen.");
		return null;
	}
}
