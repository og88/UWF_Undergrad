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
                if (handler.FSA1.getTransition(i, j) != -1) {
                    Alphabet += "input(" + i + "," + handler.FSA1.getAlphabet(j) + "," + handler.FSA1.getAlphabet(j) + ").\n";
                }
            }
        }

        return Alphabet;
    }

    public static String createRules(Handler handler) {

        String acceptable = "";
        String unacceptable = "";
        ArrayList<Integer> list1 = new ArrayList();
        ArrayList<Integer> list2 = new ArrayList();
        boolean isDone = false;

        String rule = "";

        for (int i = 0; i < handler.FSA1.getNumState(); i++) {
            for (int j = 0; j < handler.FSA1.getNumOfAlphabet(); j++) {
                if (handler.FSA1.getTransition(i, j) != -1) {
                    rule += "state" + i + "([H|T],H,T) :- input(" + i + ",H," + handler.FSA1.getAlphabet(j) + "), state" + handler.FSA1.getTransition(i, j) + "(T,_,_).\n";
                    for (int k = 0; k < handler.FSA1.getAcceptingSize(); k++) {
                        int state = handler.FSA1.getAcepting(k);
                        System.out.println("comparing " + state + " and " + i);
                        if (state == i) {
                            System.out.println("its tru \n\n");
                            isDone = true;
                        }
                    }
                    if (!isDone && (handler.FSA1.getTransition(i, j) > i)) {
                        list1.add(j);
                        System.out.println( handler.FSA1.getAlphabet(j) + " added");
                    }
                    else
                    {
                        list2.add(j);
                    }
                }
                else
                {
                    list2.add(j);
                }
            }
            rule += "state" + i + "([],_,_) :- accept(" + i + ").\n\n";
        }

        for (int i = 0; i < list1.size(); i++) {
            if (i == (list1.size() - 1)) {
                acceptable = acceptable + handler.FSA1.getAlphabet(list1.get(i));
            }
            else {
                acceptable = acceptable + handler.FSA1.getAlphabet(list1.get(i)) + ",";
            }
        }
        for (int i = 0; i < list2.size(); i++) {
            if (i == (list2.size() - 1)) {
                unacceptable = unacceptable + handler.FSA1.getAlphabet(list2.get(i));
            }
            else
            {
                unacceptable = unacceptable + handler.FSA1.getAlphabet(list2.get(i)) + ",";
            }
        }

        String start = "\n" +
                "%%****************************************%%\n" +
                "%% RULES                                  %%\n" +
                "%%****************************************%% \n" +
                "\n" +
                "\n" +
                "good :- start([" + acceptable + "]).\nbad :- start([" + unacceptable + "]).\n" + "start([H|T]) :- state0([H|T],H,T).\n";
        start += rule;
        return start;
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
