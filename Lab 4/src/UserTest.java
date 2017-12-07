
import java.util.Scanner;

public class UserTest 
{
	public static void main(String[] args)
	{
		Boolean check = false;
		User usr = new User();
		Scanner scr = new Scanner(System.in);

		System.out.println("Enter full name");
		String name = scr.nextLine();
		usr.setName(name);
		System.out.println("Enter age int number");
		String age = scr.nextLine();
		usr.setAge(Integer.parseInt(age));

		while(check == false)
		{
			System.out.println("Enter full email");
			String Email = scr.nextLine();
			try
			{
				check = usr.setEmail(Email);

			}catch(MalformedEmailAddress e)
			{
				System.out.println(e.getError());
			}
		}

		System.out.println("Enter salary");
		String salary = scr.nextLine();

		while(check == false)
		{
			if(usr.setSalary(salary))
			{
				check = true;		
			}
			else
			{
				check = false;
			}
		}
		scr.close();
		usr.setHackerName();


	}

}
