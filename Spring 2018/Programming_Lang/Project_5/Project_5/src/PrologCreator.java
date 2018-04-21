import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PrologCreator {
    public static void writeFile(Handler handler) {
        String content = "";
        content = createExplenation();
        content += createAlphabet(handler);
        content += createRules(handler);
        writeToFile(content);
        System.out.println(content);
    }

    public static String createExplenation() {
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

    public static String createAlphabet(Handler handler) {
        String Alphabet = "";

        for (int i = 0; i < handler.FSA1.getAcceptingSize(); i++) {
            Alphabet += "accept(" + handler.FSA1.getAcepting(i) + ").\n";
        }
        Alphabet += "\n";

        for (int i = 0; i < handler.FSA1.getNumState(); i++) {
            for (int j = 0; j < handler.FSA1.getNumOfAlphabet(); j++) {
                if (handler.FSA1.getTransition(i,j) != -1) {
                    Alphabet += "input(" + i + "," + handler.FSA1.getAlphabet(j) + "," + handler.FSA1.getAlphabet(j) + ").\n";
                }
            }
        }

        return Alphabet;
    }

    public static String createRules(Handler handler) {
        String rule = "\n" +
                "%%****************************************%%\n" +
                "%% RULES                                  %%\n" +
                "%%****************************************%% \n" +
                "\n" +
                "\n" +
                "good([H|T]) :- state0([H|T],H,T).\n" + "\n";

        for (int i = 0; i < handler.FSA1.getNumState(); i++) {
            for (int j = 0; j < handler.FSA1.getNumOfAlphabet(); j++) {
                if (handler.FSA1.getTransition(i,j) != -1) {
                    rule += "state" + i + "([H|T],H,T) :- input(" + i + ",H," + handler.FSA1.getAlphabet(j) + "), state" + handler.FSA1.getTransition(i,j) + "(T,_,_).\n";
                }
            }
            rule += "state" + i + "([],_,_) :- accept(" + i + ").\n\n";
        }
        return rule;
    }

    public static void writeToFile(String pl) {


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
