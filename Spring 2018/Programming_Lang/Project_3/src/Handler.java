import java.io.*;

public class Handler {
    FSA FSA1 = new FSA();

    /*Method use to report errors to users*/
    public String errorHandler(int error) {
        if (error == 0) {
            return"Program finished";
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
        }   else if (error == -6) {
            return "Invalid Transition";
        }   else if (error == -7) {
            return "Program ended in non-accepting State";
        }
        return "Nothing";
    }

    //Returns the status of the FSA
    public boolean getStatus()
    {
        return FSA1.getStatus();
    }

    //Reads the FSA file
    public int readFile(String fileName)
    {

        // This will reference one line at a time
        String line = null;
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

    //Reads the FSA file
    public int readCommand(String fileName)
    {

        // This will reference one line at a time
        String line = null;
        try {
            FileReader fileReader = new FileReader("Input/" + fileName);

            // reads input file.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            if((line = bufferedReader.readLine()) != null) {
                // close file.
                System.out.println(line);
                bufferedReader.close();
                return run(line);
            }
        }
        catch(FileNotFoundException ex) {
            return run(fileName);

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

    //Runs the FSA machine and returns results
    public int run(String input)
    {
        if (FSA1.validate(input) == 0) {
            int code = FSA1.run(input);
            if (code == 1) {
                return 1;
            } else {
                return code;
            }
        } else {
            return -5;
        }
    }
}
