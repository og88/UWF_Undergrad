import java.io.IOException;
import java.io.RandomAccessFile;

public class GradeData 
{
	private static RandomAccessFile file;
	
	public static final int BYTE_SIZE = 8;  
	public static final int DOUBLE_SIZE = 4;  
	public static final int RECORD_SIZE = BYTE_SIZE + DOUBLE_SIZE;

	public GradeData()
	{
		file = null;
	}
	
	public  void open(String filename) throws IOException
	{
		if (file != null) 
		{
			file.close();
		}
		file = new RandomAccessFile(filename, "rw");
	}
	
	public int size() throws IOException
	{
	return (int)(file.length() / RECORD_SIZE);	
	}
	
	public void close() throws IOException
	{
		if (file != null) 
		{
			file.close();
		}
		file = null;
	}
	
	public Grade read(int x) throws IOException
	{
	file.seek(x * RECORD_SIZE);
	byte StudentID = file.readByte();
	double grade = file.readDouble();
	return new Grade(StudentID, grade);
	}
	
	public int find(Byte StudentID) throws IOException
	{
		for(int i = 0; i < size(); i++)
		{
			file.seek(i * RECORD_SIZE);
			byte a = file.readByte();
			if(a == StudentID)
			{
				return i;
			}
		}
		return -1; // No match was found in the file
	}
	
	public void write(int x, Grade grade)
	         throws IOException
	   {  
	      file.seek(x * RECORD_SIZE);
	      file.writeByte(grade.getStudentID());
	      file.writeDouble(grade.getGrade());
	   }
}
