import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Classes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String classname;
	private String formalClassName;
	private String tableClassName;
	private int lvl;
	private List<Students> students;
	private double lowestAge = 999;
	private int bl1 = 0;
	private int bl2 = 0;
	private int id;
	private Teachers teacher = null;
	private boolean hasTeacher = false;

	public Classes(String name, int lvl, int ID) {
		students = new ArrayList<Students>();
		this.classname = name;
		this.formalClassName = name + "-" + lvl + "_" + ID;
		if (name.equals("math")) {
			tableClassName = "Math " + lvl + " Sec. " + ID;
		} else if (name.equals("read")) {
			tableClassName = "Reading " + lvl + " Sec. " + ID;
		} else if (name.equals("la")) {
			tableClassName = "Lang. Arts " + lvl + " Sec. " + ID;
		} else if (name.equals("homeroom")) {
			tableClassName = "Homeroom " + lvl + " Sec. " + ID;
		} else if (name.equals("special")) {
			tableClassName = "Special " + lvl + " Sec. " + ID;
		}
		this.lvl = lvl;
		this.id = ID;
	}

	public void addStd(Students std) {
		if(students.size()==0) {
			if(this.classname.equals("math")) {
				this.lvl=std.getMath();
			}
			if(this.classname.equals("la")) {
				this.lvl=std.getLA();
			}
			if(this.classname.equals("read")) {
				this.lvl=std.getRead();
			}
		}
		students.add(std);
		
		
		std.assginCls(this.classname,this.id);
		
		if (std.getAge() < lowestAge) {
			this.lowestAge = (int) Math.floor(std.getAge());
		}
		if (std.getBL() == 3) {
			this.bl1++;
		} else if (std.getBL() == 2) {
			this.bl2++;
		}

	}

	public List<Students> getStudents() {
		return students;
	}

	public Students removeStd(int id) {
		for (Students std : this.students) {
			if (std.getId() == id) {
				std.resignCls(this.classname);
				this.students.remove(std);
				
				//update # of bl3 and bl2 std
				if(std.getBL()==1){
					this.bl1--;
				}else if(std.getBL()==2){
					this.bl2--;
				}
				//update new lowest age, if ever need to change.
				if (std.getAge() == lowestAge) {
					this.lowestAge = 999;
					for(Students s:this.students){
						if(s.getAge()<this.lowestAge){
							this.lowestAge=s.getAge();
						}
					}
				}
				return std;
			}
		}
		System.err.println("Student of ID:"+id+" is not in this class.");
		System.exit(-1);
		return null;
	}

	public boolean hasStudent(Students student) {
		for (Students std : students) {
			if (std.equals(student)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasTeacher()
	{
		return this.hasTeacher;
	}
	
	public Teachers getTeacher()
	{
		if(!this.hasTeacher())
			return null;
		return this.teacher;
	}
	
	public void setTeacher(Teachers t)
	{
		this.teacher = t;
		if (t != null) {
			if (classname.equals("math")) {
				teacher.setCls(lvl, id, Teachers.Type.MATH);
			} else if (classname.equals("read")) {
				teacher.setCls(lvl, id, Teachers.Type.READ);
			} else if (classname.equals("la")) {
				teacher.setCls(lvl, id, Teachers.Type.LA);
			} else if (classname.equals("homeroom")) {
				teacher.setCls(lvl, id, Teachers.Type.HR);
			} else if (classname.equals("special")) {
				teacher.setCls(lvl, id, Teachers.Type.SP);
			}
			this.hasTeacher = true;
		}
	}
	
	public Teachers removeTeacher()
	{
		if(!this.hasTeacher())
			return null;
		this.hasTeacher = false;
		if (classname.equals("math")) {
			teacher.setCls(-1, -1, Teachers.Type.MATH);
		} else if (classname.equals("read")) {
			teacher.setCls(-1, -1, Teachers.Type.READ);
		} else if (classname.equals("la")) {
			teacher.setCls(-1, -1, Teachers.Type.LA);
		} else if (classname.equals("homeroom")) {
			teacher.setCls(-1, -1, Teachers.Type.HR);
		} else if (classname.equals("special")) {
			teacher.setCls(-1, -1, Teachers.Type.SP);
		}
		return this.teacher;
	}

	public String getClsName() {
		return this.classname;
	}
	
	public String getFormalClassName() {
		this.formalClassName = classname + "-" + lvl + "_" + id;
		return this.formalClassName;
	}

	public int getLvl() {
		return this.lvl;
	}

	public int getTotal() {
		return this.students.size();
	}

	public double getLowestAge() {
		return this.lowestAge;
	}
	
	public double getHighestAge(){
		double highestAge = 0;
		for(int i = 0; i < students.size(); i++){
			double age = students.get(i).getAge();
			if (age > highestAge){
				highestAge = age;
			}
		}
		return highestAge;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		if (students.size() != 0) {
			for (Students std : students) {
				sb.append(std.toString() + '\n');
			}
			return sb.toString();
		} else {
			return "No students in this class.\n";
		}
	}

	/**
	 * Get number of behavior lvl3 students
	 * 
	 * @return Number of BL3 students
	 */
	public int getBL1() {
		return this.bl1;
	}

	/**
	 * Get number of behavior lvl2 students
	 * 
	 * @return Number of BL2 students
	 */
	public int getBL2() {
		return this.bl2;
	}

	/**
	 * Get class ID
	 * 
	 * @return class ID
	 */
	public int getClsID() {
		return this.id;
	}
	
	public void setClsLvl(int level) {
		this.lvl = level;
		
		if (classname.equals("math")) {
			tableClassName = "Math " + lvl + " Sec. " + id;
		} else if (classname.equals("read")) {
			tableClassName = "Reading " + lvl + " Sec. " + id;
		} else if (classname.equals("la")) {
			tableClassName = "Lang. Arts " + lvl + " Sec. " + id;
		} else if (classname.equals("homeroom")) {
			tableClassName = "Homeroom " + lvl + " Sec. " + id;
		} else if (classname.equals("special")) {
			tableClassName = "Special " + lvl + " Sec. " + id;
		}
		
	}

	public String getTableName() {
		if (classname.equals("math")) {
			tableClassName = "Math " + lvl + " Sec. " + id;
		} else if (classname.equals("read")) {
			tableClassName = "Reading " + lvl + " Sec. " + id;
		} else if (classname.equals("la")) {
			tableClassName = "Lang. Arts " + lvl + " Sec. " + id;
		} else if (classname.equals("homeroom")) {
			tableClassName = "Homeroom " + lvl + " Sec. " + id;
		} else if (classname.equals("special")) {
			tableClassName = "Special " + lvl + " Sec. " + id;
		}
		return tableClassName;
	}
}
