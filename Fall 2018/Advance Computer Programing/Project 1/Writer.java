import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private String fileName;

    /**
     * Default constructor of the writer class
     */
    public Writer()
    {
        this.fileName = "Text.txt";
    }
    
    /**
     * Assign the file name for the file that will ne manipulated by the writer class
     * @param fileName Name of file
     */
    public Writer(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * Appends a string to the current file
     * @param sentence string that will be added to the file
     */
    public void add(String sentence)
    {
        try {
            FileWriter f1 = new FileWriter(fileName, true);  //The true value indicates we want to append strings to file, not write over the file
            f1.write(sentence);  //Writes string sentence to file
            f1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erases the content of the file. If the file does not exist, it will be created.
     * @param sentence
     */
    public void startNew(String sentence)
    {
        try {
            FileWriter f1 = new FileWriter(fileName, false);  //The false value indicates we want to erase the file and add to the beginning
            f1.write(sentence);   //Writes string sentence to file
            f1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * getter for retrieving the file name
     * @return File name as a string
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Manually set the name of the file. This can be used if the writer was initialized with a default constructor
     * @param fileName The name of the new file
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * Method used to read the entire file
     * @throws IOException The file must exist for the method to run
     */
    public void read() throws IOException
    {
    	BufferedReader br = new BufferedReader(new FileReader(this.fileName));
    	try {
    	    StringBuilder sb = new StringBuilder();
    	    String line = br.readLine();

    	    while (line != null) {    //Read the file line by line and appending it to the string builder
    	        sb.append(line);
    	        sb.append(System.lineSeparator());
    	        line = br.readLine();
    	    }
    	    String everything = sb.toString();  //Covert the extracts lines into a string object
    	    System.out.println(everything);    //Prints out the contents of the file
    	} finally {
    	    br.close();  //closes the string builder
    	}
    }
}
