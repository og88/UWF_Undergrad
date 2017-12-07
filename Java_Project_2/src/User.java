
public class User extends Account
{
	String username;
	String fullName;
	int deptCode;

	/**
	 * default Constructor Sets all Strings to ""
	 * and all ints to 0
	 */
	User()
	{
		this("","",0);
	}
	
	/**
	 * 
	 * @param user sets user name for the account
	 * @param name sets the full name of the owner of the account
	 * @param code sets the department code the account belongs tos
	 */
	User(String user, String name, int code)
	{
		super();
		this.username = user;
		this.fullName = name;
		this.deptCode = code;
	}

	/**
	 * 
	 * @return Returns the username of the account
	 */
	String getUserName()
	{
		return username;
	}
/**
 * 
 * @param name Sets the usename to a user defined String
 */
	void setUserName(String name)
	{
		username = name;
	}
/**
 * 
 * @return Returns the full name of the owner of the account
 */
	String getFullName()
	{
		return fullName;
	}
	
	/**
	 * 
	 * @param name Sets users full name to name
	 */
void setFullName(String name)
{
	fullName = name;
}
	/**
	 * 
	 * @return Returns the department code of the account
	 */
	int getDeptCode()
	{
		return deptCode;
	}
	
	/**
	 * 
	 * @param code set department code to value code
	 */
	void setDeptCode(int code)
	{
		deptCode = code;
	}
}
