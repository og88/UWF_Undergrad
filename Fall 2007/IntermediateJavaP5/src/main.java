import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class main 
{
	public main(String[] args) throws IOException
	{
		CarbonFootprint array[] = new CarbonFootprint[9]; //Creates an array of carbo footprint interfaces
		BufferedReader input = null;  //initializes a BufferReader object
		try 
		{
			FileReader file = new FileReader("file.txt");  //Opens the file file.txt
			input = new BufferedReader(file);  //sets the file buffer to file
			String line;
			int i = 0;
			while((line = input.readLine()) != null)  //reads a files lines until it reaces the end of the file
			{
				switch(line) //switch statement to determine whether the new object is  an auto, building, or food
				{
				case "a": 
					Auto x = new Auto();  //creates a new auto class
					x.setName(input.readLine());  //sets the name of the object
					x.setMPG(input.read());
					x.calculateDE();
					x.calculateIDE();
					x.calculateCE(input.read(),input.read());
					array[i] = x; //Adds object to the array list
					i++;
					break;
				case "b": 
					Building x1 = new Building();
					x1.setName(input.readLine());   //sets the name of the object
					x1.setkWhyr(input.read());      //Sets the eltricity footprint
					x1.setEF(input.read());         //sets emission factor
					array[i] = x1;  //Adds object to the array list
					i++;
					break;
				case "f": 
					Food x2 = new Food();
					x2.setEF(input.readLine()); //sets appropriate EF
					x2.setName(input.readLine());   //sets the name of the object
					x2.setConsumption(input.read()); //Sets consumption value
					array[i] = x2;  //Adds object to the array list
					i++;
					break;
				}
			}
		} catch ( IOException e )  //Catches an exception if an error occurs
		{
			e.printStackTrace();
		} finally 
		{
			if ( input != null ) 
			{
				input.close(); //Closes the open file
			}

		}
		for(int i = 0; i < 9; i++)
		{
			array[i].getCarbonFootprint();  //Runs the get carbon foot print method for each object
		}
	}
}
