import java.io.IOException;
import java.util.Random;
public class RandomAccessDemo 
{
	public static void main(String[] args) throws IOException 
	{
		GradeData data = new GradeData();
		int position;
		try
		{
			data.open("Grade.bat");
			Grade g1 = new Grade();
			Random r = new Random();
			for(byte i = 1; i <= 8; i++ )
			{
				g1.setStudentID((byte) i);
				g1.setGrade(100.00 * r.nextDouble());
				System.out.printf("Stored : %s %.2f\n", g1.getStudentID(), g1.getGrade());
				data.write(i, g1);
			}
			for(byte i = 1; i <= 10; i++ )
			{
				position = data.find((byte) i);
				if (position >= 0)
				{
					g1 = data.read(position);
					System.out.printf("Found :%s %.2f\n", g1.getStudentID(), g1.getGrade());		            
				}
				else
				{
					System.out.println("Could not find : " + i);		            

				}
			}


		}
		finally
		{
			data.close();
		}

	}

}
