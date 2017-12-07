
public class Sentence {
	static String sentence;

	Sentence(String sentence)
	{
		Sentence.sentence = sentence;
	}
	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		Sentence.sentence = sentence;
	}

	public static int indexOf(String subSentence)
	{
		int test = -1;
		if(sentence.charAt(0) == subSentence.charAt(0))
		{
			test = 0;
			for(int i = 1; i < subSentence.length(); i++)
			{
				if(!(sentence.charAt(i) == subSentence.charAt(i)))
				{
					test = -1;
				}
			}
		}
		if(test == -1)
		{
			String newSentence = "";
			if((sentence.length() - subSentence.length()) >= 0)
			{
				for(int i = 1; i < sentence.length(); i++)
				{
					newSentence = newSentence + sentence.charAt(i);
				}
				test = indexOf(newSentence, subSentence, 1);
			}
			else
			{
				test = -1;
			}
		}
		return test;
	}

	public static int indexOf(String sentence, String subSentence, int index)
	{
		int test = -1;
		if(sentence.charAt(0) == subSentence.charAt(0))
		{
			test = index;
			for(int i = 1; i < subSentence.length(); i++)
			{
				if(!(sentence.charAt(i) == subSentence.charAt(i)))
				{
					test = -1;
				}
			}
		}
		if(test == -1)
		{
			String newSentence = "";
			if((sentence.length() - subSentence.length()) >= 0)
			{
				for(int i = 1; i < sentence.length(); i++)
				{
					newSentence = newSentence + sentence.charAt(i);
				}
				test = indexOf(newSentence, subSentence, index + 1);
			}
			else
			{
				test = -1;
			}
		}
		return test;	}
	
	
	public static Boolean compare(String sentence, String subSentence)
	{
		Boolean test = false;
		if(sentence.charAt(0) == subSentence.charAt(0))
		{
			test = true;
			for(int i = 1; i < subSentence.length(); i++)
			{
				if(!(sentence.charAt(i) == subSentence.charAt(i)))
				{
					test = false;
				}
				else
				{
					test = true;
				}
			}
		}
		if(test == false)
		{
			String newSentence = "";
			if((sentence.length() - subSentence.length()) >= 0)
			{
				for(int i = 1; i < sentence.length(); i++)
				{
					newSentence = newSentence + sentence.charAt(i);
				}
				test = compare(newSentence, subSentence);
			}
			else
			{
				test = false;
			}
		}
		return test;
	}
}
