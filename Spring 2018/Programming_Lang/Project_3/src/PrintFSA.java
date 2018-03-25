import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import java.util.*;

public class PrintFSA {
    static void print(Handler handler)
    {
        Stage stage = new Stage();
        stage.setTitle("FSA");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        Pane root = new Pane();
        Scene scene = new Scene(root, 300, 250);
        root.setId("root");
        scene.getStylesheets().add("FSA.css");
        stage.setScene(scene);

        ArrayList<Label> labels = new ArrayList<>();
        for(int i = 0; i < handler.FSA1.getNumState(); i++)
        {
            System.out.println("y");
            Label lbl = new Label();
            lbl.setId("lbl");
            lbl.setText("" + i);
            lbl.setLayoutX(bounds.getWidth()/2);
            lbl.setLayoutY(i*100);
            labels.add(lbl);
            root.getChildren().add(lbl);
        }

        stage.show();
    }
}
