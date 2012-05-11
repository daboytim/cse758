import java.util.ArrayList;
import java.util.List;

public class ClassFactory {


	private static int maxCls = 20;
	private static int clsID = 1;

	private ClassFactory() {
		// empty
	}

	static List<Classes> mathClsLst = new ArrayList<Classes>();
	static List<Classes> laClsLst = new ArrayList<Classes>();
	static List<Classes> readClsLst = new ArrayList<Classes>();
	static List<Classes> homeroomClsLst = new ArrayList<Classes>();
	static List<Classes> specialClsLst = new ArrayList<Classes>();

	/**
	 * Check if std's behavior level fit with class.
	 * 
	 * @param std
	 * @param cls
	 * @return
	 */
	private static boolean BLfit(Students std, Classes cls) {
		if (cls.getBL3() == 1 && std.getBL() == 3) {
			return false;
		}
		if (cls.getBL2() == 3 && (std.getBL() == 2 || std.getBL() == 3)) {
			return false;
		}
		return true;
	}

	public static Classes createClass(String name, int lvl) {
		
		return new Classes(name, lvl, clsID++);
	}

	public static int getTotalClasses() {
		return mathClsLst.size()+laClsLst.size()+readClsLst.size();
	}

	public static int getTotalMath() {
		return mathClsLst.size();
	}

	public static int getTotalLA() {
		return laClsLst.size();
	}

	public static int getTotalRead() {
		return readClsLst.size();
	}

	/**
	 * Check if a student can be assigned to a particular class
	 * 
	 * @param std
	 * @param cls
	 * @return True if yes, false otherwise.
	 */
	public static boolean compatible(Students std, Classes cls) {
		if (!BLfit(std, cls)) {
			return false;
		}

		if (cls.getClsName().equals("math")) {
			if (cls.getLvl() == std.getMath()
					&& Math.abs(cls.getLowestAge() - std.getAge()) < 4.0
					&& cls.getTotal() < 5) {
				return true;
			} else {
				return false;
			}
		} else if (cls.getClsName().equals("la")) {
			if (cls.getLvl() == std.getLA()
					&& Math.abs(cls.getLowestAge() - std.getAge()) < 4.0
					&& cls.getTotal() < 5) {
				return true;
			} else {
				return false;
			}
		} else if (cls.getClsName().equals("read")) {
			if (cls.getLvl() == std.getRead()
					&& Math.abs(cls.getLowestAge() - std.getAge()) < 4.0
					&& cls.getTotal() < 5) {
				return true;
			} else {
				return false;
			}
		} else {
			System.err
					.println("A Classes object passed into ClassFactory.compatible method is labeled wrong class name");
			return false;
		}
	}

	/**
	 * Set max number of classes allowed. default is 15.
	 * 
	 * @param i
	 *            max number of cls allowed.
	 */
	public static void setMaxCls(int i) {
		maxCls = i;
		System.out.println("max number of cls set to:" + i);
	}

	/**
	 * Get max number of classes allowed.
	 * 
	 * @return number of max classes.
	 */
	public static int getMaxCls() {
		return maxCls;
	}

	/**
	 * Move a student from one class to another.
	 * 
	 * @param fromCls
	 * @param toCls
	 * @param std
	 * @throws StdClsCompatibleException
	 */
	public static void moveStd(Classes fromCls, Classes toCls, Students std)
			throws StdClsCompatibleException {
//throwing these exceptions defeats the purpose of manual modification
//		if (!fromCls.getClsName().equals(toCls.getClsName())) {
//			throw new StdClsCompatibleException(0);
//		} else if (toCls.getTotal() == 5) {
//			throw new StdClsCompatibleException(1);
//		} else if (Math.abs(fromCls.getLowestAge() - toCls.getLowestAge()) > 3.92) {
//			throw new StdClsCompatibleException(2);
//		} else if (!BLfit(std, toCls)) {
//			throw new StdClsCompatibleException(3);
//		} else if (!compatible(std, toCls)) {
//			throw new StdClsCompatibleException(4);
//		} else {
			toCls.addStd(std);
			fromCls.removeStd(std.getId());
//		}
	}
	
	
	public static int getMostClasses() {
		return Math.max(getTotalRead(), Math.max(getTotalMath(), getTotalLA()));
	}
	
	/**
	 * Even distribute any 2 classes with movable students so that we won't have one class of 5 std while one in another.
	 */
	public static void evenDistribute(){
		//TODO
	}
	
	public static Classes[] getStdClasses(Students std) {
		Classes[] classes = new Classes[3];
		//classes[0] - math / classes[1] - la / classes[2] - read
		for (Classes cls:mathClsLst) {
			if (cls.hasStudent(std)) {
				classes[0] = cls;
			}
		}
		for (Classes cls:laClsLst) {
			if (cls.hasStudent(std)) {
				classes[1] = cls;
			}
		}
		for (Classes cls:readClsLst) {
			if (cls.hasStudent(std)) {
				classes[2] = cls;
			}
		}
		return classes;
	}
	
	public static Classes getClsByID(int id){
		for(Classes cls: mathClsLst){
			if (cls.getClsID()==id) return cls;
		}
		for(Classes cls: laClsLst){
			if (cls.getClsID()==id) return cls;
		}
		for(Classes cls: readClsLst){
			if (cls.getClsID()==id) return cls;
		}
		for(Classes cls: homeroomClsLst){
			if (cls.getClsID()==id) return cls;
		}
		for(Classes cls: specialClsLst){
			if (cls.getClsID()==id) return cls;
		}
		System.err.println("Class id "+id+" does not exist.");
		return null;
	}
}
