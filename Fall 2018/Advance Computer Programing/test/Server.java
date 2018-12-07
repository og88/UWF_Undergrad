import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/**
 * OMAR GARCIA
 * Project 5
 * Advanced Computer Programing
 */
public class Server {

    public static void main(String[] args) throws IOException {

        final int SBAP_PORT = 8888;
        ServerSocket server = new ServerSocket(SBAP_PORT);
        System.out.println("Waiting for players to connect...");


        while(true)
        {
            Socket s = server.accept();
            System.out.println("Player1 connected.");
            Service service = new Service(s);
            Thread t = new Thread(service);
            t.start();
        }
    }

}
