import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Hashset {

	static FileReader fr;  //file reader used to read the context of a file
	static HashSet<String> set;  //HashSet used to hold the dictionary of words

	public Hashset() throws IOException
	{
		createSet();  //create the hash set of dictionary words
	}

	/**
	 * checks to see if a word is in the HashSet
	 * @param str  word to be checked
	 * @return boolean to see if the word is in the dictionary
	 */
	public boolean isInSet(String str)
	{
		return set.contains(str);
	}

	/**
	 * Create the HashSet for dictionary words
	 * @throws IOException
	 */
	public static void createSet() throws IOException
	{
		set = new HashSet<String>();  //initialize set as a String HasHSet
		try {
			fr = new FileReader("Dictionary.txt");  //Read provided Dictionary file for the contents of the HashSet
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int c = 0;
		String word = "";  //Initialize word to be tested
		while(c != -1)  //Reads chars until it reaches the end of the file
		{
			c = readNext();
			if((c >= 97 && c <= 122) || (c >= 65 && c <= 90))  // Checks to see if the char is a letter
			{
				word = word + (char)c;  //appends the char to word. This will continue until we build a complete word
			}
			else
			{
				if(word != "")
				{
					set.add(word.toLowerCase());  //add word to hash set. For this project we only worry about spelling, 
													// so we add words lowercase and check them lowercase.
				}
				word = "";  //empty the string to build new word
			}
		}
	}

	/**
	 * method used to read next char
	 * @return return the char
	 * @throws IOException 
	 */
	public static int readNext() throws IOException
	{
		int c = fr.read(); //read next char of the file
		return c;
	}
}
