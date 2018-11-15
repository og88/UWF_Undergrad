import javafx.scene.control.Alert;
import java.io.IOException;
import java.util.ArrayList;

public class SpellChecker {
	static Hashset hashset;

	/**
	 * This method creates a dialog message to show to the user. The message informs the userr about the result of the spell check.
	 * If there are errors, the message will recommend correct words. If no suggestions could be found, the user will be informed.
	 * @param rec  array list of recommended words
	 * @param word  original word that needs to be fixed
	 * @param context Custom message if no recommended words are available
	 */
	public static void recomendations(ArrayList<String> rec, String word, String context)
	{
		if(rec.size()>0)  //rec array has words in it!
		{
			context = "Recommended words for " + word + "\n";  //Message telling the user what the message is for
			for(int i = 0; i < rec.size(); i ++)  //goes through the array to get all the recommended words.
			{
				if(i == rec.size() - 1)  //If this is the last word, add a period not comma
				{
					context += rec.get(i) + ". ";
				}
				else {  //add comma to seperate the words
					context += rec.get(i) + ", ";
				}
			}
		}
		Alert alert = new Alert(Alert.AlertType.INFORMATION);  //create a dialog object with text context
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(context);
		alert.showAndWait();

	}

	/**
	 * Creates a string buffer out of the provided string. String buffer are very helpful since the program is seperating the input into words.
	 * @param input input that needs to be checked
	 */
	public static void stringBuilder(String input)
	{
		try {
			hashset = new Hashset();  //try to create hash set
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuffer in = new StringBuffer(input);  //create string buffer out of the provided input
		int c = 0;
		int i = 0;
		String word = "";
		boolean misspelled = false;  //boolean to check if there were any misspelled words
		ArrayList<String> recommend = new ArrayList<String>();  //array list for recommended words
		while(i < in.length())  //continue to build words until the end of the input
		{
			c = in.charAt(i);
			if((c >= 97 && c <= 122) || (c >= 65 && c <= 90))  //Checks to make sure only letters are being process into words
			{
				word = word + (char)c;
			}
			if((!(c >= 97 && c <= 122) && !(c >= 65 && c <= 90)) || i == in.length()-1)  //if we encounter anything other than a letter, check the word we built
			{
				if(word != "")  //If no word had been built, no need to check for spelling
				{
					if(!hashset.isInSet(word.toLowerCase()))  //Check to see if the word is in the dictionary
					{
						recommend = spellCheck(word.toLowerCase());  //run spell checker and receive an array of recommended words
						misspelled = true;  //the word is not in the dictionary, so it is considered misspelled
						if(recommend.size()>0) {  //if recommended words are found create dialog with recommended words
							recomendations(recommend, word,"");
						}
						else{
							recomendations(recommend, word,"No suggestions could be found for " + word + "!");  //If recommended words are found, inform the user.
						}
					}
				}
				word = "";
				recommend.clear();  //clear out the array list for next word.
			}
			i++;
		}
		if(!misspelled)  //Check to see id the word is was misspelled.
		{
			recomendations(recommend, word,"The input contain no misspelled words!"); //If not misspelled, inform user.
		}
	}

	/**
	 * Method that handles spell checking. Goes through the test to see if a recommend word is found.
	 * @param s word to be checked
	 * @return list of recommended words
	 */
	public static ArrayList<String> spellCheck(String s)
	{
		ArrayList<String> recomend = new ArrayList<String>();  //Arraylist of recommended words
		StringBuffer str = new StringBuffer(s);  //create a string buffer out of the word
		//Run through each check once, and receive recommendations
			recomend = oneLetterMissing(str,recomend);
			recomend = oneLetterAdded(str,recomend);
			recomend = swappedLetters(str,recomend);
			recomend = replaceChar(str, recomend);
		return recomend;
	}

	/**
	 * method to check if two letters have been swapped
	 * @param sb word to be checked
	 * @param R recommendation list. if new recommendation are found, the will be added to the list
	 * @return return the recommendations list
	 */
	public static ArrayList<String> swappedLetters(StringBuffer sb, ArrayList<String> R)
	{
		for(int pos = 0; pos < sb.length() - 1; pos++)
		{
			char first = sb.charAt(pos);  //first letter to be swapped
			char second = sb.charAt(pos + 1);  //second letter to be swapped

			sb.setCharAt(pos, second); //Swap the two letters
			sb.setCharAt(pos + 1, first);

			if(hashset.isInSet(sb.toString())) //check to see if the new word is in the dictionary
			{
				if(!R.contains(sb.toString()))  //if new word isn't in the recommendation, add it
				{
					R.add(sb.toString());
				}
				sb.setCharAt(pos, first);  //reset word for further testing
				sb.setCharAt(pos + 1, second);
			}
			else
			{
				sb.setCharAt(pos, first);  //reset word for further testing
				sb.setCharAt(pos + 1, second);
			}
		}
		return R;
	}

	/**
	 * method to check if one letter was left out
	 * @param sb word to be checked
	 * @param R recommendation list. if new recommendation are found, the will be added to the list
	 * @return return the recommendations list
	 */
	public static ArrayList<String> oneLetterMissing(StringBuffer sb, ArrayList<String> R)
	{
		for(int pos = 0; pos <= sb.length(); pos++)
		{
			sb.insert(pos, "a");  //add a letter to a position, starting with a
			for(int i = 97; i < 123; i++)  //increment each letter until we reach z
			{
				sb.setCharAt(pos, (char)i);  //set the letter at the current position to the new letter
				if(hashset.isInSet(sb.toString()))  //check to see if the new word is valid
				{
					if(!R.contains(sb.toString()))  //add to recommendation is not already in
					{
						R.add(sb.toString());
					}				}
			}
			sb.deleteCharAt(pos); //remove added letter
		}
		return R;  //return recommendation
	}

	/**
	 * method to check if one letter was added
	 * @param sb word to be checked
	 * @param R recommendation list. if new recommendation are found, the will be added to the list
	 * @return return the recommendations list
	 */
	public static ArrayList<String> oneLetterAdded(StringBuffer sb, ArrayList<String> R)
	{
		char original;
		for(int pos = 0; pos < sb.length(); pos++)
		{
			original = sb.charAt(pos);  //create a copy of the original letter to be removed
			sb.deleteCharAt(pos);  //remove letter at current position
			if(hashset.isInSet(sb.toString())) //check to see if the new word is valid
			{
				if(!R.contains(sb.toString()))  //add to recommendation is not already in
				{
					R.add(sb.toString());
				}
			}
			sb.insert(pos, original);  //add back removed letter
		}
		return R;
	}

	/**
	 * method to check if a letter was mistyped
	 * @param sb word to be checked
	 * @param R recommendation list. if new recommendation are found, the will be added to the list
	 * @return return the recommendations list
	 */
	public static ArrayList<String> replaceChar(StringBuffer sb, ArrayList<String> R)
	{
		for(int pos = 0; pos < sb.length(); pos++)
		{
			char old = sb.charAt(pos);  //create a copy of the letter that will be replaced
			for(int i = 97; i < 123; i++)  //replace letter starting at a and ending at z
			{
				sb.setCharAt(pos, (char)i);  //set letter to new value
				if(hashset.isInSet(sb.toString())) //check to see if the new word is valid
				{
					if(!R.contains(sb.toString()))  //add to recommendation is not already in
					{
						R.add(sb.toString());
					}
				}
			}
			sb.setCharAt(pos, old);  //set letter beack to its original value
		}
		return R;
	}

}
