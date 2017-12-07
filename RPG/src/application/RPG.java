package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class RPG extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Java League");
        Button btn = new Button();
        Button btn0 = new Button();
        Button btn1 = new Button();
        btn.setText("Easy");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Starting Easy Game!");
            }
        });
        
        btn0.setText("Medium");
        btn0.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Starting Medium Game!");
            }
        });
        
        btn1.setText("Hard");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Starting Hard Game!");
            }
        });
        
        
        Pane root = new Pane();
        btn.setLayoutX(125);
        btn.setLayoutY(50);
        btn0.setLayoutX(115);
        btn0.setLayoutY(100);
        btn1.setLayoutX(125);
        btn1.setLayoutY(150);
        root.getChildren().add(btn);
        root.getChildren().add(btn0);
        root.getChildren().add(btn1);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}