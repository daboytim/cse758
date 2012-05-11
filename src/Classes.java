import java.util.ArrayList;
import java.util.List;

public class Classes {
	private String classname;
	private int lvl;
	private List<Students> students;
	private int lowestAge = 999;
	private int bl3 = 0;
	private int bl2 = 0;
	private int id;

	public Classes(String name, int lvl, int ID) {
		students = new ArrayList<Students>();
		this.classname = name;
		this.lvl = lvl;
		this.id = ID;
	}

	public void addStd(Students std) {
		students.add(std);
		if (std.getAge() < lowestAge) {
			this.lowestAge = (int) Math.floor(std.getAge());
		}
		if (std.getBL() == 3) {
			this.bl3++;
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
				this.students.remove(std);
				return std;
			}
		}
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

	public String getClsName() {
		return this.classname;
	}

	public int getLvl() {
		return this.lvl;
	}

	public int getTotal() {
		return this.students.size();
	}

	public int getLowestAge() {
		return this.lowestAge;
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
	public int getBL3() {
		return this.bl3;
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

}
