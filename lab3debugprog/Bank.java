/**
   This bank contains a collection of bank accounts.
*/
public class Bank
{   
   /**
   An Array containing Bank Account objects
   */
   public BankAccount[] accounts;
   
   /**
   Counts number of non-null Bank Account
   Objects in accounts array
   */
   private int numOfAccounts;

   /**
      Constructs a bank with no bank accounts.
   */
   public Bank()
   {
      accounts = new BankAccount[50];
      numOfAccounts = 0;
   }

   /**
      Adds an account to this bank.
      @param a the account to add
   */
   public void addAccount(BankAccount a)
   {
      accounts[numOfAccounts] = a;
      numOfAccounts ++;
   }
   
   /**
      Gets the sum of the balances of all accounts in this bank.
      @return the sum of the balances
   */
   public double getTotalBalance()
   {
      double total = 0;
      for(int i = 0; i < numOfAccounts; i++)
      {
         total = total + accounts[i].getBalance();
      }
      return total;
   }

   /**
      Counts the number of bank accounts whose balance is at
      least a given value.
      @param atLeast the balance required to count an account
      @return the number of accounts having least the given balance
   */
   public int count(double atLeast)
   {
      int matches = 0;
      for (int i = 0; i < numOfAccounts; i++)
      {
         if (accounts[i].getBalance() >= atLeast)
        	 {
        	 matches++; // Found a match
        	 }
      }
      return matches;
   }

   /**
      Finds a bank account with a given number.
      @param accountNumber the number to find
      @return the account with the given number, or null if there
      is no such account
   */
   public BankAccount find(int accountNumber)
   {
      for(int i = 0; i < numOfAccounts; i++)
      {
         if (accounts[i].getAccountNumber() == accountNumber) // Found a match
            {
        	 return accounts[i];
            }
      } 
      return null; // No match in the entire array list
   }

   /**
      Gets the bank account with the largest balance.
      @return the account with the largest balance, or null if the
      bank has no accounts
   */
   public BankAccount getMaximum()
   {
      if (numOfAccounts == 0) 
      {
         return null;
      }
      BankAccount largestYet = accounts[0];
      for (int i = 1; i < numOfAccounts; i++) 
      {
         BankAccount a = accounts[i];
         if (a.getBalance() > largestYet.getBalance())
            largestYet = a;
      }
      return largestYet;
   }

   
}
