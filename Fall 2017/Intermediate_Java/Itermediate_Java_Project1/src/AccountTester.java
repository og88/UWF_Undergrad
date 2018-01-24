/**
 * This is a simple test class
 * This class ceates each type of class and test there methods
 * 
 */
public class AccountTester
{
	
public static void main(String[] args)
{
	Accounts acct = new Accounts("UWF CP DEPT","\n1342 Pine DR. \nPalm Beach, Fl 32585",10);
	System.out.println(acct.getCompanyName());
	System.out.println(acct.getCompanyAddress());
	acct.setComapnyName("USF SD Dept");
	acct.setCompanyAddress("1342 Pine DR. , Pensacola Florida");
	
	User user1 = new User("user1","password","","house");
	User user2 = new User("user2","drowssap","","stepp");
	User user3 = new User("user3","wordpass","","drown");
	User user4 = new User("user4","ssapdrow","","T@1ks");
	User user5 = new User("user5","21abc!56","","Sky3D");
	User user6 = new User("user6","!@#$%^&*","","51V8e");
	User user7 = new User("user7","A!1B3#mt","","56k*/");
	System.out.println(user1.getUserName());
	System.out.println(user2.getClearPassword());
	System.out.println(user3.getEncryptedPassword());
	System.out.println(user4.getKey());
	System.out.println(user5.toString());
	
	acct.addUser(user1);
	acct.addUser(user2);
	acct.addUser(user3);
	acct.addUser(user4);
	acct.addUser(user5);
	acct.addUser(user6);
	acct.addUser(user7);
	System.out.println(acct.user[0].toString());
	System.out.println(acct.user[5].toString());
	
	user2.setClearPassword("validUS!");
	user2.setKey("somes");
	user2.setUserName("helpd");
	System.out.println(acct.toString());
	acct.getUser("user5");
	acct.deleteUser("user56");
	acct.deleteUser("user5");
	acct.getUser("user5");
	System.out.println(acct.toString());
	user2.setEncryptedPassword("ecrd_psd");
	System.out.println(acct.toString());
}

}