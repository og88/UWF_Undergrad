import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Horse Race");
        ArrayList<Button> btns = new ArrayList();

        int width = 1280;
        int height = 720;
        int numHorses = 4;

        for(int i = 0; i < numHorses; i ++) {
            Button btn = new Button("Horse " + i);
            btn.setLayoutY((height/4) * i);

            btns.add(btn);
        }

        Group root = new Group();
        for(int i = 0; i < btns.size(); i++)
        {
            root.getChildren().add(btns.get(i));
        }
        primaryStage.setScene(new Scene(root,width,height));
        primaryStage.show();

        for(int i = 0; i < numHorses;i++) {
            Horse h = new Horse();
            h.setBtn(btns.get(i));
            h.setMeters(width);
            Thread t = new Thread(h, ("" + i));
            t.start();
        }
    }
}
