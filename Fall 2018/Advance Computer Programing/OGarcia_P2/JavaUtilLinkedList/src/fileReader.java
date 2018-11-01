import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class fileReader {
	FileReader fr;
	String equation;
	
	/**
	 * open the file that we will read. For this project, we have a default name
	 * @throws FileNotFoundException
	 */
	public void initialize() throws FileNotFoundException
	{
		fr = new FileReader("addsAndSubtracts.txt");
	}
	
	/**
	 * closes file that was read
	 * @throws IOException
	 */
	public void closeReader() throws IOException
	{
		fr.close();
	}
	
	/**
	 * reads the next int of the file. The result is the ASCII value
	 * @return
	 * @throws IOException
	 */
	public int readNext() throws IOException
	{
		int c = fr.read();
		if(c >=32 && c <=57)
		{
		equation = equation + (char)c; //append the char of the string for printing later
		}
		return c;
	}
	
	/**
	 * erase previous equation if any where made
	 */
	public void resetEquation()
	{
		equation = "";
	}
	
	/**
	 * Returns the equation created 
	 * 	 * @return
	 */
	public String getEquation()
	{
		return this.equation;
	}
}
