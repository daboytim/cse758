import java.util.ArrayList;

public class TeacherDB {
		ArrayList<Teachers> teachers;
		
		public TeacherDB (){
			teachers = new ArrayList<Teachers>();
		}
		
		public void addTeacher (Teachers t) {
			teachers.add(t);
		}
		
		public Teachers getTeacher (int id) {
			for (int i = 0; i < teachers.size(); i ++) {
				Teachers t = teachers.get(i);
				if (t.getId() == id) {
					return t;
				}
			}
			return null;
		}
		
		public boolean hasTeacher (int id) {
			for (int i = 0; i < teachers.size(); i ++) {
				Teachers t = teachers.get(i);
				if (t.getId() == id) {
					return true;
				}
			}
			return false;
		}

		public void removeTeacher (int id) {
			for (int i = 0; i < teachers.size(); i ++) {
				Teachers t = teachers.get(i);
				if (t.getId() == id) {
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
