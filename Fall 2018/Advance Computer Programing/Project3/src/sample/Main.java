package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Spell Checker");

        MenuBar menuBar = new MenuBar();
        Menu m1 = new Menu("File");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem Exit = new MenuItem("Exit");
        m1.getItems().addAll(open,save,Exit);


        Menu m2 = new Menu("Edit");
        MenuItem spellCheck = new MenuItem("SpellCheck");
        m2.getItems().add(spellCheck);

        menuBar.getMenus().addAll(m1,m2);
        VBox vBox = new VBox(menuBar);

        primaryStage.setScene(new Scene(vBox, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
