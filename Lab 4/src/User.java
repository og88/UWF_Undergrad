import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.*;
import java.util.Random;

public class User 
{
	/*Variables that belong to the user class*/
	String name, emailAddress;
	int age;
	double salary;
	Random rand = new Random();


/*Default constructor for user object*/
	User()
	{
		name = "";
		emailAddress = "";
		age = 0;
		salary = 0.00;
	}

	/*Method to set the name of a user*/
	public void setName(String nam)
	{
		name = nam;
	}

	/*Method to set the age of the user*/
	public void setAge(int Age)
	{
		age = Age;
	}


	public Boolean setSalary(String Sal)
	{
		try
		{
			salary = Integer.parseInt(Sal);  //Sets salary to a user defined value
			return true;                     //returns true if salary is set properly
		}
		catch (NumberFormatException nfe)  //Cathes an exception, id the salary is not in a proper format
		{
			return false;
		}
	}

	Boolean setEmail(String email) throws MalformedEmailAddress
	{
		int At = 0; //defaults to not found
		int Period = 0; //defaults to not found
		for(int i = 0; i < email.length(); i ++)
		{
			if(email.charAt(i) == '@')                 //Locates the last at in the email XXXXX'@'XXXXX.XXX
			{
				At = i;
			}
			if(email.charAt(i) == '.')                 //locates the last period in the email address  XXXXX@XXXX'.'XXX
			{
				Period = i;
			}
		}

		if((At < Period) && (At !=0 && Period != 0))    //Makes sure that the @ symbol comes before the period xx@xx.xx
		{                                               //periods in the first part won't affect this
			return true;                                //It make sure @ is not first @xxx.xxx
		}                                               //If it works, then the method returns true.
		else
		{
			throw new MalformedEmailAddress(email);     //Throws exception when email is malformed.\
		}
	}
	
	
/*This method determines the hacker name of the user. The method generates a hacker name by adding a random number
 * to the ascii value of each characters in the user name. The random number is between 0 and the users age.*/
	void setHackerName()
	{
		String hacker = "";
		String[] hack = new String[name.length()+1];  //Array that holds potential hacker names.
		int Ascii;
		int n = rand.nextInt(age);
		int i;
		for(i = 0; i < name.length(); i++)            //For loop that changes each character in the users name
		{
			Ascii = (int)name.charAt(i);
			hack[i] = database(Ascii);                //Adds potential hackername to hack array
			hacker =hacker + (char)(Ascii + n);
		}
		hack[i] = hacker;
		String hackerName = hack[rand.nextInt(name.length())+0];
		System.out.println("Hacker Name: " + hackerName);
	}

/*This is a method that contains many predefined hacker names. The ascii value of the characters of the users name
 * will determine what hacker names the user may recieve. It uses simple if-else statements and can be expanded.*/
	public String database(int i)
	{

		if(30<= i && i < 40)  return "Acid BuRn";
		else if(40 <= i && i < 50) return "Crash OveRide";
		else if(50 <= i && i < 60) return "rE-BoOt";
		else if(60 <= i && i < 70) return "AlPha-KiLLer";
		else if(70 <= i && i < 80) return "Ceaser";
		else if(80 <= i && i < 90) return "God";
		else if(90 <= i && i < 100) return "Virus";
		else if(100 <= i && i < 110) return "Plague";
		else if(110 <= i && i < 120) return "De-FRag";
		else if(120 <= i && i < 130) return "PipPy-loNgstoCkinG";
		else return "HackerMan";


	}
}
