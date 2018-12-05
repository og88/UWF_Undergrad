import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
   Executes Simple Bank Access Protocol commands
   from a socket.
*/
public class BankService implements Runnable
{
   /**
      Constructs a service object that processes commands
      from a socket for a bank.
      @param aSocket the socket
      @param aBank the bank
   */
   public BankService(Socket aSocket, Bank aBank)
   {
      s = aSocket;
      bank = aBank;
   }

   public void run()
   {
      try
      {
         try
         {
            in = new Scanner(s.getInputStream());
            out = new PrintWriter(s.getOutputStream());
            doService();            
         }
         finally
         {
            s.close();
         }
      }
      catch (IOException exception)
      {
         exception.printStackTrace();
      }
   }

   /**
      Executes all commands until the QUIT command or the
      end of input.
   */
   public void doService() throws IOException
   {      
      while (true)
      {  
         if (!in.hasNext()) return;
         String command = in.next();
         if (command.equals("QUIT")) return;         
         else executeCommand(command);
      }
   }

   /**
      Executes a single command.
      @param command the command to execute
   */
   public void executeCommand(String command)
   {
		System.out.println("Command at start of executeCommand is " + command);
	   double amount;
		int account;
		try
		{
        account = in.nextInt();
      }
		catch(Exception e)
		{
		  System.out.println("Expected a number here, possibly junk from bad command");   
		  return;
		}
		if (command.equals("DEPOSIT"))
      {
         amount = in.nextDouble();
         bank.deposit(account, amount);
      }
      else if (command.equals("WITHDRAW"))
      {
         amount = in.nextDouble();
         bank.withdraw(account, amount);
      }      
      else if (!command.equals("BALANCE"))
      {
         out.println("Invalid command");
         out.flush();
         return;
      }
		System.out.println("Transaction on account " + account + " is " + command);
      out.println(account + " " + bank.getBalance(account));
      out.flush();
   }

   private Socket s;
   private Scanner in;
   private PrintWriter out;
   private Bank bank;
}
