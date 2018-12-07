import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * OMAR GARCIA
 * Project 5
 * Advanced Computer Programing
 */
public class Service implements Runnable
{
    private Socket client;
    private Scanner in;
    private PrintWriter out;

    public  Service(Socket s)
    {
        client = s;
    }

    public void run()
    {
        try
        {
            try
            {
                in = new Scanner(client.getInputStream());
                out = new PrintWriter(client.getOutputStream());
                doService();
            }
            finally
            {
                client.close();
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    public void doService() throws IOException
    {
        while (true) {
            if (!in.hasNext()) {
                return;
            }
            String command = in.next();
            if (command.equals("QUIT"))
                return;
            else {
                System.out.println(command);
            }
        }
    }    

}


