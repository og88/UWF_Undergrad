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
        String Beginning = "(defun FSA (S)\n"+
                "\t(setq fp (open S :direction :input)) ; read the file\n"+
                "\t(state0 (read fp \"done\"))\n"+
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

            for(int j = 0; j < handler.FSA1.getNumOfAlphabet(); j++)
            {

                if(handler.FSA1.getTransition(i,j)  != -1)
                {
                    function = function + "(if (EQUAL '" + handler.FSA1.getAlphabet(j) + " X) (state" + handler.FSA1.getTransition(i,j) + " (CDR L)))\n";
                }
            }
            function = function + ")\n\n";
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
