import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class writeLSP {
    static void Create(Handler handler)
    {
        writeFile(addStates(handler));
    }

    /*Function to add states into lsp file*/
    public static String addStates(Handler handler){
        String Beginning = "(defun demo ()\n" +
                "\t(setq fp (open \"commands.txt\" :direction :input)) ; read the file\n" +
                "\t(setq s (read fp))\n" +
                "\t(state0 s)\n" +
                "\t(setq s (read fp))\n" +
                "\t(state0 s)\n" +
                "\t(setq s (read fp))\n" +
                "\t(state0 s)\n" +
                "\t(setq s (read fp))\n" +
                "\t(state0 s)\n" +
                "\t(setq s (read fp))\n" +
                "\t(state0 s)\n" +
                "\t)\n\n";
        String function = "";
        for(int i = 0; i < handler.FSA1.getNumState(); i++)
        {
            boolean accept = false;
            for(int a = 0; a < handler.FSA1.getAcceptingSize(); a++)
            {
                if(i == handler.FSA1.getAcepting(a))
                {
                    accept = true;
                }
            }
            if(accept) {
                function = "(defun state"+ i + "(L) \n" +
                        "(setq x (CAR L))\n" +
                        "(princ x)\n" +
                        "(cond \n" +
                        "((EQUAL nil L)(princ \"\\naccepting State\\n\" ))\n";
            }
            else
            {
                function = "(defun state"+ i + "(L) \n" +
                        "(setq x (CAR L))\n" +
                        "(princ x)\n" +
                        "(cond \n" +
                        "((EQUAL nil L)(princ \"\\nNon-accepting State\\n\" ))\n";
            }

            for(int j = 0; j < handler.FSA1.getNumOfAlphabet(); j++)
            {

                if(handler.FSA1.getTransition(i,j)  != -1)
                {
                    function = function + "((EQUAL '" + handler.FSA1.getAlphabet(j) + " X) (state" + handler.FSA1.getTransition(i,j) + " (CDR L)))\n";
                }
            }
            function = function + "(t (princ \"\\nInvalid\\n\"))\n" +
                    "))\n\n";
            Beginning = Beginning+ function;
        }
        return Beginning;
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
}
