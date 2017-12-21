/**
 * Accounts class
 * 
 * @author omarg
 * 
 *the Account class is a holder class. It holds multiple classes in an array
 *It also has its own methods that can be called
 *The account class holds a company name, address, and list of users
 *The information can be altered by calling methods
 */

public class Accounts
{
	String companyName;
	String companyAddress;
	int numOfelements = 0;        
	User[] user;
	int NOTFOUND = -1;
	int arraySize;

	/**
	 * class constructor for accounts
	 * sets all parameters companyName and companyAddress to a default ""
	 * sets the user array to a default size 10
	 */
	Accounts()
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
	Accounts(String CompanyName, String CompanyAddress, int Size)
	{
		companyName = CompanyName;
		arraySize = Size;
		companyAddress = CompanyAddress;
		user = new User[arraySize];
	}
	
	/**
	 * simple method that returns the company name.
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
	 * simple method that returns the company address
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
	 * addUser is a method that takes in a User class
	 * The method then checks with the global variable arraySize and numOfelements
	 * to make sure the user array is not full
	 * If the array is full, the program warns the user and the action is canceled
	 * @param usr is a User class that is inserted into the array
	 * it contains the information the user is trying to push into the array
	 * numOfelements guides the program to where the next class should go
	 * the program should add a new class in the next available space and
	 * most not replace any previus value
	 */
	public void addUser(User usr)
	{
		if (numOfelements == arraySize)
		{
		System.out.println("User array is full");	
		}
		else
		{
		user[numOfelements] = usr;
		numOfelements++;
		}
	}
	
	/**
	 * the method getUser retrieve the user that is requested by the user
	 * it implements the findUser method found below
	 * the findUser method returns the number index of where the user is located
	 * this method uses the index number to find the actual user class in the user array
	 * @param usrName is the user defined username. it is pushed into the method as a string
	 * the string is then pushed into the the findUser as a string
	 * Int index is the numerical location of the user class in the array. if the user does not exist,
	 * the index is set as -1 which means not found. A if loop is used to make sure the userName was found.
	 * @return returns the class that is requested by the user
	 */
	public User getUser(String usrName)
	{
		int index;
		index = findUser(usrName);
		if (index == -1)
		{
		System.out.println(usrName + " does not exist.");
		return null;
		}
		else
		{
		return user[index];
		}
	}
	
	/**
	 * the deleteUser method deletes a user class that is requested by the user
	 * the method takes in a string. the string is pushed into the findUser method to find the 
	 * location of the user. 
	 * the int value counter is used to keep track of the array list
	 * it is given the value of the index location. if the username does not exist,
	 * it will be set to -1 and give the user a warning
	 * the method shifts all the values after the requested username down, which overwrites
	 * the requested username. The numOfelements variable is reduced by one since we deleted an element
	 * @param usrName is the username requested by the user 
	 */
	public void deleteUser(String usrName)
	{
		int counter;
		counter = findUser(usrName);
		if(counter == -1)
		{
		System.out.println(usrName + " does not exist");
		}
		else
		{
			numOfelements = numOfelements - 1;
			
		while(counter < numOfelements)
		{
			user[counter] = user[counter + 1];
			counter++;
		}
		}
		
		
	}
	
	/**
	 * the findUser method is a very important method. It finds the numerical location of the user
	 * in the user array. the method takes in a String and uses a for loop to find the username
	 * to accomplish this, the method use a variable called counter. the variable starts at 0, 
	 * since this is the index value of the first element in an array. The counter goes up by one until it finds the 
	 * correct username. the counter variable is then returned
	 * @param usrName is the string that contains the user defined username
	 * @return returns the location of the requested username
	 */
	private int findUser(String usrName)
	{
		int counter; 
		for(counter = 0; counter < numOfelements; counter ++)
		{
			if(user[counter].username == usrName)
			{
				break;
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
	 * it users the acount information than uses a for loop to add each users toString methods
	 * the string sent is a variable that holds the string information
	 */
	public String toString()
	{
		String sent;
		sent = getCompanyName() + " " + getCompanyAddress() + "\n\n" + 
		"Username Encrypted Clear Key\n-----------------------------------\n";
		for(int count = 0; count < numOfelements; count++)
		{
		sent = sent + user[count].toString();	
		}
		return sent;
	}
}
