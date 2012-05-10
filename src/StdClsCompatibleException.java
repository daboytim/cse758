@SuppressWarnings("serial")
public class StdClsCompatibleException extends Exception {

	private String errMsg;
	private int errCode;

	public StdClsCompatibleException(int i) {
		this.errCode = i;
	}

	@Override
	public String toString() {
		switch (errCode) {
		case 0:
			errMsg = "The student is being moved to a class of different subject.";
		case 1:
			errMsg = "The student is being moved to a class of 5 students.";
		case 2:
			errMsg="The student is being moved to a class that violates age restriction";
		case 3:
			errMsg="The student is being moved to a class of wrong behavior level.";
		case 4:
			errMsg = "The student is being moved to a class of different subject level.";
		case 5:
			errMsg = "The new student cannot be added to any existing class.";
		default:
			break;

		}

		return errMsg;
	}

}
