package sample;
import java.io.IOException;

public class SpellChecker {
	
	static HashTable t;
	
	public static void stringBuilder(String input)
	{
		StringBuffer in = new StringBuffer(input);
		int c = 0;
		int i = 0;
		String word = "";
		while(i < in.length())
		{
			c = in.charAt(i);
			if(c >= 97 && c <= 122)
			{
				word = word + (char)c;
			}
			if(!(c >= 97 && c <= 122) || i == in.length()-1) 
			{
				if(word != "")
				{
					//System.out.println(word);
					spellCheck(word);
				}
				word = "";
			}
			i++;
		}
	}
	
	public static void spellCheck(String s)
	{
		StringBuffer str = new StringBuffer(s);
		try {
			t = new HashTable();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!t.isInTable(s))
		{
			boolean done = oneLetterMissing(str);
			if(!done)
			{
				done = oneLetterAdded(str);
				if(!done)
				{
					swappedLetters(str);
				}
			}
		}
	}
	
	public static boolean swappedLetters(StringBuffer sb)
	{
		//System.out.println("swapp letters");
		String original = sb.toString();
		for(int pos = 0; pos < sb.length() - 1; pos++)
		{
			char first = sb.charAt(pos);
			char second = sb.charAt(pos + 1);
			
			sb.setCharAt(pos, second);
			sb.setCharAt(pos + 1, first);
			
			if(t.isInTable(sb.toString()))
			{
				System.out.print("Recomend " + sb.toString() + " for word " + original + ".\n");
				//return true;
				//return false;
			}
			else
			{
				sb.setCharAt(pos, first);
				sb.setCharAt(pos + 1, second);	
			}
		}
		return false;
	}
	
	public static boolean oneLetterMissing(StringBuffer sb)
	{
		//System.out.println("onelettermissing");
		String original = sb.toString();
		for(int pos = 0; pos <= sb.length(); pos++)
		{
			sb.insert(pos, "a");
			for(int i = 97; i < 123; i++)
			{
				sb.setCharAt(pos, (char)i);
				if(t.isInTable(sb.toString()))
				{
					System.out.print("Recomend " + sb.toString() + " for word " + original + ".\n");
					//return true;
					//return false;
				}
			}
			sb.deleteCharAt(pos);
		}
		return false;
	}
	
	public static boolean oneLetterAdded(StringBuffer sb)
	{
		//System.out.println("oneletter added");
		String origin = sb.toString();
		char original;
		for(int pos = 0; pos < sb.length(); pos++)
		{
			original = sb.charAt(pos);
			sb.deleteCharAt(pos);
			if(t.isInTable(sb.toString()))
			{
				System.out.print("Recomend " + sb.toString() + " for word " + origin + ".\n");
				//return true;
				//return false;
			}
			sb.insert(pos, original);
		}
		return false;
	}
	
	public static boolean replaceChar(StringBuffer sb)
	{
		for(int pos = 0; pos < sb.length(); pos++)
		{
			char old = sb.charAt(pos);
			for(int i = 97; i < 123; i++)
			{
				sb.setCharAt(pos, (char)i);
				System.out.println(sb);
			}
			sb.setCharAt(pos, old);
		}
		return false;
	}
}
