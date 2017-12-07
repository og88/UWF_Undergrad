import java.io.*;

/*This is an simplae exception that is thrown when an email is malformed.*/
public class MalformedEmailAddress extends Exception
{
	/*Malformed holds the malformed email address as a string*/
	private String Malformed;

	/*Constructor sets malformed to the email imported by the user*/
	public MalformedEmailAddress(String Email)
	{
		this.Malformed = Email;

	}
	
	/*Returns an error message when the email is malformed*/
	public String getError()
	{
		return "ERROR: The Email " + Malformed + " Is not in the proper format  xxxxxxx@xxxxx.xxx";
	}

}
