import java.io.*;
import java.lang.String;

public class Main
{
    public static void main(String[] args)
    {
    int report = build();
    errorHandler(report);
    }

    static void errorHandler(int error)
    {
       if(error == 0)
       {
           System.out.println("Program ran fine");
       }
       else if(error == -2)
       {
           System.out.println("Error: Please seperate elements in transition using :");
       }
       else if(error == -3)
       {
           System.out.println("Error: Please end transitions with )");
       }
    }
    static int build()
    {
        int i = 0;

        FSA FSA1 = new FSA();
       /* i = FSA1.setStateNumber(i);
        i = FSA1.setAlphabet(i);
        i = FSA1.setTransitions(i);
        i = FSA1.setStart(i);
        FSA1.setAccepting(i);*/
        FSA1.setAccepting(FSA1.setStart(FSA1.setTransitions(FSA1.setAlphabet(FSA1.setStateNumber(i)))));
        return 0;
    }

}
