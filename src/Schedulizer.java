import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Schedulizer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// public static List<Students> unluckyStd;

	public static void genSchedule(StudentDB stds, ClassFactory clsFac) {

		List<Students> std = stds.getStudents();
		clsFac.mathClsLst.clear();
		clsFac.laClsLst.clear();
		clsFac.readClsLst.clear();
		clsFac.homeroomClsLst.clear();
		clsFac.specialClsLst.clear();
		clsFac.unlucky.clear();

		/*
		 * Scanner in = new Scanner(System.in);
		 * 
		 * System.out.print("Input student info: name:"); String name =
		 * in.nextLine();
		 * 
		 * while (!name.equals("-1")) {
		 * 
		 * System.out.print("                    age: "); int age =
		 * in.nextInt(); System.out.print("                    Math level: ");
		 * int math = in.nextInt();
		 * System.out.print("                    LA level: "); int la =
		 * in.nextInt();
		 * System.out.print("                    Reading level: "); int read =
		 * in.nextInt(); std.add(new Students(name, age, math, la, read));
		 * in.nextLine(); System.out.print("Input student info: name:"); name =
		 * in.nextLine(); }
		 * 
		 * System.out.println("\nTotal students: " + std.size());
		 */

		// sort by age.
		Collections.sort(std);

		// print sorted list.can be deleted for final product
		for (int i = 0; i < std.size(); i++) {
			System.out.println(std.get(i).toString());
		}

		Classes mathClass = null, laClass = null, readClass = null;
		boolean foundCls;

		for (int i = 0; i < std.size(); i++) {

			foundCls = false;

			// schedulize math class
			// check if there is already a class compatible with current student
			for (Classes cls : clsFac.mathClsLst) {
				if (clsFac.compatible(std.get(i), cls)) {
					mathClass = cls;
					mathClass.addStd(std.get(i));
					foundCls = true;
					break;
				}
			}

			// no compatible cls, check if we can create a new class, remember
			// there is a limit on max#ofCls
			if (!foundCls) {
				if (clsFac.getTotalMath() < clsFac.getMaxCls()) {
					mathClass = clsFac.createClass("math", std.get(i)
							.getMath());
					clsFac.mathClsLst.add(mathClass);
					mathClass.addStd(std.get(i));
					foundCls = true;
				} else {
					std.get(i).setWlReason(clsFac.noFitMath);
					clsFac.unlucky.add(std.get(i));
					continue;// those unlucky students...
				}
			}

			foundCls = false;
			// schedulize LA class
			for (Classes cls : clsFac.laClsLst) {
				if (clsFac.compatible(std.get(i), cls)) {
					laClass = cls;
					laClass.addStd(std.get(i));
					foundCls = true;
					break;
				}
			}

			if (!foundCls) {
				if (clsFac.getTotalLA() < clsFac.getMaxCls()) {
					laClass = clsFac
							.createClass("la", std.get(i).getLA());
					clsFac.laClsLst.add(laClass);
					laClass.addStd(std.get(i));
					foundCls = true;
				} else {
					std.get(i).setWlReason(clsFac.noFitLA);
					clsFac.unlucky.add(std.get(i));
					// roll back...

					mathClass.removeStd(std.get(i).getId());
					if (mathClass.getTotal() == 0) {
						clsFac.mathClsLst.remove(mathClass);
					}

					continue;// those unlucky students...
				}
			}

			foundCls = false;
			// schedulize reading class
			for (Classes cls : clsFac.readClsLst) {
				if (clsFac.compatible(std.get(i), cls)) {
					readClass = cls;
					readClass.addStd(std.get(i));
					foundCls = true;
					break;
				}
			}
			if (!foundCls) {
				if (clsFac.getTotalRead() < clsFac.getMaxCls()) {
					readClass = clsFac.createClass("read", std.get(i)
							.getRead());
					clsFac.readClsLst.add(readClass);
					readClass.addStd(std.get(i));
					foundCls = true;
				} else {
					std.get(i).setWlReason(clsFac.noFitRead);
					clsFac.unlucky.add(std.get(i));
					// roll back...

					mathClass.removeStd(std.get(i).getId());
					if (mathClass.getTotal() == 0) {
						clsFac.mathClsLst.remove(mathClass);
					}

					laClass.removeStd(std.get(i).getId());
					if (laClass.getTotal() == 0) {
						clsFac.laClsLst.remove(laClass);
					}

					continue;// those unlucky students...
				}
			}

		}

		// distribute class size evenly
		clsFac.evenDistribute();

		// sort unlucky list by incoming order.
		Collections.sort(clsFac.unlucky, new Comparator<Students>() {
			public int compare(Students std1, Students std2) {
				return std1.getWL() - std2.getWL();
			}
		});

		// make clone from math class list to homeroom and special classes.
		for (Classes cls : clsFac.mathClsLst) {

			Classes hrCls = clsFac.createClass("homeroom", cls.getLvl());

			Classes spCls = clsFac.createClass("special", cls.getLvl());

			for (Students std2 : cls.getStudents()) {
				hrCls.addStd(std2);
				spCls.addStd(std2);
			}
			clsFac.homeroomClsLst.add(hrCls);
			clsFac.specialClsLst.add(spCls);
		}

		System.out.println("*********Results************");
		System.out.println("There are " + clsFac.mathClsLst.size()
				+ " classes for math:");
		for (Classes cls : clsFac.mathClsLst) {
			System.out.println("=============" + cls.getClsName() + " "
					+ cls.getClsID());
			System.out.println(cls.toString());
		}

		System.out.println("\nThere are " + clsFac.laClsLst.size()
				+ " classes for LA:");
		for (Classes cls : clsFac.laClsLst) {
			System.out.println("=============" + cls.getClsName() + " "
					+ cls.getClsID());
			System.out.println(cls.toString());
		}

		System.out.println("\nThere are " + clsFac.readClsLst.size()
				+ " classes for reading:");
		for (Classes cls : clsFac.readClsLst) {
			System.out.println("=============" + cls.getClsName() + " "
					+ cls.getClsID());
			System.out.println(cls.toString());
		}

		System.out.println("\nThere are " + clsFac.homeroomClsLst.size()
				+ " classes for homeroom:");
		for (Classes cls : clsFac.homeroomClsLst) {
			System.out.println("=============" + cls.getClsName() + " "
					+ cls.getClsID());
			System.out.println(cls.toString());
		}

		System.out.println("\nThere are " + clsFac.specialClsLst.size()
				+ " classes for special:");
		for (Classes cls : clsFac.specialClsLst) {
			System.out.println("=============" + cls.getClsName() + " "
					+ cls.getClsID());
			System.out.println(cls.toString());
		}

		System.out.println("*******Unlucky Students********");
		for (Students stdss : clsFac.unlucky) {
			System.out.println(stdss.toString() + "WL ID:" + stdss.getWL()+" Reason:"+stdss.getWlReason());
		}

	}

	public static void addNewStd(Students std, ClassFactory clsFac) throws StdClsCompatibleException {
		boolean foundCls = false;
		// try to add to math class
		for (Classes cls : clsFac.mathClsLst) {
			if (clsFac.compatible(std, cls)) {
				cls.addStd(std);
				foundCls = true;
				break;
			}
		}
		if (!foundCls) {
			clsFac.unlucky.add(std);
			std.setWlReason(clsFac.noFitMath);
			Collections.sort(clsFac.unlucky, new Comparator<Students>() {
				public int compare(Students std1, Students std2) {
					return std1.getWL() - std2.getWL();
				}
			});
			throw new StdClsCompatibleException(5);
		}

		// try to add to la class
		foundCls = false;
		for (Classes cls : clsFac.laClsLst) {
			if (clsFac.compatible(std, cls)) {
				cls.addStd(std);
				foundCls = true;
				break;
			}
		}
		if (!foundCls) {
			clsFac.unlucky.add(std);
			std.setWlReason(clsFac.noFitLA);
			Collections.sort(clsFac.unlucky, new Comparator<Students>() {
				public int compare(Students std1, Students std2) {
					return std1.getWL() - std2.getWL();
				}
			});
			throw new StdClsCompatibleException(5);
		}

		// try to add to read cls
		foundCls = false;
		for (Classes cls : clsFac.readClsLst) {
			if (clsFac.compatible(std, cls)) {
				cls.addStd(std);
				foundCls = true;
				break;
			}
		}
		if (!foundCls) {
			clsFac.unlucky.add(std);
			std.setWlReason(clsFac.noFitRead);
			Collections.sort(clsFac.unlucky, new Comparator<Students>() {
				public int compare(Students std1, Students std2) {
					return std1.getWL() - std2.getWL();
				}
			});
			throw new StdClsCompatibleException(5);
		}

		// try to add to homeroom cls
		foundCls = false;
		for (Classes cls : clsFac.homeroomClsLst) {
			if (clsFac.compatible(std, cls)) {
				cls.addStd(std);
				foundCls = true;
				break;
			}
		}
		if (!foundCls) {
			clsFac.unlucky.add(std);
			Collections.sort(clsFac.unlucky, new Comparator<Students>() {
				public int compare(Students std1, Students std2) {
					return std1.getWL() - std2.getWL();
				}
			});
			throw new StdClsCompatibleException(5);
		}

		// try to add to special cls
		foundCls = false;
		for (Classes cls : clsFac.specialClsLst) {
			if (clsFac.compatible(std, cls)) {
				cls.addStd(std);
				foundCls = true;
				break;
			}
		}
		if (!foundCls) {
			clsFac.unlucky.add(std);
			Collections.sort(clsFac.unlucky, new Comparator<Students>() {
				public int compare(Students std1, Students std2) {
					return std1.getWL() - std2.getWL();
				}
			});
			throw new StdClsCompatibleException(5);
		}

		System.out.println("*********Results************");
		System.out.println("There are " + clsFac.mathClsLst.size()
				+ " classes for math:");
		for (Classes cls : clsFac.mathClsLst) {
			System.out.println("=============" + cls.getClsName() + " "
					+ cls.getClsID());
			System.out.println(cls.toString());
		}

		System.out.println("\nThere are " + clsFac.laClsLst.size()
				+ " classes for LA:");
		for (Classes cls : clsFac.laClsLst) {
			System.out.println("=============" + cls.getClsName() + " "
					+ cls.getClsID());
			System.out.println(cls.toString());
		}

		System.out.println("\nThere are " + clsFac.readClsLst.size()
				+ " classes for reading:");
		for (Classes cls : clsFac.readClsLst) {
			System.out.println("=============" + cls.getClsName() + " "
					+ cls.getClsID());
			System.out.println(cls.toString());
		}

		System.out.println("\nThere are " + clsFac.homeroomClsLst.size()
				+ " classes for homeroom:");
		for (Classes cls : clsFac.homeroomClsLst) {
			System.out.println("=============" + cls.getClsName() + " "
					+ cls.getClsID());
			System.out.println(cls.toString());
		}

		System.out.println("\nThere are " + clsFac.specialClsLst.size()
				+ " classes for special:");
		for (Classes cls : clsFac.specialClsLst) {
			System.out.println("=============" + cls.getClsName() + " "
					+ cls.getClsID());
			System.out.println(cls.toString());
		}

		System.out.println("*******Unlucky Students********");
		for (Students stdss : clsFac.unlucky) {
			System.out.println(stdss.toString() + "WL ID:" + stdss.getWL()+" Reason:"+stdss.getWlReason());
		}

	}

	public void runTest(ClassFactory clsFac) {
		System.out
				.println("Running automatic testing for generated schedules:");

		// ==============================
		System.out
				.println("Testing on restriction on total number of classes:");
		System.out.println("	Max # of classes: " + clsFac.getMaxCls());
		System.out.println("	# of all math classes: "
				+ clsFac.getTotalMath());
		System.out.println("	# of all LArt classes: "
				+ clsFac.getTotalLA());
		System.out.println("	# of all reading classes: "
				+ clsFac.getTotalRead());
		boolean result = clsFac.getTotalMath() <= clsFac
				.getMaxCls()
				&& clsFac.getTotalLA() <= clsFac.getMaxCls()
				&& clsFac.getTotalRead() <= clsFac.getMaxCls();

		if (result) {
			System.out.println("	Result: Pass");
		} else {
			System.out.println("	Result: Fail");
			System.exit(0);
		}
		// ==============================
		System.out
				.println("Testing on restriction on max number of student per class:");
		System.out.println("	Max # of students per class: 5");
		result = true;
		for (Classes cls : clsFac.mathClsLst) {
			if (cls.getTotal() > 5) {
				result = false;
				break;
			}
		}
		if (result) {
			System.out.println("\tAll math classes passed.");
		} else {
			System.out.println("\tAt least one math class failed");
			System.exit(0);
		}
		for (Classes cls : clsFac.laClsLst) {
			if (cls.getTotal() > 5) {
				result = false;
				break;
			}
		}
		if (result) {
			System.out.println("\tAll LA classes passed.");
		} else {
			System.out.println("\tAt least one LA class failed");
			System.exit(0);
		}

		for (Classes cls : clsFac.readClsLst) {
			if (cls.getTotal() > 5) {
				result = false;
				break;
			}
		}
		if (result) {
			System.out.println("\tAll reading classes passed.");
		} else {
			System.out.println("\tAt least one reading class failed");
			System.exit(0);
		}
		// ========================
		System.out.println("Testing on age restriction:");
		// ========================
		System.out.println("Testing on behavior level restriction:");
		// ========================
		System.out
				.println("Testing on whether each class has only one level of students:");
		// =======================
		System.out
				.println("Testing on unlucky students are truly unlucky, and cannot be assgined to any class:");
		// ======================

	}

}
