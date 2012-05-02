import java.util.ArrayList;


public class ScheduleTeachers {

	public static void assign(TeacherDB tch)
	{
		ArrayList<Teachers> teachers = tch.getTeachers();
		ArrayList<Teachers> assigned = new ArrayList<Teachers>();
		ArrayList<Teachers> unlucky = new ArrayList<Teachers>();
		ArrayList<Integer> classes = new ArrayList<Integer>();
		
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
		
		//=== math ===
		while(teachers.size() > 0)
		{
			int min = findMin(teachers);
			Teachers t = teachers.get(min);
			int pref = t.firstPref();
			int clsID = getCls(pref, classes);
			while(clsID < 0 && t.availability() > 0)
			{
				t.changePref(pref);
				pref = t.firstPref();
				clsID = getCls(pref, classes);
			}
			if(t.availability() > 0)
			{
				t.setCls(pref, clsID);
				classes.add(clsID);
				assigned.add(teachers.remove(min));
			}
			else
			{
				unlucky.add(teachers.remove(min));
			}
		}
		while(ClassFactory.getTotalMath() > classes.size())
		{
			int clsSize = classes.size();
			int una = unassigned(classes);
			int lvl = ClassFactory.mathClsLst.get(una).getLvl();
			int ID = ClassFactory.mathClsLst.get(una).getClsID();
			for(int i = 0; i < assigned.size(); i++)
			{
				if(assigned.get(i).canTeach(lvl))
				{
					int assignedLvl = assigned.get(i).getClsLvl();
					for(int j = 0; j < unlucky.size(); j++)
					{
						if(unlucky.get(j).canTeach(assignedLvl))
						{
							unlucky.get(j).setCls(assignedLvl, assigned.get(i).getClsID());
							assigned.get(i).setCls(lvl, ID);
							assigned.add(unlucky.remove(j));
							classes.add(ID);
							i = assigned.size();
						}
					}
				}
			}
			if(clsSize == classes.size())
				break;
		}
		
		//=== print outs for testing ===
		System.out.println("assigned teachers");
		for(int i = 0; i < assigned.size(); i++)
			System.out.println(teachers.get(i).toString());
		System.out.println("unassigned teachers");
		for(int i = 0; i < unlucky.size(); i++)
			System.out.println(unlucky.get(i).toString());
	}
	
	// get unassigned class
	private static int unassigned(ArrayList<Integer> classes)
	{
		for(int i = 0; i < ClassFactory.getTotalMath(); i++)
		{
			if(!classes.contains(ClassFactory.mathClsLst.get(i).getClsID()))
				return i;
		}
		return -1;
	}
	
	// check if the unassigned classes contains teacher's preferred class
	// returns classID if such class exists, else return -1
	private static int getCls(int cls, ArrayList<Integer> assigned)
	{
		if(cls < 0)
			return -1; 
		for(int i = 0; i < ClassFactory.getTotalMath(); i++)
		{
			if(ClassFactory.mathClsLst.get(i).getLvl() == cls 
					&& !assigned.contains(ClassFactory.mathClsLst.get(i).getClsID()))
				return ClassFactory.mathClsLst.get(i).getClsID();
		}
		return -1;
	}
	
	// find the teacher with fewest number of class able to teach
	private static int findMin(ArrayList<Teachers> tch)
	{
		int min = 0;
		for(int i = 1; i < tch.size(); i++)
		{
			if(tch.get(i).compareTo(tch.get(min)) < 0)
				min = 0;
		}
		return min;
	}
}
