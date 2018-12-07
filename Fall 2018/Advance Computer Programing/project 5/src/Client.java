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
    private static String name = null; //User's name that will be playing
    public static boolean gameOver = false; //A booleagn to determine whether a game is over or not
    private static PrintWriter out; //A prnt writer used to write to the server
    private static Scanner in; //A scanner used for reading data from the server
    static final int SBAP_PORT = 8889;  //port number used to connect to the database

    public static void main(String[] args) throws IOException
    {
        doSomething(); //Launch the program
    }

    /**
     * Method used by the user to start the program
     * The user can choose to join a game, make a move, or quit
     * @throws IOException
     */
    public static void doSomething() throws IOException
    {
        Scanner sc = new Scanner(System.in);  //Scanner used for userr input


        while(!gameOver) {  //While the game is still going, loop
            String command = sc.next();
            if(command.equals("quit"))  //User wants to quit the game
            {
                gameOver = true;
            }else if(command.equals("join"))  //User wants to join a game
            {
                name = sc.next();  //Get the name from the user through command line
                setup();       //Start network setup

            } else if(command.equals("choose")) {  //Player wants to make a move
                if(name != null) {  //I player has joined a game, get there moves
                    executeCommand(sc.next());  //send move positions
                }
                else{
                    System.out.println("You need to join a game");//If player has not joined a game, warn them
                }
            }
        }
        System.out.println("Game over");
    }


    public static void executeCommand(String command)
    {
        out.println(command);
        out.flush();

        String response = in.nextLine();
        messageHandler(response);

    }

    public static void messageHandler(String response)
    {
        if (response.equals("Need a second player to start")) {
            System.out.println("Need a second player to start");
        } else if(response.equals("Not your Turn yet")){
            System.out.println("Please wait your turn");
        }else if(response.equals("success")){
            System.out.println("Move made Success fully");
        }else if(response.equals("unsuccessful")){
            System.out.println("Move unSuccessful");
        }else if(response.equals("1") ||response.equals("2")  ){
            System.out.println("player " + response + "wins");
            gameOver = true;
        }

        response = in.nextLine();
        System.out.println(response.charAt(0) + response.charAt(0) + response.charAt(0) + "\n" +
                response.charAt(0)+ response.charAt(0) +response.charAt(0) + "\n" +
                response.charAt(0) + response.charAt(0) + response.charAt(0));

    }

    public static void setup() throws IOException
    {
        Socket s = new Socket("localhost",SBAP_PORT);
        InputStream instream = s.getInputStream();
        OutputStream outstream = s.getOutputStream();
        in = new Scanner(instream);
        out = new PrintWriter(outstream);

        if(in.hasNextLine())
        {
            System.out.println("Hello " + name + " you are player " +in.nextLine());
        }
    }
}
