import java.io.IOException;
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
    private Game game;

    public  Service(Socket s, Game g)
    {
        client = s;
        game = g;
    }


    public void run()
    {
        try
        {
            try
            {
                in = new Scanner(client.getInputStream());
                out = new PrintWriter(client.getOutputStream());
                game.setPlayer(game.getPlayer()+1);
                out.println(game.getPlayer());  //Let player known what number player they are
                out.flush();
                doService(game);
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

    public void doService(Game game) throws IOException
    {
        while (true) {
            if (!in.hasNext()) {

                return;
            }
            String command = in.nextLine();
            try {
                game.lock();
                if (command.equals("QUIT"))
                    return;
                else if (game.getPlayer() < 2) {
                    System.out.println("Need a second player to start");
                    out.println("Need a second player to start");
                    out.flush();
                } else if (game.getWinner() != 0) {
                    out.print(game.getWinner());
                    out.flush();
                    out.println(game.printTable());
                    out.flush();
                } else {
                    out.println(move(command, game));
                    out.flush();
                    out.println(game.printTable());
                    out.flush();
                }
            } finally{
                game.unlock();
            }
        }
    }


    public static String move(String moves, Game game)
    {

            int id = (int) moves.charAt(0) - '0';
            if (id == game.getTurn()) {
                    int x = (int) moves.charAt(1) - '0';
                    int y = (int) moves.charAt(2) - '0';
                    String result = messageHandler(game.move(id, x, y), id, x, y, game);
                    return (result);
            }
        System.out.println("not Player " + id + "Turn yet");
        return("Not your Turn yet");
    }

    public static String messageHandler(int e,int id, int x, int y, Game game)
    {
       if(e == 0)
       {
           String win = "Winner is " + game.getWinner();
          return(win);
       } else if(e == 1)
       {
           return "Illegal player number: Invalid id";
       } else if(e == 2)
       {
           return "Illegal board position: x value out of range";
       } else if(e == 3)
       {
           return "Illegal board position: y value out of range";
       } else if(e == 4)
       {
           System.out.println("Position " + x + " " + y + " is already taken, try again");
           return ("unsuccessful");
       }
       System.out.println("Player " + id + " has Chosen " + x + " " + y);  //Display to the serve the players move
        return "success";  //Display to the serve the players move
    }

}


