import java.util.ArrayList;

public class TeacherDB {
		ArrayList<Teachers> teachers;
		
		public TeacherDB (){
			teachers = new ArrayList<Teachers>();
		}
		
		public void addTeacher (Teachers t) {
			teachers.add(t);
		}
		
		public Teachers getTeacher (String name) {
			for (int i = 0; i < teachers.size(); i ++) {
				Teachers t = teachers.get(i);
				if (t.getName().equals(name)) {
					return t;
				}
			}
			return null;
		}
		
		public boolean hasTeacher (String name) {
			for (int i = 0; i < teachers.size(); i ++) {
				Teachers t = teachers.get(i);
				if (t.getName().equals(name)) {
					return true;
				}
			}
			return false;
		}

		public void removeTeacher (String name) {
			for (int i = 0; i < teachers.size(); i ++) {
				Teachers t = teachers.get(i);
				if (t.getName().equals(name)) {
					teachers.remove(i);
				}
			}
		}

		public int getSize() {
			return teachers.size();
		}
		
		public ArrayList<Teachers> getTeachers() {
			return teachers;
		}

}
