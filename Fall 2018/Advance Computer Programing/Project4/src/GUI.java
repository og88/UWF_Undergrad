import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private static ArrayList<Label> horses;
    private static int numHorses = 4;
    private static int height = 480;
    private static int width = 640;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Horse Race");
        horses = new ArrayList();
        addHorses();
        Pane root = setHorses();
        BorderPane pane = new BorderPane();
        pane.setCenter(root);
        Scene scene = new Scene(pane,width,height);
        scene.getStylesheets().add("styleSheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        startThreads();
    }

    public void startThreads()
    {
        for(int i = 0; i < numHorses;i++) {
            Horse h = new Horse();
            h.setHorse(horses.get(i));
            h.setMeters(width);
            Thread t = new Thread(h, ("" + i));
            t.start();
        }
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
        for(int i = 0; i < 4; i ++) {
            Label horse = new Label();
            horse.setPrefSize(110,50);
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

            horses.get(i).setLayoutY(((height/numHorses) * i) + 10);
        }
    }


}
