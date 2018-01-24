import java.util.Calendar;
import java.util.GregorianCalendar;

public class Bot extends Account
{
	String botFileName;
	String category;
	GregorianCalendar dateUpdated = new GregorianCalendar();
	String createdBy;

	/**
	 * Default contstructor. Sets all varaibles to ""
	 */
	Bot()
	{
		this("","", "","");
	}

	/**
	 * Parameterized constructor.
	 * @param name Sets the name of the Bot
	 * @param cat Sets the category the bot belongs to
	 * @param date Sets the date the bot was created. The date is received as
	 * a String (MM/DD/YYYY). The constructor then separates the string into
	 * a string array[] array[0] is month, array[1] is day, and array[2] is year
	 * @param creator Sets the name of the creator of the Bot
	 */
	Bot(String name, String cat, String date, String creator)
	{
		super();
		this.botFileName = name;
		this.category = cat;
		this.createdBy = creator;
		String[] date1 = date.split("/");
		dateUpdated.add(Calendar.DAY_OF_MONTH, Integer.parseInt(date1[1]));
		dateUpdated.add(Calendar.MONTH, Integer.parseInt(date1[0]));
		dateUpdated.add(Calendar.YEAR, Integer.parseInt(date1[2]));


	}

/**
 *returns the name of the Bot file as a String 
 */
	String getBotFileName()
	{
		return botFileName;
	}
	
	/**
	 * 
	 * @param name sets botFileName to name
	 */
	void setBotFileNaem(String name)
	{
		botFileName = name;
	}

	/**
	 * returns the category the bot file belongs to as a String
	 */
	String getCategory()
	{
		return category;
	}

	/**
	 * 
	 * @param cat sets category name to cat
	 */
	void setCategory(String cat)
	{
		category = cat;
	}
	/**
	 *returns the dateUpdated as a GregorianCalendar
	 */
	GregorianCalendar getDateUpdated()
	{
		return dateUpdated;
	}

	/**
	 * returns the name of the Creator as a String
	 */
	String getCreatedBy()
	{
		return createdBy;
	}
	
	/**
	 * 
	 * @param creator sets the name of the bot creator to creator
	 */
	void setCreatedBy(String creator)
	{
		createdBy = creator;
	}
}


