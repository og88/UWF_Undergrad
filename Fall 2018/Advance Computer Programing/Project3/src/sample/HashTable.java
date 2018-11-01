package sample;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class HashTable {
	
	static FileReader fr;
	static Hashtable table;
	
public HashTable() throws IOException
{
	createTable();
}

public boolean isInTable(String str)
{
	return table.contains(str);
}

public static void createTable() throws IOException
{
	table = new Hashtable();
	try {
		fr = new FileReader("Words.txt");
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	int c = 0;
	String word = "";
	//System.out.print(c);
	while(c != -1)
	{
		c = readNext();
		if(c >= 97 && c <= 122)
		{
			word = word + (char)c;
		}
		else 
		{
			if(word != "")
			{
				//System.out.println(word);
				table.put(word, word);
			}
			word = "";
		}
	}
	//System.out.print(c);
}

public static int readNext() throws IOException
{
	int c = fr.read();
	return c;
}
}
