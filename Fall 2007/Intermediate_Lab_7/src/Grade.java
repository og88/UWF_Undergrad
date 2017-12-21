public class Grade 
{
	byte studentID;
	double grade;

	Grade()
	{
		this((byte)0,0.0);
	}
	
	Grade(byte ID, double grade1)
	{
		this.studentID = ID;
		this.grade = grade1;
	}
	
	public byte getStudentID() 
	{
		return studentID;
	}
	
	public void setStudentID(byte studentID) 
	{
		this.studentID = studentID;
	}
	
	public double getGrade() 
	{
		return grade;
	}
	
	public void setGrade(double grade) 
	{
		this.grade = grade;
	}
	
	public String toString()
	{
		return "Student " + studentID + " grade is " + grade;
	}
}
