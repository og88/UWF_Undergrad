import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
   This program tests the bank server.
*/
public class BankClient
{
   public static void main(String[] args) throws IOException
   {
      final int SBAP_PORT = 8888;
      Socket s = new Socket("localhost", SBAP_PORT);
      InputStream instream = s.getInputStream();
      OutputStream outstream = s.getOutputStream();
      Scanner in = new Scanner(instream);
      PrintWriter out = new PrintWriter(outstream); 
      Scanner con = new Scanner(System.in);
		String com = con.nextLine();
		System.out.print("Enter command like: DEPOSIT 3 1000 or quit to quit");
		while(! com.equals("quit"));
		{
         System.out.print("Sending: " + com);
         out.print(com);
         out.flush();
         String response = in.nextLine();
         System.out.println("Receiving: " + response);
    		System.out.print("Enter command like: DEPOSIT 3 1000 or quit to quit");
	   	String com = con.nextLine();	         
		}
      s.close();
   }
}





