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
    private static int ID;
    private Socket client;
    private static Scanner in;
    private static PrintWriter out;

    public  Service(Socket s, int id)
    {
        client = s;
        ID = id;
        Game.game();
    }

    public void run()
    {
        try
        {
            try
            {
                in = new Scanner(client.getInputStream());
                out = new PrintWriter(client.getOutputStream());
                start(ID);
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



    public static void main(String[] args){
        start(1);
    }

    public static void start(int id) {
        ID = id;
        while (Game.getWinner() == 0) {
            if (!in.hasNext()) {
                return;
            }
                System.out.print("Enter a string : ");
                String inputString = in.next();
                System.out.println(inputString);
                if (ID == Game.getTurn()) {
                    move(inputString);
                } else {
                    //System.out.println("not your turn yet");
                    out.println("not your turn yet\n");
                    out.flush();
                }
        }
    }

    public static void move(String moves)
    {
        int id = moves.charAt(0) - '0';
        int x = (int)moves.charAt(1)- '0';
        int y = (int)moves.charAt(2)- '0';
        ID = id;
        try {
            Game.lock();
            if (ID == Game.getTurn()) {
                out.println(messageHandler(Game.move(id, x, y)));
                out.flush();
            }
            else
            {
                out.println("not your turn");
                out.flush();
            }
        }
        finally
        {
            Game.unlock();
        }

    }

    public static String messageHandler(int e)
    {
       if(e == 0)
       {
           String win = "Winner is " + Game.getWinner();
          System.out.println(win);
          return(win);
       } else if(e == 1)
       {
           return "Invalid id";
       } else if(e == 2)
       {
           return "x value out of range";
       } else if(e == 3)
       {
           return "y value out of range";
       } else if(e == 4)
       {
           return "space is already taken";
       }
        return "Unknown error";
    }

}


