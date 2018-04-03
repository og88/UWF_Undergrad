import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    static int numOfStates = 5;
    private static char[] alphabet = new char[4];
    private static int[][] transition = new int[5][4];
    private static int[] accepting = new int[2];




    public static void main(String[] args) {
        buildTransitionTable();
        addAlphabet();
        addTransition();
        addAccepting();
        writeFile(addState());
        System.out.println(addState());

    }

    public static void writeFile(String lsp)
    {


        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter("FSA.lsp");
            bw = new BufferedWriter(fw);
            bw.write(lsp);

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

    public static void addAccepting()
    {
        accepting[0] = 1;
        accepting[1] = 3;
    }

    public static void addTransition()
    {
        transition[0][0] = 0;
        transition[0][1] = 1;
        transition[1][0] = 2;
        transition[2][0] = 2;
        transition[2][1] = 3;
        transition[3][0] = 3;
        transition[3][2] = 4;
        transition[4][0] = 4;
        transition[4][3] = 1;

    }

    public static void addAlphabet()
    {
        alphabet[0] = 'x';
        alphabet[1] = 'y';
        alphabet[2] = 'z';
        alphabet[3] = 'a';
    }

    /*Defaults all transitions to -1 for testing*/
    public static void buildTransitionTable() {
        transition = new int[numOfStates][alphabet.length];
        for (int i = 0; i < numOfStates; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                transition[i][j] = -1;
            }
        }
    }

    public static String addState(){
        String Beginning = "(defun FSA ()\n"+
                "\t(setq fp (open \"commands.txt\" :direction :input)) ; read the file\n"+
                "\t(state0 (read fp \"done\"))\n"+
                "\t)\n";
        String function = "";
        for(int i = 0; i < transition.length; i++)
        {
            boolean accept = false;
            for(int a = 0; a < accepting.length; a++)
            {
                if(i == accepting[a])
                {
                    accept = true;
                }
            }
            if(accept) {
                function = "(defun state" + i + "(L)\n" +
                        "(if (NULL L) (princ \"accepting State\")) \n" +
                        "(setq x (CAR L))\n";
            }
            else
            {
                function = "(defun state" + i + "(L)\n" +
                        "(if (NULL L) (princ \"Non-accepting State\")) \n" +
                        "(setq x (CAR L))\n";
            }

            for(int j = 0; j < alphabet.length; j++)
            {

                if(transition[i][j] != -1)
                {
                    function = function + "(if (EQUAL '" + alphabet[j] + " X) (state" + transition[i][j] + " (CDR L)))\n";
                }
            }
            function = function + ")\n\n";
            Beginning = Beginning+ function;
        }
        return function;
}
}
