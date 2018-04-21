import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        int transactions[][] = new int[5][4];
        int accept[] = new int[2];
        ArrayList<String> alphabet = new ArrayList<>();


        for(int i = 0; i < transactions.length;i++)
        {
            for(int j = 0; j < transactions[i].length; j++)
            {
                transactions[i][j] = -1;
            }
        }

        transactions[0][0]= 0;
        transactions[0][1]= 1;
        transactions[1][0]= 2;
        transactions[2][0]= 2;
        transactions[2][1]= 3;
        transactions[3][0]= 3;
        transactions[3][2]= 4;
        transactions[4][0]= 4;
        transactions[4][3]= 1;

        accept[0] = 1;
        accept[1] = 3;

        alphabet.add("x");
        alphabet.add("y");
        alphabet.add("z");
        alphabet.add("a");

        writeFile(alphabet, transactions, accept);

    }

    public static void writeFile(ArrayList alphabet, int transactions[][], int accept[])
    {
        String content = "";
        content = createExplenation();
        content += createAlphabet(alphabet, transactions, accept);
        content += createRules(alphabet, transactions);
        writeToFile(content);
        System.out.println(content);
    }

    public static String createExplenation()
    {
        String Explanation =
                "%%******************************************%%\n" +
                "%% This file holds the rules and funtions   %%\n" +
                "%% for an fsa in prolog                     %%\n" +
                "%%******************************************%%\n" +
                "\n" +
                "%%******************************************%%\n" +
                "%% FACTS                                    %%\n" +
                "%%******************************************%%                        \n";
        return Explanation;
    }

    public static String createAlphabet(ArrayList alphabet, int transactions[][], int accept[])
    {
        String Alphabet = "";

        for(int i = 0; i < accept.length; i++)
        {
            Alphabet += "accept(" + accept[i] + ").\n";
        }
        Alphabet += "\n";
        for(int i = 0; i < transactions.length;i++)
        {
            for(int j = 0; j < transactions[i].length; j++)
            {
                if(transactions[i][j] != -1)
                {
                    Alphabet += "input(" + i + "," + alphabet.get(j) + "," + alphabet.get(j) + ").\n";
                }
            }
        }

        return Alphabet;
    }

    public static String createRules(ArrayList alphabet, int transactions[][])
    {
        String rule = "\n" +
                "%%****************************************%%\n" +
                "%% RULES                                  %%\n" +
                "%%****************************************%% \n" +
                "\n" +
                "\n" +
                "good([H|T]) :- state0([H|T],H,T).\n" + "\n";

        for(int i = 0; i < transactions.length; i++)
        {
            for(int j = 0; j < transactions[i].length; j++)
            {
                if(transactions[i][j] != -1) {
                    rule += "state" + i + "([H|T],H,T) :- input(" + i + ",H," + alphabet.get(j) + "), state" + transactions[i][j] + "(T,_,_).\n";
                }
            }
            rule+="state" + i + "([],_,_) :- accept(" + i + ").\n\n";
        }
        return rule;
    }

    public static void writeToFile(String pl)
    {


        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter("fsa.pl");
            bw = new BufferedWriter(fw);
            bw.write(pl);

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

}
