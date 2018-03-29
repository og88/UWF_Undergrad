package com.example.omarg.scavenge_client;

import java.net.*;
import java.io.*;

public class Handler {
    public static String main(){
        URL url;
        String inputLine;

        try {
            // get URL content
            url = new URL("http://node-express-env.hvwzjgwfbd.us-east-1.elasticbeanstalk.com/room/004348");
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));





            while ((inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
                return inputLine;
            }

            br.close();

            System.out.println("Done");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Done";
    }
}