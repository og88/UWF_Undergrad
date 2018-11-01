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

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Spell Checker");

        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);

        TextArea txt = new TextArea("Hello");
        txt.setWrapText(true);

        MenuBar menuBar = new MenuBar();
        Menu m1 = new Menu("File");
        MenuItem open = new MenuItem("Open");

        open.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if (file != null) {
                            try {
                                openFile(file, txt);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });

        MenuItem save = new MenuItem("Save");
        MenuItem Exit = new MenuItem("Exit");
        m1.getItems().addAll(open, save, Exit);


        Menu m2 = new Menu("Edit");
        MenuItem spellCheck = new MenuItem("SpellCheck");
        spellCheck.setOnAction(e -> {
        SpellChecker.stringBuilder(txt.getText());
        System.out.println(txt.getText());
        });
        m2.getItems().add(spellCheck);

        menuBar.getMenus().addAll(m1, m2);
        VBox vBox = new VBox(menuBar);

        BorderPane bp = new BorderPane();
        bp.setTop(vBox);
        bp.setCenter(txt);

        primaryStage.setScene(new Scene(bp, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.dir"))
        );
    }

    public static void openFile(File file, TextArea txt) throws IOException {
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String text = "";
        int i;
        while ((i = fr.read()) != -1)
            text += (char) i;
           // System.out.print((char) i);
        txt.setText(text);
    }
}

