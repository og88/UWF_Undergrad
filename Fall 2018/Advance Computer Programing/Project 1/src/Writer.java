import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private String fileName;

    public Writer()
    {
        this.fileName = "Text.txt";
    }

    public Writer(String fileName)
    {
        this.fileName = fileName;
    }

    public void add(String sentence)
    {
        try {
            FileWriter f1 = new FileWriter(fileName, true);
            f1.write(sentence);
            f1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startNew(String sentence)
    {
        try {
            FileWriter f1 = new FileWriter(fileName, false);
            f1.write(sentence);
            f1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
