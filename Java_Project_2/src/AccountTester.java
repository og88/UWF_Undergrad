import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

public class AccountTester
{
	public static void main(String[] args)
	{
		try {
			Company_Accounts account = new Company_Accounts("UWF CP DEPT","\n1342 Pine DR. \nPalm Beach, Fl 32585",10);
			URL path = AccountTester.class.getResource("commands.txt");
			File f = new File(path.getFile());
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String[] line;
			String line1;
			while((line1 = reader.readLine()) != null) 
			{
				line = line1.split(",");
				if(line[0].equals("u"))
				{
					int valid = check(line[4]);
					if(valid == 1)
					{
						User user1 = new User(line[1],line[2], Integer.parseInt(line[3]));
						user1.setClearPassword(line[4]);
						user1.setKey(line[5]);

						account.addAccount(user1);
					}
					else
					{
						System.out.println("Error: Password not valid for " + line[1]);
					}

				}
				else if(line[0].equals("b"))
				{
					int valid = check(line[5]);
					if(valid == 1)
					{
						Bot bot1 = new Bot( line[1], line[2], line[3], line[4]);
						bot1.setClearPassword(line[5]);
						bot1.setKey(line[6]);
						account.addAccount(bot1);
					}
					else
					{
						System.out.println("Error: Password not valid");
					}
				}
			}

			for(int i = 0; i < account.numOfelements; i++)
			{
				System.out.println(account.accounts[i].getAccountId()
						+ " " + account.accounts[i].getNextID() + " " + account.accounts[i].getEncryptedPassword());	
			}
			reader.close();
			System.out.print("\n");
			System.out.println(account.toString());
			account.deleteAccount(2000);
			account.deleteAccount(1002);
			System.out.print("\n\n");

			System.out.println(account.toString());


		}
		catch(Exception e)
		{
			System.out.println("Something went wrong");
		}
	}

	public static int check(String string) {
		int valid = 0;
		if(string.length() >= 8)
		{
			for(int i = 0; i < string.length(); i++)
			{
				switch (string.charAt(i)){
				case '#': valid = 1; 
				return valid;
				case '$': valid = 1;
				return valid;
				case '%': valid = 1;
				return valid;
				case '&': valid = 1; 
				return valid;
				}

			}
		}
		return valid;
	}

}
