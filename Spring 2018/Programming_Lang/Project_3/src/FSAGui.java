import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.scene.layout.HBox;



public class FSAGui extends Application {
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        Handler handler = new Handler();
        MainMenu(primaryStage, handler);
    }

    static void MainMenu(Stage primaryStage, Handler handler)
    {
        primaryStage.setTitle("Main");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        Button btn = new Button();
        Button btn2 = new Button();

        btn.setLayoutX(bounds.getWidth()/2);
        btn.setLayoutY(bounds.getHeight() * .25);
        btn.setText("Load a FSA");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                read(primaryStage, handler);
            }
        });

        btn2.setLayoutX(bounds.getWidth()/2);
        btn2.setLayoutY(bounds.getHeight() * .75);
        btn2.setText("Process a String");
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                run(primaryStage, handler);
            }
        });

        Pane root = new Pane();
        root.getChildren().add(btn);
        root.getChildren().add(btn2);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    static int run(Stage primaryStage, Handler handler)
    {
        primaryStage.setTitle("Second Menu");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        Button btn = new Button();
        Button btn2 = new Button();
        Label label1 = new Label("Input:");
        HBox hb = new HBox();
        Label label2 = new Label();


        btn.setLayoutX(bounds.getWidth()/2);
        btn.setLayoutY(bounds.getHeight()/2);
        btn.setText("Run FSA");

        btn2.setText("Back");

        hb.setLayoutX(bounds.getWidth()/2);
        hb.setLayoutY(btn.getLayoutY() - 50);
        TextField textField = new TextField ();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);

        label2.setLayoutX(bounds.getWidth()/2);
        label2.setLayoutY(hb.getLayoutY() - 50);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(handler.getStatus()) {
                    label2.setText(handler.errorHandler(handler.readCommand(textField.getText())));
                }
                else
                {
                    label2.setText("Warning: FSA has not been set");
                }
            }
        });

        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               MainMenu(primaryStage, handler);
            }
        });



        Pane root = new Pane();
        root.getChildren().add(btn);
        root.getChildren().add(btn2);
        root.getChildren().add(hb);
        root.getChildren().add(label2);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        return 0;
    }

    static int read(Stage primaryStage, Handler handler)
    {
        primaryStage.setTitle("Third Menu");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        Button btn = new Button();
        Button btn2 = new Button();
        Label label1 = new Label("File name:");
        HBox hb = new HBox();
        Label label2 = new Label();


        btn.setLayoutX(bounds.getWidth()/2);
        btn.setLayoutY(bounds.getHeight()/2);
        btn.setText("Load a File");

        btn2.setText("Back");

        hb.setLayoutX(bounds.getWidth()/2);
        hb.setLayoutY(btn.getLayoutY() - 50);
        TextField textField = new TextField ();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);

        label2.setLayoutX(bounds.getWidth()/2);
        label2.setLayoutY(hb.getLayoutY() - 50);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int x = handler.readFile(textField.getText());
                if(x==0)
                {
                    label2.setText(textField.getText() + " Loaded!");
                    handler.build(textField.getText());
                    PrintFSA.print(handler);
                    writeLSP.Create(handler);
                }
                else if(x == 1)
                {
                    label2.setText(("Unable to open file '" + textField.getText() + "'"));
                }
                else if(x == 2)
                {
                    label2.setText(("Error reading file '" + textField.getText() + "'"));
                }
            }
        });

        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                MainMenu(primaryStage, handler);
            }
        });

        Pane root = new Pane();
        root.getChildren().add(btn);
        root.getChildren().add(btn2);
        root.getChildren().add(hb);
        root.getChildren().add(label2);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        return 0;
    }
}