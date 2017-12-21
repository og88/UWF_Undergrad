
public class Company_Accounts
{
	String companyName;
	String companyAddress;
	int numOfelements = 0;        
	Account[] accounts;
	int NOTFOUND = -1;
	int arraySize;

	/**
	 * class constructor for accounts
	 * sets all parameters companyName and companyAddress to a default ""
	 * sets the user array to a default size 10
	 */
	Company_Accounts()
	{
		this("","", 10);
		arraySize = 10;

	}

	/**
	 * 
	 * @param CompanyName sets the global variable companyName to a user defined String
	 * @param CompanyAddress sets the global variable companyAddress to a user defined String
	 * @param size sets the global variable array user to a user defined int.
	 * @param size will also set the global variable arraySize to the user defined size 
	 */
	Company_Accounts(String CompanyName, String CompanyAddress, int Size)
	{
		companyName = CompanyName;
		arraySize = Size;
		companyAddress = CompanyAddress;
		accounts = new Account[arraySize];
	}

	/**
	 * @return simple method that returns the company name.
	 */

	public String getCompanyName()
	{
		return companyName;
	}

	/**
	 * this method sets the companyName to a user defined string
	 * it is used when the user wants to change the current company name
	 * @param name is the variable that replaces the value of the @param companyName
	 */
	public void setComapnyName(String name)
	{
		companyName = name;
	}

	/**
	 * @return simple method that returns the company address
	 */
	public String getCompanyAddress()
	{
		return companyAddress;
	}

	/**
	 * this is a method that replaces the current company address with a new user defined string
	 * @param address holds the user defined string that replaces the current address
	 */
	public void setCompanyAddress(String address)
	{
		companyAddress = address;
	}

	/**
	 * addUser is a method that takes in a User or Bot classes
	 * The method then checks with the global variable arraySize and numOfelements
	 * to make sure the user array is not full
	 * If the array is full, the program warns the user and the action is canceled
	 * @param acc is a Account class that is inserted into the array
	 * it contains the information the user is trying to push into the array
	 * numOfelements guides the program to where the next class should go
	 * the program should add a new class in the next available space and
	 * most not replace any previous value
	 */
	void addAccount(Account acc)
	{
		if (numOfelements == arraySize)
		{
			System.out.println("User array is full");	
		}
		else
		{
			accounts[numOfelements] = acc;
			numOfelements++;
		}
	}

	/**
	 * the method getAccount retrieve the accountid that is requested by the user
	 * it implements the findAccount method found below
	 * the findAccount method returns the number index of where the user is located
	 * this method uses the index number to find the actual user/bot class in the account array
	 * @param accID is the user defined id. it is pushed into the method as a string
	 * the string is then pushed into the the findAccount as a string
	 * Int index is the numerical location of the user class in the array. if the user does not exist,
	 * the index is set as -1 which means not found. A if loop is used to make sure the id was found.
	 * @return returns the class that is requested by the user
	 */
	public Account getAccount(int accID)
	{
		int index;
		index = findAccount(accID);
		if (index == -1)
		{
			System.out.println(accID + " does not exist.");
			return null;
		}
		else
		{
			return accounts[index];
		}
	}

	/**
	 * the deleteAccount method deletes a account class that is requested by the user
	 * the method takes in a string. the string is pushed into the findAccount method to find the 
	 * location of the account. 
	 * the int value counter is used to keep track of the array list
	 * it is given the value of the index location. if the account does not exist,
	 * it will be set to -1 and give the user a warning
	 * the method shifts all the values after the requested account down, which overwrites
	 * the requested account. The numOfelements variable is reduced by one since we deleted an element
	 * @param accID is the id requested by the user 
	 */
	public void deleteAccount(int accID)
	{
		int counter;
		counter = findAccount(accID);
		if(counter == -1)
		{
			System.out.println(accID + " does not exist");
		}
		else
		{
			numOfelements = numOfelements - 1;

			while(counter < numOfelements)
			{
				accounts[counter] = accounts[counter + 1];
				counter++;
			}
			System.out.print(accID + " has been deleted");
		}


	}

	/**
	 * the findAccount method is a very important method. It finds the numerical location of the account
	 * in the accounts array. the method takes in a String and uses a for loop to find the id
	 * to accomplish this, the method use a variable called counter. the variable starts at 0, 
	 * since this is the index value of the first element in an array. The counter goes up by one until it finds the 
	 * correct id. the counter variable is then returned
	 * @param accID is the string that contains the user defined account id
	 * @return returns the location of the requested account
	 */
	private int findAccount(int accId)
	{
		int counter; 
		for(counter = 0; counter < numOfelements; counter ++)
		{
			if(accounts[counter].accountid == accId)
			{
				break;
			}
			else
			{

			}
		}
		if(counter == numOfelements)
		{
			return NOTFOUND;
		}
		else
		{
			return counter;
		}
	}

	/**
	 * this method creates a string method with all the accounts information
	 * it uses the account information than uses a for loop to add each account toString methods
	 * the string sent is a variable that holds the string information
	 */
	public String toString()
	{
		String sent;
		sent = getCompanyName() + " " + getCompanyAddress() + "\n\n" + 
				"Username Encrypted Clear Key\n-----------------------------------\n";
		for(int count = 0; count < numOfelements; count++)
		{
			sent = sent + accounts[count].toString();	
		}
		return sent;
	}
}
