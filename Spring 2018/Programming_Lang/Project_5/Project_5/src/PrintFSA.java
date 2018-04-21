import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.scene.shape.Line;
import java.lang.Math;
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
            Label lbl = new Label();
            if(handler.FSA1.getStart() == i)
            {
                lbl.setId("start");
            }
            else {
                boolean isAccepting = false;
                for (int j = 0; j < handler.FSA1.getAcceptingSize(); j++) {
                    if (handler.FSA1.getAcepting(j) == i) {
                        isAccepting = true;
                    }
                }
                if (isAccepting) {
                    lbl.setId("accept");
                } else {
                    lbl.setId("lbl");
                }
            }
            lbl.setText("" + i);
            lbl.setLayoutY(i*100);
            if((i%2) == 1) {
                lbl.setLayoutX(bounds.getWidth()*.25);
            }
            else{
                lbl.setLayoutX(bounds.getWidth()*.75);
            }

            labels.add(lbl);
            root.getChildren().add(lbl);
        }
        for(int i = 0; i < labels.size(); i++) {
            for (int x = 0; x < handler.FSA1.getNumState(); x++) {
                for (int y = 0; y < handler.FSA1.getNumOfAlphabet(); y++) {
                    if (handler.FSA1.getTransition(x, y) == i) {
                        Line line = new Line();
                        int offset = 25;
                        double startx = labels.get(x).getLayoutX()+ offset;
                        double starty = labels.get(x).getLayoutY()+ offset;
                        double endx = labels.get(i).getLayoutX()+ offset;
                        double endy = labels.get(i).getLayoutY()+ offset;

                        line.setStartX(startx);
                        line.setStartY(starty);
                        line.setEndX(endx);
                        line.setEndY(endy);
                        TextField text = new TextField();
                        text.setText("" + handler.FSA1.getAlphabet(y));
                        if(x == i)
                        {
                            text.setLayoutX(startx);
                            text.setLayoutY(starty);
                        }
                        else {
                            if(startx < endx) {
                                text.setLayoutX(startx + (Math.abs(endx - startx) / 2));
                                text.setLayoutY(starty + (Math.abs(endy - starty) / 2));
                            } else
                            {
                                text.setLayoutX(endx + (Math.abs(endx - startx) / 2));
                                text.setLayoutY(endy + (Math.abs(endy - starty) / 2));
                            }
                        }
                        root.getChildren().add(line);
                        root.getChildren().add(text);
                    }
                }
            }
        }
        stage.show();
    }
}
