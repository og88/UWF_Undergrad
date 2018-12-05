import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
   This program tests the bank server.
*/
public class BankClientWithdraw implements Runnable
{

  public void run()
  {
      final int SBAP_PORT = 8888;
      Socket s = new Socket("localhost", SBAP_PORT);
      InputStream instream = s.getInputStream();
      OutputStream outstream = s.getOutputStream();
      Scanner in = new Scanner(instream);
      PrintWriter out = new PrintWriter(outstream); 
  
    for(int i = 0; i < 50; i++)
    {
      
      command = "WITHDRAW 3 500\n";
      System.out.print("Sending: " + command);
      out.print(command);
      out.flush();
      response = in.nextLine();
      System.out.println("Receiving: " + response); 
  
    }
	 
	       s.close();
  }




   public static void main(String[] args) throws IOException
   {
      BankClientWithdraw w = new BankClientWithdraw();
      Thread t = new Thread(w);
		t.start();  
	}
}





