/////////////////////////////////////////////
//  Program name : BankAccount
//  Programmer: John Coffey
//  Modification data: 1/21/2004
//  Purpose:
//    This class defines a bank account class in order to . . 
//*****************************************//
public class BankAccount
{
   // Attributes or Instance fields
	double balance;
	int number;
   BankAccount nextBankAccount;

  // Methods  
  public BankAccount(int num)
  {
    balance = 0;
	 number = num;
	 nextBankAccount = null;
  } 
  
  public BankAccount(double initialBalance, int num)
  {
    balance = initialBalance;
	 number = num;
	 nextBankAccount = null;
  } 
    
  public void deposit(double amount)
  {
    balance = balance + amount;
  }
   
  public void withdraw(double amount)
  {
    if(balance >= amount)  
	   balance = balance - amount; 
    else
	   System.out.println("Insufficient funds for withdrawal");
  } 
  
  public double getBalance()
  {
	  return balance;
  }
  public int getNumber()
  {
	  return number;
  }

}


