import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * OMAR GARCIA
 * Project 3
 * Advanced Computer Programing
 */
public class GUI extends Application {
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }
    private static ArrayList<Rectangle> horses;
    private static int numHorses = 5;  //Number of horses that will race
    private static int height = 480;  //height of the window
    private static int width = 640;  //width of the window

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Horse Race");
        horses = new ArrayList();   //Initialize the list of horses
        addHorses();  //Add horses to an ArrayList of horses.
        Pane root = setHorses();  //Get a Pane object with the horses in it

        Rectangle finish = new Rectangle();  //Create a visual representation of the finish line
        finish.setWidth(15);  //Set width to a reasonable size.
        finish.setHeight(height);  //The finish line will span the window
        finish.setLayoutX(width - finish.getWidth());  //Create a finish line for the horses. Subtract its width from the windows width, so it is visible.
        root.getChildren().add(finish);  //Add finish line to our track

        HBox Buttons = addButtons();
        BorderPane pane = new BorderPane();
        pane.setCenter(root);
        pane.setBottom(Buttons);
        Scene scene = new Scene(pane,width,height);
        scene.getStylesheets().add("styleSheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public HBox addButtons()
    {
        Button run = new Button("RUN");  //Button used to start the race

        run.setOnAction(value -> {
           horseRace.startThreads(numHorses,horses,width);
        });

        Button reset = new Button("Reset");  //Button used to put horses back into starting line
        reset.setOnAction(value ->{
            reset();
        });

        Button exit = new Button("EXIT"); //Exits app

        exit.setOnAction(value -> {
            primaryStage.close();
        });
        HBox btns = new HBox();
        btns.getChildren().addAll(run,reset,exit);
        return btns;
    }


    public Pane setHorses()
    {
        Pane root = new Pane();
        for(int i = 0; i < horses.size(); i++)
        {
            root.getChildren().add(horses.get(i));
        }
        return root;
    }

    public void addHorses()
    {
        for(int i = 0; i < numHorses; i ++) {
            Rectangle horse = new Rectangle();
            horse.setWidth(100);
            horse.setHeight(50);
            horse.setLayoutY(((height/numHorses) * i) + 10);
            horse.setId("horse"+i);
            horses.add(horse);
        }

    }

    public static void update(int id, int position)
    {
        horses.get(id).setLayoutX(position);
    }

    public void reset()
    {
        for(int i = 0; i < numHorses; i ++) {

            horses.get(i).setLayoutX(0);
        }
    }

    public static void showWinner(String id)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner!");
        alert.setHeaderText("Race finished");
        alert.setContentText("Horse " + id + "is the winner!");

        alert.showAndWait();
    }

}
