import java.io.*;
import java.util.Scanner;
import java.lang.String;

public class Main
{
    static int[][] list =new int[8][6];
    static char[] Alpha = new char[6];

    public static void main(String[] args)
    {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter String: ");
        String n = reader.nextLine(); // Scans the next token of the input as an int.

        int state = 0;
        build();

        for (int i = 0; i < n.length(); i++)
        {
            int nextState = check(state, n.charAt(i));
            System.out.println("q" + state + " -> q" + nextState);
            state = nextState;
        }
        //once finished
        reader.close();
    }

    public static void build()
    {
        for(int i =0; i < 8; i++)
        {
            for(int j = 0; j < 6; j++)
            {
                list[i][j] = 0;
            }
        }

        list[0][1] = 1;
        list[1][2] = 2;
        list[2][1] = 3;
        list[3][2] = 4;
        list[4][3] = 5;
        list[5][4] = 6;
        list[6][5] = 7;

        Alpha[1] = 'c';
        Alpha[2] = 'o';
        Alpha[3] = 'n';
        Alpha[4] = 'u';
        Alpha[5] = 't';
    }

    public static int check(int state, char let)
    {
        System.out.printf("%c : ", let);
        if(state == 7)
        {
            return 7;
        }
        else
        {
            for(int i = 1; i <= 5; i++)
            {
                if(Alpha[i] == let)
                {
                    return list[state][i];
                }
            }
        }
        return 0;
    }
}

