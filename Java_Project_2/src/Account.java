
public class Account 
{
	int accountid;
	String clearPassword;
	String encryptedPassword;
	String key;
	static int nextIDNum = 1000;
	/**
	 * default class constructor for User
	 * set parameters clearPassword and Key to default ""
	 * The constructor adds one to nextID, since the previous nextID is now taken up
	 */
	Account()
	{
		this("","");
		Account.nextIDNum = accountid + 1;
	}

	/**
	 * user defined class construct
	 * @param ClearPassword sets the clearPassword field to a user defined password
	 * @param Key sets key value to the user defined key value
	 * sets accountid to the next available id
	 * sets the encryptedPassword field to a default ""
	 * nextId is incremeted by one since the previous id is now taken
	 * the clearPassword is then encrypted
	 */
	Account(String ClearPassword, String Key)
	{
		this.accountid = nextIDNum;
		this.clearPassword = ClearPassword;
		this.encryptedPassword = "";
		this.key = Key;
		Account.nextIDNum =  accountid + 1;
		encrypt();
	}

	/**
	 * Simple method to return accountid
	 * @return returns global variable accountid
	 */
	public int getAccountId()
	{
		return accountid;
	}

	/**
	 * sets the global variable accountid to a user defined id
	 * @param ID this holds the user defined id
	 */
	public void setAccountId(int ID)
	{
		accountid = ID;	
	}

	/**
	 * the method returns the global variable clearPassword
	 * @return returns the clearPassword variable
	 */
	public String getClearPassword()
	{
		return clearPassword;
	}

	/**
	 * sets the global clearPassword to a user defined string
	 * @param clearPass holds the value for the user defined password
	 * the method then calls the encrypt method to encrypt the password
	 */
	public void setClearPassword(String clearPass)
	{
		clearPassword = clearPass;
		encrypt();
	}

	/**
	 * this method returns the encryptedPassword String
	 * @return returns the encrypted password
	 */
	public String getEncryptedPassword()
	{
		return encryptedPassword;
	}


	/**
	 * method that returns the key string
	 * @return returns the key string
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * sets the key global variable to a user defined key string
	 * @param key1 the user defined string
	 * It then encrypts the clearPassword using the new key
	 */
	public void setKey(String key1)
	{
		key = key1;	
		encrypt();
	}

	/**
	 * @return This method returns the next available ID
	 */
	public int getNextID()
	{
		return nextIDNum;
	}

	/**
	 * @param ID This method sets the nextIf to a user defined id
	 */

	public void setID(int ID)
	{
		nextIDNum = ID;
	}



	/**
	 * the method encrypt is very important. It encrypts the users clear password
	 * the method deconstructs the clearPassword and key strings into chars
	 * asciiPass is the ascii value of the current password char
	 * asciiKey is the key value of the current key char
	 * passwordLength is the number of char in the password String
	 * keyLength is the number of char in the key String
	 * the encrypted password is set to a default ""
	 * int y is the counter for the asciikey. It is reseted to 0 if the y value is greater than the key size.
	 * the password can be longer than the key, so it must be accomodated for it
	 * int x is in a for loop. It is in a counter for the asciipass variable
	 * the sciiKey variable is added into the asciiPass variable to create an encryped ascii key
	 * the encrypted ascii value replaces the asciiPass value
	 * the asciiPass value is then type casted and added to the encryptedPassword String
	 */
	public void encrypt()
	{
		int asciiPass;
		int asciiKey;
		int passwordLength = clearPassword.length();
		int y = 0;
		int keylength = key.length();
		encryptedPassword = "";
		if(key != "")
		{
			for (int x = 0; x < passwordLength; x++)
			{
				asciiPass = (int)clearPassword.charAt(x);
				asciiKey = (int)key.charAt(y);
				asciiPass = asciiKey + asciiPass;
				y++;

				if (y >= keylength)
				{
					y = y - key.length();
				}
				encryptedPassword = encryptedPassword + (char)asciiPass;	
			}
		}
	}


	/**
	 * this method organizes all the user information into a String variable
	 * string sent(short for sentence) holds the sentence
	 */
	public String toString()
	{
		String sent;
		sent = accountid + "  " + encryptedPassword +"  "+ clearPassword +"  "+ key + "\n";
		return sent;
	}
}
