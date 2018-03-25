import java.io.*;

public class Handler {
    FSA FSA1 = new FSA();

    /*Method use to report errors to users*/
    public String errorHandler(int error) {
        if (error == 0) {
            return"Program ran fine";
        } else if (error == 1) {
            return "Program ended in accepting State";
        }else if (error == -2) {
            return"Error: Warning input element not in Alphabet";
        } else if (error == -2) {
            return"Error: Please separate elements in transition using :";
        } else if (error == -3) {
            return "Error: Please end transitions with )";
        } else if (error == -4) {
            return "Error: Program experienced error building FSA logic";
        } else if (error == -5) {
            return "Error: Input Value not in alphabet";
        }   else if (error == -7) {
            return "Program ended in non-accepting State";
        }
        return "Nothing";
    }

    public boolean getStatus()
    {
        return FSA1.getStatus();
    }

    public int readFile(String fileName)
    {

        // This will reference one line at a time
        String line = null;
        System.out.println(new File(".").getAbsoluteFile());
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader("FSA/" + fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            if((line = bufferedReader.readLine()) != null) {
                FSA1.setFSA(line);
                FSA1.setStatus(true);
                return 0;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            return 1;

        }
        catch(IOException ex) {
            return 2;
        }
        return 2;
    }

    /*Method use to create FSA object and to validate user input*/
    public int build(String Test) {
        int i = 0;

        if(FSA1.setAccepting(FSA1.setStart(FSA1.setTransitions(FSA1.setAlphabet(FSA1.setStateNumber(i))))) > 0)
        {
            FSA1.printTable();
        }
        else
            return -4;
        return 0;
    }

    public int run(String input)
    {
        if (FSA1.validate(input) == 0) {
            if (FSA1.run(input) == 1) {
                System.out.println("Input finished in accepting state!");
                return 1;
            } else {
                System.out.println("Input finished in accepting non-state!");
                return -7;
            }
        } else {
            return -5;
        }
    }
}
