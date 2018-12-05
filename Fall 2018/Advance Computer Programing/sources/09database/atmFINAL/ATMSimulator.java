import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
   A text-based simulation of an automatic teller machine
*/
public class ATMSimulator
{ 
   Bank theBank;
	ATM theATM; 
	Scanner in;
	Connection conn;
   Statement stat;
	ResultSet result;
	ResultSetMetaData rsm;
		
   public ATMSimulator(String props) throws Exception
	{
      theBank = new Bank();
      theATM = new ATM(theBank);
      in = new Scanner(System.in);
		SimpleDataSource.init(props);
      conn = SimpleDataSource.getConnection();
	}

   public void createCustomers()
	{
      try
      {
         stat = conn.createStatement();
			try
			{
			  stat.execute("DROP TABLE BANKCUSTOMER");      
			}
			catch(Exception e)
			{ System.out.println("no bank customer table to drop"); }
			
			stat.execute("CREATE TABLE BANKCUSTOMER (CUSTOMER_NUMBER INTEGER, PIN INTEGER, CHECKING_ACCOUNT_NUMBER INTEGER, SAVINGS_ACCOUNT_NUMBER INTEGER)");
         stat.execute("INSERT INTO BANKCUSTOMER VALUES (1, 1234, 10, 100)");
         stat.execute("INSERT INTO BANKCUSTOMER VALUES (2, 2345, 20, 200)");
			stat.execute("INSERT INTO BANKCUSTOMER VALUES (3, 3456, 30, 300)");
		}
		catch(Exception e)
		{ System.out.println("exception in create customers"); };
		System.out.println("created customer table");	  
	}
 
   public void showCustomers() throws Exception
	{
	  try
	  {
	    result = stat.executeQuery("SELECT * FROM BANKCUSTOMER"); 
	    System.out.println("# pin check sav");
	    rsm = result.getMetaData();
	    int cols = rsm.getColumnCount();
	    while(result.next())
	    {
		   for(int i = 1; i <= cols; i++)
           System.out.print(result.getString(i)+" ");
         System.out.println("");      
	    } 
	    System.out.println("finished listing customers");	
	  }		
	  catch(Exception e)
		{ System.out.println("exception in show Customers"); };
	  
	}
 
   public void createAccounts()
	{
	  try {
	       try{  
			  stat.execute("DROP TABLE ACCOUNT"); 
          }
			 catch(Exception e)
			 {System.out.println("no table account to drop");}  
	      stat.execute("CREATE TABLE ACCOUNT (ACCOUNT_NUMBER INTEGER, BALANCE DECIMAL(10,2))");
 			System.out.println("create table account");
         stat.execute("INSERT INTO ACCOUNT VALUES (10, 12.89)");
         stat.execute("INSERT INTO ACCOUNT VALUES (20, 123.89)");
         stat.execute("INSERT INTO ACCOUNT VALUES (30, 1234.89)");
         stat.execute("INSERT INTO ACCOUNT VALUES (100, 2234.89)");
         stat.execute("INSERT INTO ACCOUNT VALUES (200, 22234.89)");
         stat.execute("INSERT INTO ACCOUNT VALUES (300, 33234.89)");
		}
	   catch(Exception e)
		{ System.out.println("exception in create Accounts"); };        
	    System.out.println("finished creating accounts");	
	}
	
	public void showAccounts()
	{
	   try
		{
		  result = stat.executeQuery("SELECT * FROM ACCOUNT"); 
		  System.out.println("after ACCOUNT inserts");
		  rsm = result.getMetaData();
		  int cols = rsm.getColumnCount();
		  while(result.next())
	     {
			  for(int i = 1; i <= cols; i++)
             System.out.print(result.getString(i)+" ");
           System.out.println("");      
	     }
	   }
		catch(Exception e)
		{ System.out.println("exception in show Accounts"); };
	}
	
	public void processTransactions() throws Exception
	{
	    boolean keepgoing = true;
		while (keepgoing)
      {
         int state = theATM.getState();
         if (state == ATM.START)
         {
            System.out.print("Enter customer number: ");
            int number = in.nextInt();
            theATM.setCustomerNumber(number);            
         }
         else if (state == ATM.PIN)
         {
            System.out.print("Enter PIN: ");
            int pin = in.nextInt();
            theATM.selectCustomer(pin);
         }
         else if (state == ATM.ACCOUNT)
         {
            System.out.print("A=Checking, B=Savings, C=Quit: ");
            String command = in.next();
            if (command.equalsIgnoreCase("A"))
               theATM.selectAccount(ATM.CHECKING);
            else if (command.equalsIgnoreCase("B"))
               theATM.selectAccount(ATM.SAVINGS);
            else if (command.equalsIgnoreCase("C"))
               theATM.reset();
            else
               System.out.println("Illegal input!");                        
         }
         else if (state == ATM.TRANSACT)
         {
            System.out.println("Balance=" + theATM.getBalance());
            System.out.print("A=Deposit, B=Withdrawal, C=Cancel: ");
            String command = in.next();
            if (command.equalsIgnoreCase("A"))
            {
               System.out.print("Amount: ");
               double amount = in.nextDouble();
               theATM.deposit(amount);
               theATM.back();
            }
            else if (command.equalsIgnoreCase("B"))
            {
               System.out.print("Amount: ");
               double amount = in.nextDouble();
               theATM.withdraw(amount);
               theATM.back();
            }
            else if (command.equalsIgnoreCase("C"))
				{
				   keepgoing = false;
               theATM.back();
            }  
	         else
               System.out.println("Illegal input!");                                    
         }         
      }
		try {  
		     stat.execute("DROP TABLE BANKCUSTOMER"); 
			  stat.execute("DROP TABLE ACCOUNT");
         }
	   catch (Exception e)
		{ 
			System.out.println("drop failed"); 
		}
		finally
		{
		   conn.close();
			System.out.println("dropped Table BANKCUSTOMER, closed connection and ending program"); 
		} 

	}
 
   public static void main(String[] args)
         throws Exception
   {  
      if (args.length == 0)
         System.out.println("Usage: ATMTester propertiesFile");

       ATMSimulator atms = new ATMSimulator(args[0]);
		 atms.createCustomers();
		 atms.showCustomers(); 
		 atms.createAccounts();
		 atms.showAccounts();    
		 atms.processTransactions();							  	
   }
}

