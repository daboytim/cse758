import java.util.ArrayList;
import java.util.List;


public class ScheduleTeachers {
	public static void assign(TeacherDB tch)
	{
		Teachers.Type type;
		ArrayList<Teachers> teachers = tch.getTeachers();
		ArrayList<Teachers> assigned = new ArrayList<Teachers>();
		ArrayList<Teachers> unlucky = new ArrayList<Teachers>();
		ArrayList<Integer> classes = new ArrayList<Integer>();
		List<Classes> clsList;
		int clsNum;
		
		/* === Algorithm ===
		 * find teacher with minimum classes able to teach
		 * look through the preference list of classes
		 *     check each class on list to see if any class fits
		 * 	   assigns the first classes on that list if fits
		 * if no class fits on the preference list
		 *     mark teacher as unlucky
		 *     
		 * after assigning all teachers, find classes that still do not have teacher
		 * for each class
		 *     find one teacher A who has already assigned to a class that can teach an unassigned class
		 *     and one unlucky teacher who can teach same class that A was assigned to, and swap
		 */
		
		for (int classType = 0; classType < 3; classType++) {
			if (classType == 0)
			{
				type = Teachers.Type.MATH;
				clsList = ClassFactory.mathClsLst;
				clsNum = ClassFactory.getTotalMath();
			}
			else if (classType == 1)
			{
				type = Teachers.Type.READ;
				clsList = ClassFactory.readClsLst;
				clsNum = ClassFactory.getTotalRead();
			}
			else
			{
				type = Teachers.Type.LA;
				clsList = ClassFactory.laClsLst;
				clsNum = ClassFactory.getTotalLA();
			}
			while(assigned.size() > 0)
				teachers.add(assigned.remove(0));
			while(unlucky.size() > 0)
				teachers.add(unlucky.remove(0));
			
			while (teachers.size() > 0) {
				int min = findMin(teachers, type);
				Teachers t = teachers.get(min);
				int pref = t.firstPref(type);
				int clsID = getCls(pref, classes, type);
				while (clsID < 0 && t.availability(type) > 0) {
					t.changePref(pref, type);
					pref = t.firstPref(type);
					clsID = getCls(pref, classes, type);
				}
				if (t.availability(type) > 0) {
					t.setCls(pref, clsID, type);
					classes.add(clsID);
					assigned.add(teachers.remove(min));
				} else {
					unlucky.add(teachers.remove(min));
				}
			}
			while (clsNum > classes.size()) {
				int clsSize = classes.size();
				int una = unassigned(classes, type);
				int lvl = clsList.get(una).getLvl();
				int ID = clsList.get(una).getClsID();
				for (int i = 0; i < assigned.size(); i++) {
					if (assigned.get(i).canTeach(lvl, type)) {
						int assignedLvl = assigned.get(i).getClsLvl(type);
						for (int j = 0; j < unlucky.size(); j++) {
							if (unlucky.get(j).canTeach(assignedLvl, type)) {
								unlucky.get(j).setCls(assignedLvl,
										assigned.get(i).getClsID(type), type);
								assigned.get(i).setCls(lvl, ID, type);
								assigned.add(unlucky.remove(j));
								classes.add(ID);
								i = assigned.size();
								break;
							}
						}
					}
				}
				if (clsSize == classes.size())
					break;
			}


			for (int i = 0; i < assigned.size(); i++) {
				if (assigned.get(i).getClsID(Teachers.Type.MATH) <= 0
						|| assigned.get(i).getClsID(Teachers.Type.READ) <= 0
						|| assigned.get(i).getClsID(Teachers.Type.LA) <= 0) {
					unlucky.add(assigned.remove(i));
					i--;
				}
			}
		}
		//=== print outs for testing ===
		System.out.println("assigned teachers: " +assigned.size());
		for(int i = 0; i < assigned.size(); i++)
			System.out.println(assigned.get(i).toString());
		System.out.println("unassigned teachers: "+unlucky.size());
		for(int i = 0; i < unlucky.size(); i++)
			System.out.println(unlucky.get(i).toString());
	}
	
	// get unassigned class
	private static int unassigned(ArrayList<Integer> classes, Teachers.Type type)
	{
		switch(type) {
		case MATH:
			for(int i = 0; i < ClassFactory.getTotalMath(); i++)
			{
				if(!classes.contains(ClassFactory.mathClsLst.get(i).getClsID()))
					return i;
			
			}
		case READ:
			for(int i = 0; i < ClassFactory.getTotalRead(); i++)
			{
				if(!classes.contains(ClassFactory.readClsLst.get(i).getClsID()))
					return i;
			}
		case LA:
			for(int i = 0; i < ClassFactory.getTotalLA(); i++)
			{
				if(!classes.contains(ClassFactory.laClsLst.get(i).getClsID()))
					return i;
			}
		default:
			return -1;
		}
	}
	
	// check if the unassigned classes contains teacher's preferred class
	// returns classID if such class exists, else return -1
	private static int getCls(int cls, ArrayList<Integer> assigned, Teachers.Type type)
	{
		if(cls < 0)
			return -1; 
		switch(type) {
		case MATH:
			for(int i = 0; i < ClassFactory.getTotalMath(); i++)
			{
				if(ClassFactory.mathClsLst.get(i).getLvl() == cls 
						&& !assigned.contains(ClassFactory.mathClsLst.get(i).getClsID()))
					return ClassFactory.mathClsLst.get(i).getClsID();
			}
		case READ:
			for(int i = 0; i < ClassFactory.getTotalRead(); i++)
			{
				if(ClassFactory.readClsLst.get(i).getLvl() == cls
						&& !assigned.contains(ClassFactory.readClsLst.get(i).getClsID()))
					return ClassFactory.readClsLst.get(i).getClsID();
			}
		case LA:
			for(int i = 0; i < ClassFactory.getTotalLA(); i++)
			{
				if(ClassFactory.laClsLst.get(i).getLvl() == cls
						&& !assigned.contains(ClassFactory.laClsLst.get(i).getClsID()))
					return ClassFactory.laClsLst.get(i).getClsID();
			}
		default:
			return -1;
		}
	}
	
	// find the teacher with fewest number of class able to teach
	private static int findMin(ArrayList<Teachers> tch, Teachers.Type t)
	{
		int min = 0;
		for(int i = 1; i < tch.size(); i++)
		{
			if(tch.get(i).compareTo(tch.get(min), t) < 0)
				min = i;
		}
		return min;
	}
}
