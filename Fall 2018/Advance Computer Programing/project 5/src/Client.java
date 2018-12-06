import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * OMAR GARCIA
 * Project 5
 * Advanced Computer Programing
 */
public class Client {
    public static void main(String[] args) throws IOException
    {
        final int SBAP_PORT = 8888;
        Socket s = new Socket("127.0.0.1",SBAP_PORT);
        InputStream instream = s.getInputStream();
        OutputStream outstream = s.getOutputStream();
        Scanner in = new Scanner(instream);
        PrintWriter out = new PrintWriter(outstream);

        String command = "101\n";
        out.print(command);
        out.flush();
        String response = in.nextLine();
        System.out.println("Receiving: " + response);
    }
}
