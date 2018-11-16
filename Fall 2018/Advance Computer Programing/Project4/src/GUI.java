import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
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

    private static ArrayList<Label> horses; //ArrayList that holds the objects that will represent the horses
    private static int numHorses = 5;  //Number of horses that will race
    private static int height = 480;  //height of the window
    private static int width = 640;  //width of the window

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Horse Race");
        horses = new ArrayList();   //Initialize the list of horses
        addHorses();  //Add horses to an ArrayList of horses.
        Pane root = setHorses();  //Get a Pane object with the horses and the finish line in it
        HBox Buttons = addButtons();  //Get an HBox filled with buttons. The buttons are used for user functionality
        BorderPane pane = new BorderPane();  //Create a border pane to keep animations and buttons separate
        pane.setCenter(root); //Center of the pane will be for the horse race
        pane.setBottom(Buttons);  //The bottom will host the user buttons
        Scene scene = new Scene(pane,width,height);  //Create a new scene with the functionality
        scene.getStylesheets().add("styleSheet.css");  //Set styleSheet to the css file
        primaryStage.setScene(scene);  
        primaryStage.show();
    }

    /**
     * Methos use to create an HBox for the program
     * The HBox will hold three button. Run, Reset, and Exit
     * @return Returns HBox containing the buttons
     */
    public HBox addButtons()
    {
        Button run = new Button("RUN");  //Button used to start the race

        run.setOnAction(value -> {
            if(!horseRace.isFinished() && !horseRace.getStarted()) {
                horseRace.setStarted(true);
                horseRace.startThreads(numHorses, horses, width);  //Calls method to start the horse threads
            }
        });

        Button reset = new Button("Reset");  //Button used to put horses back into starting line
        reset.setOnAction(value ->{
            if(horseRace.isFinished() && !horseRace.getStarted()) {
                reset();  //Calls reset method, to reset the race
                horseRace.setFin(false);
            }
        });

        Button exit = new Button("EXIT"); //Exits app
        exit.setOnAction(value -> {
            primaryStage.close();  //Closes the window that runs the game
        });

        HBox btns = new HBox();  //HBox to hold the buttons
        btns.getChildren().addAll(run,reset,exit);  //add all the Buttons to the HBox
        btns.setAlignment(Pos.CENTER);
        btns.setSpacing(30);
        return btns;  //Return HBox to be added to the scene
    }

    /**
     * Method used to create the pane that will host the horse race.
     * The pane will cantain the horses, the finish line and a green track
     * @return  Returns the pane that will be the center of the border Pane
     */
    public Pane setHorses()
    {
        Pane root = new Pane(); //Create new Pane to hold horses
        root.setStyle("-fx-background-color: green"); //Set background color to green

        Rectangle finish = new Rectangle();  //Create a visual representation of the finish line
        finish.setWidth(15);  //Set width to a reasonable size.
        finish.setHeight(height);  //The finish line will span the window
        finish.setLayoutX(width - finish.getWidth());  //Create a finish line for the horses. Subtract its width from the windows width, so it is visible.
        finish.setStroke(Color.BLACK);
        finish.setFill(Color.RED);
        root.getChildren().add(finish);  //Add finish line to our track


        for(int i = 0; i < horses.size(); i++)
        {
            root.getChildren().add(horses.get(i));  //Add horse created earlier to the pane
        }

        for(int i = 0; i < numHorses; i ++) {  //loop to get all horses, default is 5
           Line track = new Line();
           track.setStartX(0);
           track.setEndX(width);
           Label name = new Label("horse " + i);
           name.setLayoutY(((height/numHorses) * i));
           track.setLayoutY(((height/numHorses) * i));  //divide the race into equal lanes, each horse is positioned in its lane
           root.getChildren().addAll(track,name);
        }

        return root;  //return pane for use
    }

    /**
     * Method used to create and store the horse visual objects
     * The horse will be labels that are styled with a GIF
     */
    public void addHorses()
    {
        for(int i = 0; i < numHorses; i ++) {  //loop to get all horses, default is 5
            Label horse = new Label();  //Create a new label for each horse
            horse.setLayoutY(((height/numHorses) * i));  //divide the race into equal lanes, each horse is positioned in its lane
            horse.setId("horse"+i); //Set id for each horse, this will be used by the style sheet
            horse.setPrefSize(100,75); //set horse size. For this project 100x75 is good, but can be changed to fit any window size/preferences
            horses.add(horse); //add horse to list
        }

    }

    /**
     * Method used to update the location of a horse
     * @param id identify the horse who has moved
     * @param position the horses new position
     */
    public static void update(int id, int position)
    {
        horses.get(id).setLayoutX(position);  //GUI updates the position of the horse based on where the horse tells it it is
    }

    /**
     * Method used to reset the horse race.
     */
    public void reset()
    {
        for(int i = 0; i < numHorses; i ++) { //loop used to modify every horses position

            horses.get(i).setLayoutX(0);  //Set horses position to the starting line
        }
    }

    /**
     * Method use to show the winning horse
     * @param id
     */
    public static void showWinner(String id, Long time)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);  //Dialog box is of information type
        alert.setTitle("Winner!");     
        alert.setHeaderText("Race finished");
        alert.setContentText("Horse " + id + " won with a time of " + time + " seconds!");  //tells the user which horse has won and how long it took
        
        alert.showAndWait();

    }

}
