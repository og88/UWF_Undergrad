import java.util.LinkedList;
import java.util.ListIterator;

/**
   A program that builds a list of BankAccounts
*/
public class ListTester
{  

BankAccount b1, b2;

   public void createAccounts()
	{
	  b1 = new BankAccount(1000,1);
	  b2 = b1;
	  b2.deposit(1000);
	  System.out.println(" from b1 balance = " + b1.getBalance());
	  System.out.println(" from b2 balance = " + b2.getBalance());
	  b2 = new BankAccount(450,2);
	  b1.nextBankAccount = b2;
	}

  public void showAccounts()
{
  BankAccount current = b1;
  while(current != null)
  {
  	  System.out.println(" account = " + current.getNumber());
	  System.out.println(" balance = " + current.getBalance());  
     current = current.nextBankAccount;
  }
}

   public static void main(String[] args)
   {  
    ListTester lt = new ListTester();
	 lt.createAccounts();
	 lt.showAccounts();
	}
}
