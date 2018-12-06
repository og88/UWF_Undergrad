import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


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
            System.out.println("Player1 connected1.");
            Service service = new Service(s,1);
            Thread t = new Thread(service);
            t.start();
            Socket s2 = server.accept();
            System.out.println("Player2 connected1.");
            Service service2 = new Service(s2,2);
            Thread t2 = new Thread(service2);
            t2.start();


        }
    }

}
