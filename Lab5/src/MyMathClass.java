import java.util.ArrayList;
import static java.lang.Math.*;

public class MyMathClass<T>
{

	public static double average( ArrayList< ? extends Number > list )
	{
		double total = 0; // initialize total

		// calculate sum
		for ( Number element : list )
		{
			total += element.doubleValue(); //Adds all elements to create sum
		}
		double AVG = total/list.size();  //calculates average
		return AVG;
	} 

	public static double standardDeviation( ArrayList< ? extends Number > list )
	{
		double total = 0, Deviation = 0; // initialize total
		double avg = average(list);   //retrieves average
		System.out.println(avg);

		// calculate sum
		for ( Number element : list )
		{
			total += (Math.pow((element.doubleValue()-avg),2));  //Total number neaded for standard deviation
		}
		/*sample standard deviation*/
		double av = total/(double)list.size() - 1;

		Deviation = Math.sqrt(av);
		return Deviation;
	} 

} 

