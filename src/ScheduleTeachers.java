import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ScheduleTeachers implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void assign(TeacherDB tch, ClassFactory clsFac)
	{
		Teachers.Type type;
		ArrayList<Teachers> teachers = tch.getTeachers();
		for(Teachers t : teachers)
		{
			t.reset();
		}
		ArrayList<Teachers> assigned = new ArrayList<Teachers>();
		ArrayList<Teachers> unlucky = new ArrayList<Teachers>();
		ArrayList<Integer> classes = new ArrayList<Integer>();
		List<Classes> clsList;
		List<Classes> emptyCls = new ArrayList<Classes> ();
		int clsNum=0;
		
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
		
		// push empty classes from homeroom and special to end of class list
		clsList = clsFac.homeroomClsLst;
		for(int i = 0; i < clsList.size(); i++)
		{
			if(clsList.get(i).getTotal() == 0)
			{
				Classes tempCls = clsList.remove(i);
				if(tempCls.hasTeacher())
				{
					tempCls.removeTeacher();
				}
				emptyCls.add(tempCls);
				i--;
			}
		}
		while(emptyCls.size() > 0)
			clsList.add(emptyCls.remove(0));
		clsList = clsFac.specialClsLst;
		for(int i = 0; i < clsList.size(); i++)
		{
			if(clsList.get(i).getTotal() == 0)
			{
				Classes tempCls = clsList.remove(i);
				if(tempCls.hasTeacher())
				{
					tempCls.removeTeacher();
				}
				emptyCls.add(tempCls);
				i--;
			}
		}
		while(emptyCls.size() > 0)
			clsList.add(emptyCls.remove(0));
		
		for (int classType = 0; classType < 3; classType++) {
			// === reset ===
			if (classType == 0)
			{
				type = Teachers.Type.MATH;
				clsList = clsFac.mathClsLst;
				for(int i = 0; i < clsList.size(); i++)
				{
					if(clsList.get(i).getTotal() == 0)
					{
						Classes tempCls = clsList.remove(i);
						if(tempCls.hasTeacher())
						{
							tempCls.removeTeacher();
						}
						emptyCls.add(tempCls);
						i--;
					}
				}
				clsNum = clsFac.mathClsLst.size();
			}
			else if (classType == 1)
			{
				type = Teachers.Type.READ;
				clsList = clsFac.readClsLst;
				for(int i = 0; i < clsList.size(); i++)
				{
					if(clsList.get(i).getTotal() == 0)
					{
						Classes tempCls = clsList.remove(i);
						if(tempCls.hasTeacher())
						{
							tempCls.removeTeacher();
						}
						emptyCls.add(tempCls);
						i--;
					}
				}
				clsNum += clsFac.readClsLst.size();
			}
			else
			{
				type = Teachers.Type.LA;
				clsList = clsFac.laClsLst;
				for(int i = 0; i < clsList.size(); i++)
				{
					if(clsList.get(i).getTotal() == 0)
					{
						Classes tempCls = clsList.remove(i);
						if(tempCls.hasTeacher())
						{
							tempCls.removeTeacher();
						}
						emptyCls.add(tempCls);
						i--;
					}
				}
				clsNum += clsFac.laClsLst.size();
			}
			while(assigned.size() > 0)
				teachers.add(assigned.remove(0));
			while(unlucky.size() > 0)
				teachers.add(unlucky.remove(0));
			// === reset end ===
			
			// if teacher has class can teach, move to assigned
			// else move to unlucky
			while (teachers.size() > 0) {
				int min = findMin(teachers, type);
				Teachers t = teachers.get(min);
				int pref = t.firstPref(type);
				int clsID = getCls(pref, classes, type, clsFac);
				while (clsID < 0 && t.availability(type) > 0) {
					t.changePref(pref, type);
					pref = t.firstPref(type);
					clsID = getCls(pref, classes, type, clsFac);
				}
				if (t.availability(type) > 0) {
					t.setCls(pref, clsID, type);
					classes.add(clsID);
					assigned.add(teachers.remove(min));
				} else {
					t.setCls(-1, -1, type);
					unlucky.add(teachers.remove(min));
				}
			}
			// check assigned teachers, see if can swap with unlucky for classes unlucky can't teach
			for (int i = 0; i < clsList.size(); i++) {
				if(!classes.contains(clsList.get(i).getClsID()))
				{
					int unaLvl = clsList.get(i).getLvl();
					int unaID = clsList.get(i).getClsID();
					if(!swap(assigned, unlucky, classes, unaLvl, unaID, type))
					{
						String name;
						if(type == Teachers.Type.MATH)
							name = "   Math";
						else if(type == Teachers.Type.READ)
							name = "Reading";
						else
							name = "     LA";
						System.out.println("unassigned class:: " + name + " lvl " + unaLvl + " ID " + unaID);
					}
					
				}
			}
			if(classes.size() < clsList.size())
			{
				clsNum = clsNum - (clsList.size() - classes.size());
			}
			
			while(emptyCls.size() > 0)
			{
				clsList.add(emptyCls.remove(0));
			}
		}
		
		// if any teacher doesn't have all 3 classes assigned, move to unlucky
		for (int i = 0; i < assigned.size(); i++) {
			if (assigned.get(i).getClsID(Teachers.Type.MATH) < 0
					|| assigned.get(i).getClsID(Teachers.Type.READ) < 0
					|| assigned.get(i).getClsID(Teachers.Type.LA) < 0) {
				unlucky.add(assigned.remove(i));
				i--;
			}
		}
		
		// === fill up all 3 classes for unlucky if possible, then move to assigned ===
		for (int i = 0; i < unlucky.size(); ) {
			Teachers lucky = unlucky.remove(0);
			for(int k = 0; k < 3; k++) {
				if(k == 0){
					type = Teachers.Type.MATH;
				} else if(k == 1){
					type = Teachers.Type.READ;
				} else {
					type = Teachers.Type.LA;
				}
				if(lucky.getClsID(type) < 0 )
				{
					boolean fromUnlucky = false;
					for(int j = 0; j < unlucky.size(); j++)
					{
						if(lucky.canTeach(unlucky.get(j).getClsLvl(type), type))
						{
							lucky.setCls(unlucky.get(j).getClsLvl(type), unlucky.get(j).getClsID(type), type);
							unlucky.get(j).setCls(-1, -1, type);
							fromUnlucky = true;
							break;
						}
					}
					if(!fromUnlucky)
					{
						for(int j = 0; j < unlucky.size(); j++)
						{
							if(unlucky.get(j).getClsID(type) >= 0)
							{
								saveUnlucky(unlucky.get(j).getClsLvl(type), unlucky.get(j).getClsID(type), lucky, assigned, type);
								if(lucky.getClsID(type) >= 0)
								{
									unlucky.get(j).setCls(-1, -1, type);
									break;
								}
							}
						}
					}
				}
			}
			if(lucky.getClsID(Teachers.Type.MATH)>= 0 && 
					lucky.getClsID(Teachers.Type.READ) >= 0 &&
					lucky.getClsID(Teachers.Type.LA) >= 0)
			{
				assigned.add(lucky);
			}
			else
			{
				unlucky.add(i, lucky);
				i++;
			}
		}
		
		//======= assign homeroom, special according to students in math class =======
		for(int i = 0; i < assigned.size(); i++)
		{
			for(int j = 0; j < clsFac.mathClsLst.size(); j++)
			{
				if(clsFac.mathClsLst.get(j).getClsID() == assigned.get(i).getClsID(Teachers.Type.MATH))
				{
					if(!clsFac.mathClsLst.get(j).getStudents().isEmpty())
					{
						assigned.get(i).setCls(-1, clsFac.mathClsLst.get(j).
								getStudents().get(0).getHomeroomCls().getClsID(), Teachers.Type.HR);
						assigned.get(i).setCls(-1, clsFac.mathClsLst.get(j).
								getStudents().get(0).getSpecialCls().getClsID(), Teachers.Type.SP);
					}
				}
			}
		}
		for(int i = 0; i < unlucky.size(); i++)
		{
			for(int j = 0; j < clsFac.mathClsLst.size(); j++)
			{
				if(clsFac.mathClsLst.get(j).getClsID() == unlucky.get(i).getClsID(Teachers.Type.MATH))
				{
					if(!clsFac.mathClsLst.get(j).getStudents().isEmpty())
					{
					unlucky.get(i).setCls(-1, clsFac.mathClsLst.get(j).
							getStudents().get(0).getHomeroomCls().getClsID(), Teachers.Type.HR);
					unlucky.get(i).setCls(-1, clsFac.mathClsLst.get(j).
							getStudents().get(0).getSpecialCls().getClsID(), Teachers.Type.SP);
					}
				}
			}
		}
		//=== printouts for testing ===
		System.out.println("assigned teachers: " +assigned.size());
		for(int i = 0; i < assigned.size(); i++)
			System.out.println(assigned.get(i).toString());
		System.out.println("unassigned teachers: "+unlucky.size());
		for(int i = 0; i < unlucky.size(); i++)
			System.out.println(unlucky.get(i).toString());
		
		// assign to class officially
		Teachers temp;
		while(assigned.size() > 0)
		{
			temp = assigned.remove(0);
			temp.setPreference(temp.capableM, Teachers.Type.MATH);
			temp.setPreference(temp.capableL, Teachers.Type.LA);
			temp.setPreference(temp.capableR, Teachers.Type.READ);
			assignToClass(temp, clsFac);
			teachers.add(temp);
		}
		while(unlucky.size() > 0)
		{
			temp = unlucky.remove(0);
			temp.setPreference(temp.capableM, Teachers.Type.MATH);
			temp.setPreference(temp.capableL, Teachers.Type.LA);
			temp.setPreference(temp.capableR, Teachers.Type.READ);
			assignToClass(temp, clsFac);
			teachers.add(temp);
		}
	}

	// search through assigned teachers to check if there's class that unlucky teacher can teach. if so, swap
	private static void saveUnlucky(Integer unaLvl, Integer unaID, 
			Teachers unlucky, ArrayList<Teachers> assigned, Teachers.Type type)
	{
		for (int i = 0; i < assigned.size(); i++)
		{
			if(assigned.get(i).canTeach(unaLvl, type))
			{
				if(unlucky.canTeach(assigned.get(i).getClsLvl(type), type))
				{
					unlucky.setCls(assigned.get(i).getClsLvl(type), assigned.get(i).getClsID(type), type);
					assigned.get(i).setCls(unaLvl, unaID, type);
					break;
				}
			}
		}
		int searchCount = 0;
		for (int i = 0; i < assigned.size() && searchCount < 1; i++)
		{
			if (assigned.get(i).canTeach(unaLvl, type)) {
				searchCount++;
				Teachers temp = assigned.remove(i);
				saveUnlucky(temp.getClsLvl(type), temp.getClsID(type), unlucky,
						assigned, type);
				if (unlucky.getClsID(type) >= 0) {
					temp.setCls(unaLvl, unaID, type);
					assigned.add(i, temp);
					break;
				}
				assigned.add(i, temp);

			}
		}
	}
	
	// search through assigned, to find teacher that can teach unaLvl 
	// and swap with unlucky that can teach assigned
	private static boolean swap(ArrayList<Teachers> assigned, ArrayList<Teachers> unlucky, 
			ArrayList<Integer> classes, int unaLvl, int unaID, Teachers.Type type)
	{
		for (int i = 0; i < assigned.size(); i++) {
			if (assigned.get(i).canTeach(unaLvl, type)) {
				int assignedLvl = assigned.get(i).getClsLvl(type);
				int assignedID = assigned.get(i).getClsID(type);
				for (int j = 0; j < unlucky.size(); j++) {
					if (unlucky.get(j).canTeach(assignedLvl, type)) {
						unlucky.get(j).setCls(assignedLvl, assignedID, type);
						assigned.get(i).setCls(unaLvl, unaID, type);
						assigned.add(unlucky.remove(j));
						if(!classes.contains(unaID))
						{
							classes.add(unaID);
						}
						return true;
					}
				}
			}
		}
		int searchCount = 0;
		for (int i = 0; i < assigned.size() && searchCount < 1; i++)
		{
			if (assigned.get(i).canTeach(unaLvl, type)) {
				searchCount ++;
				int assignedLvl = assigned.get(i).getClsLvl(type);
				int assignedID = assigned.get(i).getClsID(type);
				Teachers resign = assigned.remove(i);
				if (swap(assigned, unlucky, classes, assignedLvl, assignedID,
						type)) {
					resign.setCls(unaLvl, unaID, type);
					if (!classes.contains(unaID)) {
						classes.add(unaID);
					}
					assigned.add(i, resign);
					return true;
				}
				assigned.add(i, resign);
			}
		}
		return false;
	}
	// assign teacher to classes
	private static void assignToClass(Teachers t, ClassFactory clsFac)
	{
		for(int i = 0; i < clsFac.mathClsLst.size(); i++)
		{
			if(clsFac.mathClsLst.get(i).getClsID() == t.getClsID(Teachers.Type.MATH))
			{
				clsFac.mathClsLst.get(i).setTeacher(t);
			}
		}
		for(int i = 0; i < clsFac.readClsLst.size(); i++)
		{
			if(clsFac.readClsLst.get(i).getClsID() == t.getClsID(Teachers.Type.READ))
			{
				clsFac.readClsLst.get(i).setTeacher(t);
			}
		}
		for(int i = 0; i < clsFac.laClsLst.size(); i++)
		{
			if(clsFac.laClsLst.get(i).getClsID() == t.getClsID(Teachers.Type.LA))
			{
				clsFac.laClsLst.get(i).setTeacher(t);
			}
			
		}
		for(int i = 0; i < clsFac.homeroomClsLst.size(); i++)
		{
			if(clsFac.homeroomClsLst.get(i).getClsID() == t.getClsID(Teachers.Type.HR))
			{
				clsFac.homeroomClsLst.get(i).setTeacher(t);
			}
		}
		for(int i = 0; i < clsFac.specialClsLst.size(); i++)
		{
			if(clsFac.specialClsLst.get(i).getClsID() == t.getClsID(Teachers.Type.SP))
			{
				clsFac.specialClsLst.get(i).setTeacher(t);
			}
		}
	}
	
	// check if the unassigned classes contains teacher's preferred class
	// returns classID if such class exists, else return -1
	private static int getCls(int cls, ArrayList<Integer> assignedIDs, Teachers.Type type, ClassFactory clsFac)
	{
		if(cls < 0)
			return -1; 
		switch(type) {
		case MATH:
			for(int i = 0; i < clsFac.mathClsLst.size(); i++)
			{
				if(clsFac.mathClsLst.get(i).getLvl() == cls 
						&& !assignedIDs.contains(clsFac.mathClsLst.get(i).getClsID()))
					return clsFac.mathClsLst.get(i).getClsID();
			}
			return -1;
		case READ:
			for(int i = 0; i < clsFac.readClsLst.size(); i++)
			{
				if(clsFac.readClsLst.get(i).getLvl() == cls
						&& !assignedIDs.contains(clsFac.readClsLst.get(i).getClsID()))
					return clsFac.readClsLst.get(i).getClsID();
			}
			return -1;
		case LA:
			for(int i = 0; i < clsFac.laClsLst.size(); i++)
			{
				if(clsFac.laClsLst.get(i).getLvl() == cls
						&& !assignedIDs.contains(clsFac.laClsLst.get(i).getClsID()))
				{
					return clsFac.laClsLst.get(i).getClsID();
				}
			}
			return -1;
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
