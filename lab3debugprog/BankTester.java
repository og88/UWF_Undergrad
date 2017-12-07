/**
   This program tests the Bank class.
*/
public class BankTester
{
   public static void main(String[] args)
   {
   try{
      Bank firstBankOfJava = new Bank();
      firstBankOfJava.addAccount(new BankAccount(1001, 20000));
      firstBankOfJava.addAccount(new BankAccount(1015, 10000));
      firstBankOfJava.addAccount(new BankAccount(1729, 15000));
      
      System.out.println("Total Balance is " +  firstBankOfJava.getTotalBalance() + " count " + firstBankOfJava.count(10000));
      BankAccount Max = firstBankOfJava.getMaximum();
      BankAccount Find = firstBankOfJava.find(1015);
      
      System.out.println("The max is " + Max.getAccountNumber() + " " + Max.getBalance() + "\n" 
      + Find.getAccountNumber() + " " + Find.getBalance() + "\n");
      
      System.out.println(firstBankOfJava.accounts[0].getAccountNumber() + " " + firstBankOfJava.accounts[0].getBalance());
      System.out.println(firstBankOfJava.accounts[1].getAccountNumber() + " " + firstBankOfJava.accounts[1].getBalance());
      System.out.println(firstBankOfJava.accounts[2].getAccountNumber() + " " + firstBankOfJava.accounts[2].getBalance() +"\n");
      firstBankOfJava.accounts[2].deposit(50000);
      firstBankOfJava.accounts[1].withdraw(5000);
      Max = firstBankOfJava.getMaximum();
      
      System.out.println( "The max is " + Max.getAccountNumber() + " " + Max.getBalance()+"\n");
      System.out.println("Total Balance is " +  firstBankOfJava.getTotalBalance() + " count " + firstBankOfJava.count(10000));
    }catch(Exception e)
    {
    System.out.println("Something broke, please fix me");
    }

   }
}
