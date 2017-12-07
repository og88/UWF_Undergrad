/**
 * User class
 * 
 * @author omarg
 *
 *This is a user class. it creates users and holds their information
 *The class contains methods that can change or retrieve values
 */

public class User
{
	String username;
	String clearPassword;
	String encryptedPassword;
	String key;
	
/**
 * defualt class constructor for User
 * set parameters username, clearPassword, EncryptedPassword, and Key to default ""
 */
	User()
	{
	   this("","","","");
	}
	
	/**
	 * user defined class construct
	 * @param userName sets username value to a user defined username value
	 * @param ClearPassword sets the clearPassword field to a user defined password
	 * @param EncryptedPassword sets the encryptedPassword field to a user defined password
	 * @param Key sets key value to the user defined key value
	 */
	User(String userName, String ClearPassword, String EncryptedPassword, String Key)
	{
	this.username = userName;
	this.clearPassword = ClearPassword;
	this.encryptedPassword = EncryptedPassword;
	this.key = Key;
	encrypt();
	}

	/**
	 * Simple method to return username
	 * @return returns global variable username
	 */
	public String getUserName()
	{
		return username;
	}
	
	/**
	 * sets the global variable username to a user defined username
	 * @param user this holds the user defined username
	 */
	public void setUserName(String user)
	{
		username = user;	
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
	 * this method sets the encryptedPassword variable to a user defined string
	 * the method does not call the encrypt method since it is the encrypted password
	 * @param encryptPass is the user defined String
	 */
	public void setEncryptedPassword(String encryptPass)
	{
		encryptedPassword = encryptPass;	
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
	 */
	public void setKey(String key1)
	{
		key = key1;	
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
		int passwordLength = clearPassword.length(); //
		int y = 0;
		int keylength = key.length();
		encryptedPassword = "";
	
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
	
	/**
	 * this method organizes all the user information into a String variable
	 * string sent(short for sentence) holds the sentence
	 */
	public String toString()
	{
	String sent;
	sent = username + "  " + encryptedPassword +"  "+ clearPassword +"  "+ key + "\n";
	return sent;
	}
	
	
}
