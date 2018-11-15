import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class Main extends Application {

    /**
     * Method that displays most of the GUI items.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Spell Checker");

        FileChooser fileChooser = new FileChooser();  //File chooser used to select test txt
        configureFileChooser(fileChooser);   //Configure the file chooser object to open at the current directory

        TextArea txt = new TextArea("Please input text, or open file!");   //Add a text area to the window to get user input
        txt.setWrapText(true); //Automatically adds new line if the text is too long

        MenuBar menuBar = new MenuBar();  //Menu bar for selecting various actions
        Menu m1 = new Menu("File");   //First menu is named Menu

        MenuItem open = new MenuItem("Open");  //Open is used to select a file to test with the spell checker

        open.setOnAction(                            //action event handler for selecting a txt file
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(primaryStage);   //Opens a window in the stage to select file. The file selector has been configure to open in the source code directory
                        if (file != null) {   //If a file was selected, open it
                            try {
                                openFile(file, txt);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });

        MenuItem Exit = new MenuItem("Exit"); //Menu item to exit the program

        Exit.setOnAction(e -> {
            primaryStage.close();  //Close the primary windo to exit the program
        });

        m1.getItems().addAll(open, Exit);  //Add the open and exit options to our file menu


        Menu m2 = new Menu("Edit");  //Second menu used in this program
        MenuItem spellCheck = new MenuItem("SpellCheck");  //Option will be to check spelling

        spellCheck.setOnAction(e -> {
            SpellChecker.stringBuilder(txt.getText());  //When option is selected, we will get the text in the text area and pass it to the spell checker
        });

        m2.getItems().add(spellCheck);  //adds the spell checker to the edit menu

        menuBar.getMenus().addAll(m1, m2);  //adds both menus to our menu bar
        VBox vBox = new VBox(menuBar);  //Adds the menu to our vBox node

        BorderPane bp = new BorderPane();  //Create a border pane layout for our window
        bp.setTop(vBox);   //The top of our window will be the menu options
        bp.setCenter(txt);  //The center and majority will be the text area

        primaryStage.setScene(new Scene(bp, 300, 275));  //Sets scen to a specific size
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * File Chooser configurator. This method is used to configure the file chooser in different ways.
     * For this project the file chooser is configured to open in the project directory
     * @param fileChooser The file chooser we want to configure
     */
    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Text Files");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.dir"))  //open the file selector in the project directory
        );
    }

    /**
     * Simple method to open a file. This method will also copy the contents into a string that will be used by the spell checker.
     * @param file file to open
     * @param txt text area for user input. When the user opens a file, the text area will be populated by the contents of the file.
     * @throws IOException  must handle io exeptions for files
     */
    public static void openFile(File file, TextArea txt) throws IOException {
        FileReader fr = null;
        try {
            fr = new FileReader(file);   //try to read the file provided by the user
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String text = "";  //text will hold the contants of the file
        int i;
        while ((i = fr.read()) != -1)   //Go through the file and append all the contants
            text += (char) i;
        txt.setText(text);
    }
}

