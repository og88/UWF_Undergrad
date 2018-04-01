package com.example.omarg.scavenge_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Handler {
    static String inputLine;
    static int i = 0;
    static int buildingNum = 0;


    public static ArrayList<Building> main(String URL) {

        URL url;

        try {
            // get URL content
            url = new URL("http://node-express-env.hvwzjgwfbd.us-east-1.elasticbeanstalk.com/" + URL);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));


            while ((inputLine = br.readLine()) != null) {
                System.out.println(inputLine);
                ArrayList<Building> buildingList = start();
                printList(buildingList);
                return buildingList;
            }

            br.close();
            return null;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static ArrayList<Building> start() {
        ArrayList<Building> buildingList = new ArrayList<>();
        while (i < inputLine.length()) {
            Building toAdd = id();
            if (toAdd != null) {
                buildingList.add(toAdd);
                buildingNum++;
            }
        }
        return buildingList;
    }

    static void printList(ArrayList<Building> List) {
        for (int i = 0; i < buildingNum; i++) {
            System.out.println(List.get(i).getBuilding_id());
        }
    }

    static String Parse() {
        for (i = i; i < inputLine.length(); i++) {
            char c = inputLine.charAt(i);
            if (c == '\"') {
                i++;
                c = inputLine.charAt(i);
                String item = "";
                for (int j = i + 1; j < inputLine.length() && c != '\"'; j++) {
                    item = item + c;
                    c = inputLine.charAt(j);
                    i = j;
                }
                i++;
                return item;
            } else {
                //System.out.println("Missing: \"");
            }
        }
        return null;
    }

    static Building id() {
        Building building = new Building();
        while (i < inputLine.length()) {
            String s = Parse();
            if(s == null)
            {
                return null;
            }
            //System.out.print(s);
            if (s.equals("building_id")) {
                //System.out.print(" : Found");
                s = Parse();
                if(s == null)
                {
                    return null;
                }
                if (s.equals("S")) {
                    // System.out.print(s);
                    // System.out.print(" : Found");
                    s = Parse();
                    if(s == null)
                    {
                        return null;
                    }
                    building.setBuilding_id(s);
                }
            } else if (s.equals("info")) {
                //System.out.print(" : Found");
            } else if (s.equals("Location_type")) {
                //System.out.print(" : Found");
                s = Parse();
                if(s == null)
                {
                    return null;
                }
                if (s.equals("S")) {
                    //System.out.print(s);
                    // System.out.print(" : Found");
                    s = Parse();
                    if(s == null)
                    {
                        return null;
                    }
                    building.setLocation_type(s);
                }
            } else if (s.equals("room#")) {
                //System.out.print(" : Found");
                s = Parse();
                if(s == null)
                {
                    return null;
                }
                if (s.equals("S")) {
                    // System.out.print(" : Found");
                    s = Parse();
                    if(s == null)
                    {
                        return null;
                    }
                    building.setRoom(s);
                }
            } else if (s.equals("Description")) {
                //System.out.print(" : Found");
                s = Parse();
                if(s == null)
                {
                    return null;
                }
                if (s.equals("S")) {
                    //System.out.print(" : Found");
                    s = Parse();
                    if(s == null)
                    {
                        return null;
                    }
                    building.setDescription(s);
                }
            } else if (s.equals("_id")) {
                //System.out.print(" : Found");
                s = Parse();
                if(s == null)
                {
                    return null;
                }
                if (s.equals("S")) {
                    // System.out.print(" : Found");
                    s = Parse();
                    if(s == null)
                    {
                        return null;
                    }
                    building.set_id(s);
                    return building;
                }
            } else {
                //System.out.print(" : Not Found");
            }
            //System.out.println("");
        }
        return null;
    }
}

