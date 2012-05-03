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
			int clsID = getCls(pref, classes, 0);
			while(clsID < 0 && t.availability() > 0)
			{
				t.changePref(pref);
				pref = t.firstPref();
				clsID = getCls(pref, classes, 0);
			}
			if(t.availability() > 0)
			{
				t.setMathCls(pref, clsID);
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
			int una = unassigned(classes, 1);
			int lvl = ClassFactory.mathClsLst.get(una).getLvl();
			int ID = ClassFactory.mathClsLst.get(una).getClsID();
			for(int i = 0; i < assigned.size(); i++)
			{
				if(assigned.get(i).canTeach(lvl))
				{
					int assignedLvl = assigned.get(i).getMathClsLvl();
					for(int j = 0; j < unlucky.size(); j++)
					{
						if(unlucky.get(j).canTeach(assignedLvl))
						{
							unlucky.get(j).setMathCls(assignedLvl, assigned.get(i).getMathClsID());
							assigned.get(i).setMathCls(lvl, ID);
							assigned.add(unlucky.remove(j));
							classes.add(ID);
							i = assigned.size();
							break;
						}
					}
				}
			}
			if(clsSize == classes.size())
				break;
		}
		
		//=== Reading ===
		classes.clear();
		// move all teachers from assigned+unlucky to teachers for assigning to reading classes
		while(assigned.size() > 0)
			teachers.add(assigned.remove(0));
		while(unlucky.size() > 0)
			teachers.add(unlucky.remove(0));
		for(int i = 0; i < teachers.size(); i++)
		{
			teachers.get(i).prefReset();
		}
		
		while(teachers.size() > 0)
		{
			int min = findMin(teachers);
			Teachers t = teachers.get(min);
			int pref = t.firstPref();
			int clsID = getCls(pref, classes, 1);
			while(clsID < 0 && t.availability() > 0)
			{
				t.changePref(pref);
				pref = t.firstPref();
				clsID = getCls(pref, classes, 1);
			}
			if(t.availability() > 0)
			{
				t.setReadCls(pref, clsID);
				classes.add(clsID);
				assigned.add(teachers.remove(min));
			}
			else
			{
				unlucky.add(teachers.remove(min));
			}
		}
		while(ClassFactory.getTotalRead() > classes.size())
		{
			int clsSize = classes.size();
			int una = unassigned(classes, 1);
			int lvl = ClassFactory.readClsLst.get(una).getLvl();
			int ID = ClassFactory.readClsLst.get(una).getClsID();
			for(int i = 0; i < assigned.size(); i++)
			{
				if(assigned.get(i).canTeach(lvl))
				{
					int assignedLvl = assigned.get(i).getReadClsLvl();
					for(int j = 0; j < unlucky.size(); j++)
					{
						if(unlucky.get(j).canTeach(assignedLvl))
						{
							unlucky.get(j).setReadCls(assignedLvl, assigned.get(i).getReadClsID());
							assigned.get(i).setReadCls(lvl, ID);
							assigned.add(unlucky.remove(j));
							classes.add(ID);
							i = assigned.size();
							break;
						}
					}
				}
			}
			if(clsSize == classes.size())
				break;
		}
		
		//=== LA ===
		classes.clear();
		// move all teachers from assigned+unlucky to teachers for assigning to reading classes
		while(assigned.size() > 0)
			teachers.add(assigned.remove(0));
		while(unlucky.size() > 0)
			teachers.add(unlucky.remove(0));
		for(int i = 0; i < teachers.size(); i++)
		{
			teachers.get(i).prefReset();
		}
		
		while(teachers.size() > 0)
		{
			int min = findMin(teachers);
			Teachers t = teachers.get(min);
			int pref = t.firstPref();
			int clsID = getCls(pref, classes, 2);
			while(clsID < 0 && t.availability() > 0)
			{
				t.changePref(pref);
				pref = t.firstPref();
				clsID = getCls(pref, classes, 2);
			}
			if(t.availability() > 0)
			{
				t.setLACls(pref, clsID);
				classes.add(clsID);
				assigned.add(teachers.remove(min));
			}
			else
			{
				unlucky.add(teachers.remove(min));
			}
		}
		while(ClassFactory.getTotalLA() > classes.size())
		{
			int clsSize = classes.size();
			int una = unassigned(classes, 1);
			int lvl = ClassFactory.laClsLst.get(una).getLvl();
			int ID = ClassFactory.laClsLst.get(una).getClsID();
			for(int i = 0; i < assigned.size(); i++)
			{
				if(assigned.get(i).canTeach(lvl))
				{
					int assignedLvl = assigned.get(i).getLAClsLvl();
					for(int j = 0; j < unlucky.size(); j++)
					{
						if(unlucky.get(j).canTeach(assignedLvl))
						{
							unlucky.get(j).setLACls(assignedLvl, assigned.get(i).getLAClsID());
							assigned.get(i).setLACls(lvl, ID);
							assigned.add(unlucky.remove(j));
							classes.add(ID);
							i = assigned.size();
							break;
						}
					}
				}
			}
			if(clsSize == classes.size())
				break;
		}
		
		for(int i = 0; i < assigned.size(); i++)
		{
			if(assigned.get(i).getLAClsID() <= 0 || assigned.get(i).getReadClsID() <= 0
					|| assigned.get(i).getMathClsID() <= 0)
			{
				unlucky.add(assigned.remove(i));
				i--;
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
	// type: 0 = math, 1 = reading, 2 = la
	private static int unassigned(ArrayList<Integer> classes, int type)
	{
		if(type == 0)
		{
			for(int i = 0; i < ClassFactory.getTotalMath(); i++)
			{
				if(!classes.contains(ClassFactory.mathClsLst.get(i).getClsID()))
					return i;
			
			}
		}
		else if(type == 1)
		{
			for(int i = 0; i < ClassFactory.getTotalRead(); i++)
			{
				if(!classes.contains(ClassFactory.readClsLst.get(i).getClsID()))
					return i;
			}
		}
		else
		{
			for(int i = 0; i < ClassFactory.getTotalLA(); i++)
			{
				if(!classes.contains(ClassFactory.laClsLst.get(i).getClsID()))
					return i;
			}
		}
		return -1;
	}
	
	// check if the unassigned classes contains teacher's preferred class
	// returns classID if such class exists, else return -1
	// type: 0 = math, 1 = reading, 2 = la
	private static int getCls(int cls, ArrayList<Integer> assigned, int type)
	{
		if(cls < 0)
			return -1; 
		if(type == 0)
		{
			for(int i = 0; i < ClassFactory.getTotalMath(); i++)
			{
				if(ClassFactory.mathClsLst.get(i).getLvl() == cls 
						&& !assigned.contains(ClassFactory.mathClsLst.get(i).getClsID()))
					return ClassFactory.mathClsLst.get(i).getClsID();
			}
		}
		else if(type == 1)
		{
			for(int i = 0; i < ClassFactory.getTotalRead(); i++)
			{
				if(ClassFactory.readClsLst.get(i).getLvl() == cls
						&& !assigned.contains(ClassFactory.readClsLst.get(i).getClsID()))
					return ClassFactory.readClsLst.get(i).getClsID();
			}
		}
		else
		{
			for(int i = 0; i < ClassFactory.getTotalLA(); i++)
			{
				if(ClassFactory.laClsLst.get(i).getLvl() == cls
						&& !assigned.contains(ClassFactory.laClsLst.get(i).getClsID()))
					return ClassFactory.laClsLst.get(i).getClsID();
			}
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
				min = i;
		}
		return min;
	}
}
